package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Guard on a set of variables.
 * @param variableConditions a list of conditions to add to the guard.
 */
class VariablesGuard(
    private val variableConditions: List<VariableGuardCondition<*>>
) {

    companion object {
        val EMPTY = VariablesGuard(listOf())
    }

    /**
     * Constructs a guard with exactly one condition.
     * @param condition condition to add to gurad.
     */
    constructor(condition: VariableGuardCondition<*>) : this(listOf(condition))

    /**
     * Fluent builer to create a new guard from existing one adding one additional condition.
     * @param condition to add to existing guard.
     */
    fun fromExisting(condition: VariableGuardCondition<*>) = VariablesGuard(variableConditions.plus(condition))

    /**
     * Evaluates the contained conditions on variables from given variable map.
     * @param variableMap variable map to work on.
     * @return list of violations if any.
     */
    fun evaluate(variableMap: VariableMap): List<GuardViolation<*>> =
        variableConditions.flatMap { it.evaluate(variableMap) }

    /**
     * Evaluates the contained conditions on variables from given variable scope.
     * @param variableScope variable scope to work on.
     * @return list of violations if any.
     */
    fun evaluate(variableScope: VariableScope): List<GuardViolation<*>> =
        variableConditions.flatMap { it.evaluate(variableScope) }

    /**
     * Evaluates the contained conditions on variables retrieved from task service.
     * @param taskService task service to access the task.
     * @param taskId task id.
     * @return list of violations if any.
     */
    fun evaluate(taskService: TaskService, taskId: String): List<GuardViolation<*>> =
        variableConditions.flatMap { it.evaluate(taskService, taskId) }

    /**
     * Evaluates the contained conditions on variables retrieved from runtime service.
     * @param runtimeService runtime service to access the execution.
     * @param executionId execution id.
     * @return list of violations if any.
     */
    fun evaluate(runtimeService: RuntimeService, executionId: String): List<GuardViolation<*>> =
        variableConditions.flatMap { it.evaluate(runtimeService, executionId) }

    /**
     * Retrieves a list of local variables addressed by this guard.
     * @return variable factories extracted from condition with scope local.
     */
    fun getLocalVariables() = variableConditions.filter { it.local }.map { it.variableFactory }

    /**
     * Retrieves a list of variables addressed by this guard.
     * @return variable factories extracted from condition with scope global.
     */
    fun getVariables() = variableConditions.filter { !it.local }.map { it.variableFactory }
}
