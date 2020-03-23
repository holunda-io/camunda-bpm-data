package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.guard.condition.VariableGuardCondition
import java.util.*

/**
 * Represents a violation of a guard.
 */
data class GuardViolation<T>(
    /**
     * Guard condition.
     */
    val condition: VariableGuardCondition<T>,
    /**
     * Retrieved value.
     */
    val option: Optional<T>,
    /**
     * Message, explaining the violation.
     */
    val message: String
)
