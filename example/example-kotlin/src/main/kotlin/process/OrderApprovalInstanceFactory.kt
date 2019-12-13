package io.holunda.camunda.bpm.data.example.kotlin.process

import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_ID
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderApprovalInstanceFactory(
  private val runtimeService: RuntimeService
) {

  fun start(): OrderApprovalInstance {
    val vars = createVariables()
    ORDER_ID.on(vars).set("1")
    val instance = runtimeService.startProcessInstanceByKey(OrderApproval.KEY, "order-${UUID.randomUUID()}", vars)
    return OrderApprovalInstance(instance)
  }
}
