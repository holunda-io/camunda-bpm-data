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
 * @property reduceOperator operation to reduce list of single violations to a result of the guard.
 */
class VariablesGuard(
  private val name: String?,
  private val variableConditions: List<VariableGuardCondition<*>>,
  private val reduceOperator: ReduceOperator
) {

  companion object {
    /**
     * Default guard type checking that none of the conditions has been violated.
     */
    @JvmField
    val ALL: ReduceOperator = { violations -> violations.flatten() }

    /**
     * Guard allowing all but one conditions to be violated.
     */
    @JvmField
    val ONE_OF: ReduceOperator = { violations ->
      if (violations.none { it.isEmpty() }) {
        violations.flatten()
      } else {
        listOf()
      }
    }

    /**
     * Empty guard.
     */
    val EMPTY = VariablesGuard(name = null, variableConditions = listOf(), reduceOperator = ALL)
  }

  /**
   * Constructs a named guard with a list of conditions and ALL operator.
   * @param variableConditions conditions to add to guard.
   */
  constructor(name: String?, variableConditions: List<VariableGuardCondition<*>>) : this(name = name, variableConditions = variableConditions, reduceOperator = ALL)

  /**
   * Constructs an anonymous guard with a list of conditions.
   * @param variableConditions conditions to add to guard.
   */
  constructor(variableConditions: List<VariableGuardCondition<*>>) : this(name = null, variableConditions = variableConditions, reduceOperator = ALL)

  /**
   * Constructs an anonymous guard with exactly one condition.
   * @param condition condition to add to guard.
   */
  constructor(condition: VariableGuardCondition<*>) : this(name = null, variableConditions = listOf(condition), reduceOperator = ALL)

  /**
   * Constructs a named guard with exactly one condition.
   * @param name name of the guard.
   * @param condition condition to add to guard.
   */
  constructor(name: String, condition: VariableGuardCondition<*>) : this(name = name, variableConditions = listOf(condition), reduceOperator = ALL)

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
    reduceOperator.invoke(variableConditions.map { it.evaluate(variableMap) })

  /**
   * Evaluates the contained conditions on variables from given variable scope.
   * @param variableScope variable scope to work on.
   * @return list of violations if any.
   */
  fun evaluate(variableScope: VariableScope): List<GuardViolation<*>> =
    reduceOperator.invoke(variableConditions.map { it.evaluate(variableScope) })

  /**
   * Evaluates the contained conditions on variables retrieved from task service.
   * @param taskService task service to access the task.
   * @param taskId task id.
   * @return list of violations if any.
   */
  fun evaluate(taskService: TaskService, taskId: String): List<GuardViolation<*>> =
    reduceOperator.invoke(variableConditions.map { it.evaluate(taskService, taskId) })

  /**
   * Evaluates the contained conditions on variables retrieved from runtime service.
   * @param runtimeService runtime service to access the execution.
   * @param executionId execution id.
   * @return list of violations if any.
   */
  fun evaluate(runtimeService: RuntimeService, executionId: String): List<GuardViolation<*>> =
    reduceOperator.invoke(variableConditions.map { it.evaluate(runtimeService, executionId) })

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
   * Retrieves the name of this guard which can be provided during construction
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


/**
 * Reduce operation.
 */
typealias ReduceOperator = (violations: List<List<GuardViolation<*>>>) -> List<GuardViolation<*>>



