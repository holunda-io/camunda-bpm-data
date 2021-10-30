package io.holunda.camunda.bpm.data.example.process;

import io.holunda.camunda.bpm.data.example.domain.Order;
import io.holunda.camunda.bpm.data.example.domain.OrderPosition;
import io.holunda.camunda.bpm.data.example.domain.OrderRepository;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.bpm.data.guard.integration.DefaultGuardExecutionListener;
import io.holunda.camunda.bpm.data.guard.integration.DefaultGuardTaskListener;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.*;

/**
 * Process backing bean.
 */
@Configuration
public class OrderApproval {

  public static final String KEY = "order-approval";

  enum Elements {
    start_order_created,
    user_approve_order,
    end_order_approved,
    end_order_rejected;

    static String element(Elements element) {
      return element.name();
    }
  }

  public static final VariableFactory<String> ORDER_ID = stringVariable("orderId");
  public static final VariableFactory<Order> ORDER = customVariable("order", Order.class);
  public static final VariableFactory<Boolean> ORDER_APPROVED = booleanVariable("orderApproved");
  public static final VariableFactory<OrderPosition> ORDER_POSITION = customVariable("orderPosition", OrderPosition.class);
  public static final VariableFactory<BigDecimal> ORDER_TOTAL = customVariable("orderTotal", BigDecimal.class);

  private static final Logger logger = LoggerFactory.getLogger(OrderApproval.class);

  @Autowired
  private OrderRepository orderRepository;

  /**
   * Loads a primitive variable by id (string) and store a complex variable (order).
   * Used in "Load Order" service task in BPMN ${loadOrder}
   *
   * @return Java delegate
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

      // alternative
      // ORDER_TOTAL.on(execution).updateLocal(amount -> amount.add(orderPosition.getNetCost().multiply(BigDecimal.valueOf(orderPosition.getAmount()))));
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
   * Checks that the variable "orderId" exists.
   * Used as execution listener on start event in BPMN ${guardExecutionListener}
   *
   * @return execution listener.
   */
  @Bean
  public ExecutionListener guardExecutionListener() {
    return new DefaultGuardExecutionListener(newArrayList(exists(ORDER_ID)), true);
  }

  /**
   * Checks that the variable "orderApproved" exists and is true.
   * Used as task listener on complete of user task in BPMN ${taskExecutionListener}
   *
   * @return task listener.
   */
  @Bean
  public TaskListener guardTaskListener() {
    return new DefaultGuardTaskListener(
      newArrayList(
        exists(ORDER_APPROVED)
      ), true
    );
  }

  /**
   * Logs the task creation.
   *
   * @param task task passed by the engine.
   */
  @EventListener(condition = "#task != null && #task.eventName == 'create'")
  public void taskLogger(DelegateTask task) {
    logger.info("TASK LOGGER: Created user task {}", task.getId());
  }

  /**
   * Logs process start.
   *
   * @param execution execution passed by the engine.
   */
  @EventListener(condition = "#execution != null && #execution.eventName == 'start' && #execution.currentActivityId == 'start_order_created'")
  public void processStartLogger(DelegateExecution execution) {
    logger.info("INSTANCE LOGGER: Started process instance {}", execution.getProcessInstanceId());
  }

  /**
   * Logs process end.
   *
   * @param execution execution passed by the engine.
   */
  @EventListener(condition = "#execution != null && #execution.eventName == 'end' && #execution.currentActivityId == 'end_order_approved'")
  public void processEndLogger(DelegateExecution execution) {
    logger.info("INSTANCE LOGGER: Finished process instance {}", execution.getProcessInstanceId());
  }

}
