package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*

/**
 * Guard for testing a condition passed by a matcher-function.
 * @constructor Creates a condition.
 * @param T variable type.
 * @param variableFactory Factory to work with.
 * @param local flag indicating the variable scope (global/local). Defaults to global.
 * @property matcher matcher function, specifying the condition.
 */
class VariableMatchesGuardCondition<T>(
  /**
   * Variable factory to work with.
   */
  variableFactory: VariableFactory<T>,
  local: Boolean = false,
  private val matcher: (value: T) -> Boolean,
  private val violationMessageSupplier: (variableFactory: VariableFactory<T>, localLabel: String, option: Optional<T>) -> String =
    { factory, localLabel, option ->
      "Expecting$localLabel variable '${factory.name}' to match the condition, but its value '${option.get()}' has not."
    }
) : VariableGuardCondition<T>(variableFactory, local) {

  private val existsCondition = VariableExistsGuardCondition(variableFactory, local)

  override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
    val violations = existsCondition.evaluate(option).toMutableList()
    if (option.isPresent && !matcher.invoke(option.get())) {
      violations.add(
        GuardViolation(
          condition = this,
          option = option,
          message = violationMessageSupplier.invoke(variableFactory, localLabel, option)
        )
      )
    }
    return violations
  }

  override fun toString(): String {
    return "Matches condition for$localLabel variable '${super.variableFactory.name}'"
  }

}

/**
 * Creation extension for the condition.
 * @param matcher function that must match the value.
 * @return instance of [VariableMatchesGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matches(matcher: (value: T) -> Boolean) = VariableMatchesGuardCondition(this, false, matcher)

/**
 * Creation extension for the local condition.
 * @param matcher function that must match the value.
 * @return instance of [VariableMatchesGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matchesLocal(matcher: (value: T) -> Boolean) = VariableMatchesGuardCondition(this, true, matcher)

/**
 * Creation extension for the condition.
 * @param matcher function that must match the value.
 * @param violationMessageSupplier supplier that specify the violation message.
 * @return instance of [VariableMatchesGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matches(
  violationMessageSupplier: (variableFactory: VariableFactory<T>, localLabel: String, option: Optional<T>) -> String,
  matcher: (value: T) -> Boolean
) = VariableMatchesGuardCondition(this, false, matcher, violationMessageSupplier)

/**
 * Creation extension for the local condition.
 * @param matcher function that must match the value.
 * @param violationMessageSupplier supplier that specify the violation message.
 * @return instance of [VariableMatchesGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matchesLocal(
  violationMessageSupplier: (variableFactory: VariableFactory<T>, localLabel: String, option: Optional<T>) -> String,
  matcher: (value: T) -> Boolean
) = VariableMatchesGuardCondition(this, true, matcher, violationMessageSupplier)
