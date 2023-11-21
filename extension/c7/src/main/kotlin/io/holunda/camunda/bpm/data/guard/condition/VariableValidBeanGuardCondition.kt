package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*
import java.util.function.Supplier
import jakarta.validation.Validator

/**
 * Condition to check if the variable is a valid bean.
 * @constructor Creates a condition.
 * @param T variable type.
 * @param variableFactory Factory to work with.
 * @param validatorSupplier supplier for the validator.
 * @param local flag indicating the variable scope (global/local). Defaults to global.
 */
class VariableValidBeanGuardCondition<T>(
  /**
   * Variable factory to work with.
   */
  variableFactory: VariableFactory<T>,
  private val validatorSupplier: Supplier<Validator>,
  local: Boolean = false
) : VariableGuardCondition<T>(variableFactory, local) {

  private val existsCondition = VariableExistsGuardCondition(variableFactory, local)

  override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
    val violations = existsCondition.evaluate(option).toMutableList()
    if (option.isPresent) {
      val constraintViolations = validatorSupplier.get().validate(option.get())
      if (constraintViolations.isNotEmpty()) {
        violations.add(
          GuardViolation(
            condition = this,
            option = option,
            message = "Expecting$localLabel variable '${variableFactory.name}' to be a valid bean, but its value '${option.get()}' has not." +
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
 * @param validatorSupplier supplier for the validator.
 * @return instance of [VariableValidBeanGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isValidBean(validatorSupplier: Supplier<Validator>) =
  VariableValidBeanGuardCondition(this, validatorSupplier, false)

/**
 * Creation extension for the local condition.
 * @param validatorSupplier supplier for the validator.
 * @return instance of [VariableValidBeanGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isValidBeanLocal(validatorSupplier: Supplier<Validator>) =
  VariableValidBeanGuardCondition(this, validatorSupplier, true)
