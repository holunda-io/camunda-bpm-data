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
    private val matcher: (value: T) -> Boolean
) : VariableGuardCondition<T>(variableFactory, local) {

    private val existsCondition = VariableExistsGuardCondition(variableFactory, local)

    override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
        val violations = existsCondition.evaluate(option).toMutableList()
        if (option.isPresent && !matcher.invoke(option.get())) {
            violations.add(
                GuardViolation(
                    condition = this,
                    option = option,
                    message = "Expecting$localLabel variable '${variableFactory.name}' to match the condition, but its value '${option.get()}' has not."
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
 * @param local is the variable should be local.
 * @param matcher function that must match the value.
 * @return instance of [VariableMatchesGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matches(local: Boolean = false, matcher: (value: T) -> Boolean) = VariableMatchesGuardCondition(this, local, matcher)
