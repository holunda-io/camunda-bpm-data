package io.holunda.camunda.bpm.data.example.kotlin.process

import io.holunda.camunda.bpm.data.builder.VariableMapBuilder
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_ID
import java.util.*
import org.camunda.bpm.engine.RuntimeService
import org.springframework.stereotype.Component

/** Instance factory. */
@Component
class OrderApprovalInstanceFactory(private val runtimeService: RuntimeService) {

  /** Starts the approval process. */
  fun start(id: String): OrderApprovalInstance {
    val vars = VariableMapBuilder().set(ORDER_ID, id).build()
    val instance =
      runtimeService.startProcessInstanceByKey(
        OrderApproval.KEY,
        "order-${UUID.randomUUID()}",
        vars
      )
    return OrderApprovalInstance(instance)
  }
}
