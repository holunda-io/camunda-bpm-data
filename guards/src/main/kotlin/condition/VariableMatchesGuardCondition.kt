package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import java.util.*

class VariableMatchesGuardCondition<T>(
    variableFactory: VariableFactory<T>,
    local: Boolean = false,
    private val matcher: (value: T) -> Boolean
) : VariableGuardCondition<T>(variableFactory, local) {

    private val existsCondition = VariableExistsGuardCondition(variableFactory, local)

    override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
        val violations = existsCondition.evaluate(option).toMutableList()
        if (!matcher.invoke(option.get())) {
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
}

/**
 * Creation extension for the condition.
 * @param local is the variable should be local.
 * @param matcher function that must match the value.
 * @return instance of [VariableMatchesGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matches(local: Boolean = false, matcher: (value: T) -> Boolean)
    = VariableMatchesGuardCondition(this, local, matcher)