package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Guard on a set of variables.
 */
class VariablesGuard(
  /**
   * List of conditions.
   */
  val variableConditions: List<VariableGuardCondition<*>>
) {

  /**
   * Constructs a guard with exactly one condition.
   */
  constructor(condition: VariableGuardCondition<*>) : this(listOf(condition))

  /**
   * Creates a new guard from existing one adding one additional condition.
   */
  fun fromExisting(condition: VariableGuardCondition<*>) = VariablesGuard(variableConditions.plus(condition))

  fun evaluate(variableMap: VariableMap): List<GuardViolation<*>> =
    variableConditions.flatMap { it.evaluate(variableMap) }

  fun evaluate(variableScope: VariableScope): List<GuardViolation<*>> =
    variableConditions.flatMap { it.evaluate(variableScope) }

  fun evaluate(taskService: TaskService, taskId: String): List<GuardViolation<*>> =
    variableConditions.flatMap { it.evaluate(taskService, taskId) }

  fun evaluate(runtimeService: RuntimeService, executionId: String): List<GuardViolation<*>> =
    variableConditions.flatMap { it.evaluate(runtimeService, executionId) }

  fun getLocalVariables() = variableConditions.filter { it.local }.map { it.variableFactory }
  fun getVariables() = variableConditions.filter { !it.local }.map { it.variableFactory }
}
