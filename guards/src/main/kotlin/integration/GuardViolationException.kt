package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.guard.GuardViolation
import org.camunda.bpm.engine.ProcessEngineException

class GuardViolationException(
    val violations: List<GuardViolation<*>>,
    override val message: String
) : ProcessEngineException(createMessage(message, violations)) {
    companion object {
        @JvmStatic
        fun createMessage(message: String, violations: List<GuardViolation<*>>): String {
            return "$message\n" + violations.joinToString { "\t${it.message}\n" }
        }
    }
}