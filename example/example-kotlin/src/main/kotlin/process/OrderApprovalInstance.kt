package io.holunda.camunda.bpm.data.example.kotlin.process

import org.camunda.bpm.engine.runtime.ProcessInstance

/**
 * Represents order delivery process instance.
 */
class OrderApprovalInstance(
    private val delegate: ProcessInstance) : ProcessInstance by delegate {
}
