package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.guard.GuardViolation
import org.camunda.bpm.engine.ProcessEngineException

/**
 * Exception indicating a guard violation.
 */
class GuardViolationException(
    val violations: List<GuardViolation<*>>,
    override val message: String
) : ProcessEngineException(createMessage(message, violations)) {
    companion object {
        /**
         * Create a message from a provided message prefix and a list of violations.
         * @param message message prefix.
         * @param violations list of violations.
         * @return message.
         */
        @JvmStatic
        fun createMessage(message: String, violations: List<GuardViolation<*>>): String {
            return "$message\n" + violations.joinToString { "\t${it.message}\n" }
        }
    }
}