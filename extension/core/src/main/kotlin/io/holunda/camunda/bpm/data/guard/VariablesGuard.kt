package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Guard on a set of variables.
 * @constructor Creates new guard.
 * @property name optional name of the guard ('anonymous' by default)
 * @property variableConditions a list of conditions to add to the guard.
 */
class VariablesGuard(
  private val name : String?,
  private val variableConditions: List<VariableGuardCondition<*>>
) {

  companion object {
    /**
     * Empty guard.
     */
    val EMPTY = VariablesGuard(listOf())
  }

  /**
   * Constructs an anonymous guard with a list of conditions.
   * @param variableConditions conditions to add to gurad.
   */
  constructor(variableConditions: List<VariableGuardCondition<*>>) : this(null, variableConditions)

  /**
   * Constructs an anonymous guard with exactly one condition.
   * @param condition condition to add to gurad.
   */
  constructor(condition: VariableGuardCondition<*>) : this(listOf(condition))

  /**
   * Constructs a named guard with exactly one condition.
   * @param name name of the guard.
   * @param condition condition to add to gurad.
   */
  constructor(name: String, condition: VariableGuardCondition<*>) : this(name, listOf(condition))

  /**
   * Fluent builder to create a new guard from existing one adding one additional condition.
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

  /**
   * Retireves the name of this guard which can be provided during construction
   * @return the name of the VariableGuard, 'anonymous' if no name was specified
   */
  fun getName() = name

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as VariablesGuard

    if (variableConditions != other.variableConditions) return false

    return true
  }

  override fun hashCode(): Int {
    return variableConditions.hashCode()
  }

  override fun toString(): String {
    return "VariablesGuard%s(variableConditions=$variableConditions)".format(if (name != null) "[$name]" else "")
  }
}
