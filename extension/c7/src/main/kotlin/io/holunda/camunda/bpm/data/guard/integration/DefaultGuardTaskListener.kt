package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import org.camunda.bpm.engine.delegate.DelegateTask
import org.camunda.bpm.engine.delegate.TaskListener
import org.slf4j.LoggerFactory

/**
 * Default guard execution listener, evaluating the given guard conditions on the task.
 * @constructor Creates a listener.
 * @property guard guard to check.
 * @property throwViolations flag controlling if the violation should lead to an exception.
 */
class DefaultGuardTaskListener(
  val guard: VariablesGuard,
  val throwViolations: Boolean = true
) : TaskListener {

  companion object {
    private val logger = LoggerFactory.getLogger(DefaultGuardTaskListener::class.java)
  }

  /**
   * Constructs an execution listener using the provided conditions.
   * @param variableConditions condition to check by the guard.
   * @param throwViolations flag controlling if the violation should lead to an exception.
   */
  @Deprecated("create VariablesGuard and use constructor(guard, throwViolations) instead")
  constructor(variableConditions: List<VariableGuardCondition<*>>, throwViolations: Boolean) : this(VariablesGuard(variableConditions), throwViolations)

  override fun notify(task: DelegateTask) {
    val violations = guard.evaluate(task)
    if (violations.isNotEmpty()) {
      val message = "${guard.getName() ?: "Guard"} violated in task '${task.name.removeNewLines()}' (taskId: '${task.id}')"
      violations.forEach {
        logger.error("$message: ${it.message}")
      }
      if (throwViolations) {
        throw GuardViolationException(violations = violations, reason = message)
      }
    }
  }

  /**
   * Removes new lines from the task name.
   */
  private fun String.removeNewLines() = this
    .replace("\n", " ")
}
