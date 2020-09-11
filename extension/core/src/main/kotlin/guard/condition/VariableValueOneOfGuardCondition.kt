package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*

/**
 * Condition to check if the variable has on of the provided values.
 * @constructor Creates a condition.
 * @param variableFactory factory to work on.
 * @param values set of values to compare with.
 * @param local flag indicating if local or global scope is required.
 */
class VariableValueOneOfGuardCondition<T>(
  variableFactory: VariableFactory<T>,
  val values: Set<T>,
  local: Boolean = false
) : VariableGuardCondition<T>(variableFactory, local) {

  private val existsCondition = VariableExistsGuardCondition(variableFactory, local)
  private val valueConditions = values.map { VariableValueGuardCondition(variableFactory, it, local) }

  override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
    val violations = existsCondition.evaluate(option).toMutableList()
    if (option.isPresent) {
      if (valueConditions.none { it.evaluate(option).isEmpty() }) {
        violations.add(
          GuardViolation(
            condition = this,
            option = option,
            message = "Expecting$localLabel variable '${variableFactory.name}' to be one of [${values.joinToString("', '", "'", "'")}], but it was '${option.get()}'."
          )
        )
      }
    }
    return violations
  }

  override fun toString(): String {
    return "ValueIn condition for$localLabel variable '${super.variableFactory.name}', values [${values.joinToString("', '", "'", "'")}]"
  }

}

/**
 * Creation extension for the condition.
 * @param values set of values which are allowed.
 * @return instance of [VariableValueOneOfGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.hasOneOfValues(values: Set<T>) = VariableValueOneOfGuardCondition(this, values, false)

/**
 * Creation extension for the local condition.
 * @param values set of values which are allowed.
 * @return instance of [VariableValueOneOfGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.hasOneOfValuesLocal(values: Set<T>) = VariableValueOneOfGuardCondition(this, values, true)
