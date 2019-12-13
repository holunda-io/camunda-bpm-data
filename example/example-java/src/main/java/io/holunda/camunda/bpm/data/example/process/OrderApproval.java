package io.holunda.camunda.bpm.data.example.process;

import io.holunda.camunda.bpm.data.example.domain.Order;
import io.holunda.camunda.bpm.data.example.domain.OrderPosition;
import io.holunda.camunda.bpm.data.example.service.OrderRepository;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;

@Configuration
public class OrderApproval {

  public static final String KEY = "order-approval";

  public static final VariableFactory<String> ORDER_ID = stringVariable("orderId");
  public static final VariableFactory<Order> ORDER = customVariable("order", Order.class);
  public static final VariableFactory<Boolean> ORDER_APPROVED = booleanVariable("orderApproved");
  public static final VariableFactory<OrderPosition> ORDER_POSITION = customVariable("orderPosition", OrderPosition.class);
  public static final VariableFactory<BigDecimal> ORDER_TOTAL = customVariable("orderTotal", BigDecimal.class);

  private static final Logger logger = LoggerFactory.getLogger(OrderApproval.class);

  @Autowired
  private OrderRepository orderRepository;

  /**
   * Load a primitive variable by id (string) and store a complex variable (order).
   */
  @Bean
  public JavaDelegate loadOrder() {
    return execution -> {
      String orderId = ORDER_ID.from(execution).get();
      Order order = orderRepository.loadOrder(orderId);
      ORDER.on(execution).set(order);
    };
  }

  /**
   * Load a local order position, write a local variable.
   */
  @Bean
  public JavaDelegate calculateOrderPositions() {
    return execution -> {
      OrderPosition orderPosition = ORDER_POSITION.from(execution).get();
      BigDecimal oldTotal = ORDER_TOTAL.from(execution).getOptional().orElse(BigDecimal.ZERO);
      BigDecimal newTotal = oldTotal.add(orderPosition.getNetCost().multiply(BigDecimal.valueOf(orderPosition.getAmount())));
      ORDER_TOTAL.on(execution).setLocal(newTotal);
    };
  }


  /**
   * Read a local variable and store it in global variable.
   */
  @Bean
  public ExecutionListener writeOrderTotal() {
    return execution ->
    {
      BigDecimal total = ORDER_TOTAL.from(execution).get();
      ORDER_TOTAL.on(execution).set(total);
    };
  }

  /**
   * Log the task id.
   */
  @EventListener(condition = "#task != null && #task.eventName == 'create'")
  public void taskLogger(DelegateTask task) {
    logger.info("TASK LOGGER: Created user task {}", task.getId());
  }

  @EventListener(condition = "#execution != null && #execution.eventName == 'start' && #execution.currentActivityId == 'start_order_created'")
  public void processStartLogger(DelegateExecution execution) {
    logger.info("INSTANCE LOGGER: Started process instance {}", execution.getProcessInstanceId());
  }

  @EventListener(condition = "#execution != null && #execution.eventName == 'end' && #execution.currentActivityId == 'end_order_approved'")
  public void processEndLogger(DelegateExecution execution) {
    logger.info("INSTANCE LOGGER: Finished process instance {}", execution.getProcessInstanceId());
  }

}
