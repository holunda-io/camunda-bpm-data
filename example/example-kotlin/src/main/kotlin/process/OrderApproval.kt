package io.holunda.camunda.bpm.data.example.kotlin.process

import io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.customVariable
import io.holunda.camunda.bpm.data.delegate.VariableAwareExecutionListener
import io.holunda.camunda.bpm.data.delegate.VariableAwareJavaDelegate
import io.holunda.camunda.bpm.data.example.kotlin.domain.Order
import io.holunda.camunda.bpm.data.example.kotlin.domain.OrderPosition
import io.holunda.camunda.bpm.data.example.kotlin.domain.OrderRepository
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_ID
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_POSITION
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_TOTAL
import io.holunda.camunda.bpm.data.factory.VariableFactory
import mu.KLogging
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.DelegateTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import java.math.BigDecimal

/**
 * Backing bean.
 */
@Configuration
class OrderApproval {

  @Autowired
  lateinit var orderRepository: OrderRepository

  companion object : KLogging() {
    const val KEY = "order-approval"
  }

  object Variables {
    val ORDER_ID = stringVariable("orderId")
    val ORDER: VariableFactory<Order> = customVariable("order")
    val ORDER_APPROVED = booleanVariable("orderApproved")
    val ORDER_POSITION: VariableFactory<OrderPosition> = customVariable("orderPosition")
    val ORDER_TOTAL: VariableFactory<BigDecimal> = customVariable("orderTotal")
  }

  /**
   * Load a primitive variable by id (string) and store a complex variable (order).
   */
  @Bean
  fun loadOrder() = VariableAwareJavaDelegate { execution ->
    val orderId = execution.get(ORDER_ID);
    val order = orderRepository.loadOrder(orderId)

    execution.set(ORDER, order)
    execution.set(ORDER_TOTAL, BigDecimal.ZERO)
  }

  /**
   * Load a local order position, write a local variable.
   */
  @Bean
  fun calculateOrderPositions() = VariableAwareJavaDelegate { execution ->
    val orderPosition = execution.get(ORDER_POSITION)

    ORDER_TOTAL.on(execution).update { it.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount))) }
  }

  /**
   * Read a local variable and store it in global variable.
   */
  @Bean
  fun writeOrderTotal() = VariableAwareExecutionListener { execution ->
    val total = execution.get(ORDER_TOTAL)
    execution.set(ORDER_TOTAL, total)
  }

  /**
   * Log the task id.
   */
  @EventListener(condition = "#task != null && #task.eventName == 'create'")
  fun taskLogger(task: DelegateTask) {
    logger.info("TASK LOGGER: Created user task ${task.id}")
  }

  @EventListener(condition = "#execution != null && #execution.eventName == 'start' && #execution.currentActivityId == 'start_order_created'")
  fun processStartLogger(execution: DelegateExecution) {
    logger.info { "INSTANCE LOGGER: Started process instance ${execution.processInstanceId}" }
  }

  @EventListener(condition = "#execution != null && #execution.eventName == 'end' && #execution.currentActivityId == 'end_order_approved'")
  fun processEndLogger(execution: DelegateExecution) {
    logger.info { "INSTANCE LOGGER: Finished process instance ${execution.processInstanceId}" }
  }

}
