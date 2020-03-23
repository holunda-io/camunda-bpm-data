package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import mu.KLogging
import org.camunda.bpm.engine.delegate.DelegateTask
import org.camunda.bpm.engine.delegate.TaskListener

/**
 * Abstract guard execution listener, evaluating the given guard conditions on the task.
 * @param variableConditions condition to check by the guard.
 * @param throwViolations flag controlling if the violation should lead to an exception.
 */
abstract class AbstractGuardTaskListener(
    val variableConditions: List<VariableGuardCondition<*>>,
    val throwViolations: Boolean = true
) : TaskListener {

    companion object : KLogging()

    private val guard = VariablesGuard(variableConditions)

    override fun notify(task: DelegateTask) {
        val violations = guard.evaluate(task)
        if (violations.isNotEmpty()) {
            val message = "Guard violated in task '${task.name.removeNewLines()}' (taskId: '${task.id}')"
            violations.forEach {
                logger.error { "$message: ${it.message}" }
            }
            if (throwViolations) {
                throw GuardViolationException(violations = violations, message = message)
            }
        }
    }

    /**
     * Removes new lines from the task name.
     */
    fun String.removeNewLines() = this
        .replace("\n", " ")
}