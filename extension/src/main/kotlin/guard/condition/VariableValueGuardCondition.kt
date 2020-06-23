package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*

/**
 * Condition to check if the variable has provided value.
 * @param variableFactory factory to work on.
 * @param value set of values to compare with.
 * @param local flag indicating if local or global scope is required.
 */
class VariableValueGuardCondition<T>(
    variableFactory: VariableFactory<T>,
    private val value: T,
    local: Boolean = false
) : VariableGuardCondition<T>(variableFactory, local) {

    private val existsCondition = VariableExistsGuardCondition(variableFactory, local)

    override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
        val violations = existsCondition.evaluate(option).toMutableList()
        if (option.isPresent) {
            if (option.get() != value) {
                violations.add(
                    GuardViolation(
                        condition = this,
                        option = option,
                        message = "Expecting$localLabel variable '${variableFactory.name}' to have value '$value', but it was '${option.get()}'."
                    )
                )
            }
        }
        return violations
    }

    override fun toString(): String {
        return "Value condition for$localLabel variable '${super.variableFactory.name}', value $value"
    }

}

/**
 * Creation extension for the condition.
 * @param value value to check for.
 * @param local if the variable should be local.
 * @return instance of [VariableValueGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.hasValue(value: T, local: Boolean = false) = VariableValueGuardCondition(this, value, local)

