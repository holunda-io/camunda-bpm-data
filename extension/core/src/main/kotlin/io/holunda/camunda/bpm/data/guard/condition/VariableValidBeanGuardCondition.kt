package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*
import javax.validation.Validation

/**
 * Condition to check if the variable is a valid bean.
 * @constructor Creates a condition.
 * @param T variable type.
 * @param variableFactory Factory to work with.
 * @param local flag indicating the variable scope (global/local). Defaults to global.
 */
class VariableValidBeanGuardCondition<T>(
  /**
   * Variable factory to work with.
   */
  variableFactory: VariableFactory<T>,
  local: Boolean = false
) : VariableGuardCondition<T>(variableFactory, local) {

  private val existsCondition = VariableExistsGuardCondition(variableFactory, local)
  private val validator = Validation.buildDefaultValidatorFactory().validator

  override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
    val violations = existsCondition.evaluate(option).toMutableList()
    if (option.isPresent) {
      val constraintViolations = validator.validate(option.get())
      if (constraintViolations.isNotEmpty()) {
        violations.add(
          GuardViolation(
            condition = this,
            option = option,
            message = "Expecting$localLabel variable '${variableFactory.name}' be a valid bean, but its value '${option.get()}' has not." +
              constraintViolations.joinToString(separator = ". ", prefix = " ") { it.message }
          )
        )
      }
    }

    return violations
  }

  override fun toString(): String {
    return "ValidBean condition for$localLabel variable '${super.variableFactory.name}'"
  }

}

/**
 * Creation extension for the condition.
 * @return instance of [VariableValidBeanGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isValidBean() = VariableValidBeanGuardCondition(this, false)

/**
 * Creation extension for the local condition.
 * @return instance of [VariableValidBeanGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isValidBeanLocal() = VariableValidBeanGuardCondition(this, true)
