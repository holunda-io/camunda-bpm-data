package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Abstract guard condition.
 * @since 0.0.6
 * @param T variable type.
 * @param variableFactory factory to work on.
 * @param local flag indicating the variable scope (global/local). Defaults to global.
 * <p>
 *     This class is intended to be subclassed by developers of new variable guards.
 * </p>
 */
abstract class VariableGuardCondition<T>(
    internal val variableFactory: VariableFactory<T>,
    internal val local: Boolean = false
) {

    /**
     * Label for messages indicating the variable scope (local or global, which is a default).
     */
    val localLabel: String by lazy { if (local) " local" else "" }

    /**
     * Evaluate the condition on a value option.
     * @param option optional value for the variable, contaning the value or nothing.
     * @return list of guard violations.
     */
    internal open fun evaluate(option: Optional<T>): List<GuardViolation<T>> = emptyList()

    /**
     * Evaluate the condition on a value retrieved from the variable map.
     * @param variableMap variables to run the evaluation on.
     * @return list of guard violations
     */
    fun evaluate(variableMap: VariableMap): List<GuardViolation<T>> {
        return evaluate(if (local) variableFactory.from(variableMap).localOptional else variableFactory.from(variableMap).optional)
    }

    /**
     * Evaluate the condition on a value retrieved from the variable map.
     * @param variableScope variable scope (e.g. delegate execution or delegate task) to run the evaluation on.
     * @return list of guard violations
     */
    fun evaluate(variableScope: VariableScope): List<GuardViolation<T>> {
        return evaluate(if (local) variableFactory.from(variableScope).localOptional else variableFactory.from(variableScope).optional)
    }

    /**
     * Evaluate the condition on a value retrieved from the task service.
     * @param taskService to retrieve the task from.
     * @param taskId id of the task to work on.
     * @return list of guard violations
     */
    fun evaluate(taskService: TaskService, taskId: String): List<GuardViolation<T>> {
        return evaluate(if (local) variableFactory.from(taskService, taskId).localOptional else variableFactory.from(taskService, taskId).optional)
    }

    /**
     * Evaluate the condition on a value retrieved from the runtime service.
     * @param runtimeService to retrieve the execution from.
     * @param executionId id of the execution to work on.
     * @return list of guard violations
     */
    fun evaluate(runtimeService: RuntimeService, executionId: String): List<GuardViolation<T>> {
        return evaluate(if (local) variableFactory.from(runtimeService, executionId).localOptional else variableFactory.from(runtimeService, executionId).optional)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VariableGuardCondition<*>

        if (variableFactory != other.variableFactory) return false
        if (local != other.local) return false

        return true
    }

    override fun hashCode(): Int {
        var result = variableFactory.hashCode()
        result = 31 * result + local.hashCode()
        return result
    }

}
