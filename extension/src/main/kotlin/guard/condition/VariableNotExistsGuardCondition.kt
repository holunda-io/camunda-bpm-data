package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*

/**
 * Condition to check if the variable doesn't exist.
 * @param variableFactory factory to work on.
 * @param local flag indicating if local or global scope is required.
 */
class VariableNotExistsGuardCondition<T>(
    variableFactory: VariableFactory<T>,
    local: Boolean = false
) : VariableGuardCondition<T>(variableFactory, local) {

    override fun evaluate(option: Optional<T>) =
        if (!option.isPresent) {
            super.evaluate(option)
        } else {
            listOf(GuardViolation(
                condition = this,
                option = option,
                message = "Expecting$localLabel variable '${variableFactory.name}' not to be set, but it had a value of '${option.get()}'.")
            )
        }

    override fun toString(): String {
        return "NotExists condition for$localLabel variable '${super.variableFactory.name}'"
    }

}

/**
 * Creation extension for the condition.
 * @param local is the variable should be local.
 * @return instance of [VariableNotExistsGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.notExists(local: Boolean = false) = VariableNotExistsGuardCondition(this, local)

