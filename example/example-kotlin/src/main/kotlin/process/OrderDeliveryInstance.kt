package io.holunda.camunda.bpm.data.example.process

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.runtime.ProcessInstance

/**
 * Represents order delivery process instance.
 */
class OrderDeliveryInstance(
  private val delegate: ProcessInstance) : ProcessInstance by delegate {
}
