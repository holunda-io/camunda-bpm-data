package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.ExecutionListener
import org.slf4j.LoggerFactory

/**
 * Default guard execution listener, evaluating the given guard conditions on the execution.
 * @constructor Creates a listener.
 * @property guard guard to check.
 * @property throwViolations flag controlling if the violation should lead to an exception.
 */
class DefaultGuardExecutionListener(
  val guard: VariablesGuard,
  val throwViolations: Boolean = true
) : ExecutionListener {

  companion object {
    private val logger = LoggerFactory.getLogger(DefaultGuardExecutionListener::class.java)
  }

  /**
   * Constructs an execution listener using the provided conditions.
   * @param variableConditions condition to check by the guard.
   * @param throwViolations flag controlling if the violation should lead to an exception.
   */
  constructor(variableConditions: List<VariableGuardCondition<*>>, throwViolations: Boolean) : this(VariablesGuard(variableConditions), throwViolations)

  override fun notify(execution: DelegateExecution) {
    val violations = guard.evaluate(execution)
    if (violations.isNotEmpty()) {
      val message = "Guard violated by execution '${execution.id}' in activity '${execution.currentActivityName}'"
      violations.forEach {
        logger.error("$message: ${it.message}")
      }
      if (throwViolations) {
        throw GuardViolationException(violations = violations, reason = message)
      }
    }
  }
}
