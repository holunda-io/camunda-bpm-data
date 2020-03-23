package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import mu.KLogging
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.ExecutionListener

/**
 * Abstract guard execution listener, evaluating the given guard conditions on the execution.
 * @param variableConditions condition to check by the guard.
 * @param throwViolations flag controlling if the violation should lead to an exception.
 */
abstract class AbstractGuardExecutionListener(
    val variableConditions: List<VariableGuardCondition<*>>,
    val throwViolations: Boolean = true
) : ExecutionListener {

    companion object: KLogging()

    private val guard = VariablesGuard(variableConditions)

    override fun notify(execution: DelegateExecution) {
        val violations =  guard.evaluate(execution)
        if (violations.isNotEmpty()) {
            val message = "Guard violated by execution '${execution.id}' in activity '${execution.currentActivityName}'"
            violations.forEach {
                logger.error { "$message: ${it.message}" }
            }
            if (throwViolations) {
                throw GuardViolationException(violations = violations, message = message)
            }
        }
    }
}