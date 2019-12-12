package io.holunda.camunda.bpm.data.example.process

import io.holunda.camunda.bpm.data.example.process.OrderDelivery.Variables.ORDER_ID
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDeliveryInstanceFactory(
  private val runtimeService: RuntimeService
) {

  fun start(): OrderDeliveryInstance {
    val vars = createVariables()
    ORDER_ID.on(vars).set("1")
    val instance = runtimeService.startProcessInstanceByKey(OrderDelivery.KEY, "order-${UUID.randomUUID()}", vars)
    return OrderDeliveryInstance(instance)
  }
}
