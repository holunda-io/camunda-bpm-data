package io.holunda.camunda.bpm.data.example.kotlin.process

import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_ID
import io.holunda.camunda.bpm.data.writer
import io.holunda.camunda.bpm.data.writer.VariableMapWriter
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.springframework.stereotype.Component
import java.util.*

/**
 * Instance factory.
 */
@Component
class OrderApprovalInstanceFactory(
    private val runtimeService: RuntimeService
) {

    /**
     * Starts the approval process.
     */
    fun start(id: String): OrderApprovalInstance {
        val vars = VariableMapWriter(createVariables()).set(ORDER_ID, id).variables()
        val instance = runtimeService.startProcessInstanceByKey(OrderApproval.KEY, "order-${UUID.randomUUID()}", vars)
        return OrderApprovalInstance(instance)
    }

}
