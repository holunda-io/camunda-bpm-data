package io.holunda.camunda.bpm.data.example.process;

import io.holunda.camunda.bpm.data.example.domain.Order;
import io.holunda.camunda.bpm.data.guard.integration.GuardViolationException;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.spring.boot.starter.test.helper.StandaloneInMemoryTestConfiguration;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@Deployment(resources = "order_approval.bpmn")
public class GuardProcessTest {

  @RegisterExtension
  public static final ProcessEngineExtension engine = ProcessEngineExtension
    .builder()
    .useProcessEngine(new StandaloneInMemoryTestConfiguration(new SpinProcessEnginePlugin()).buildProcessEngine())
    .build();


  @BeforeEach
  public void register() {
    OrderApproval config = new OrderApproval();
    Mocks.register("guardExecutionListener", config.guardExecutionListener());
    Mocks.register("guardTaskListener", config.guardTaskListener());
    Mocks.register("orderApproval", new MockOrderApproval());
  }

  static class MockOrderApproval {
    public JavaDelegate loadOrder() {
      return execution -> {
        OrderApproval.ORDER.on(execution).set(new Order("1", Date.from(Instant.now()), new ArrayList<>()));
      };
    }

    public JavaDelegate calculateOrderPositions() {
      return mock(JavaDelegate.class);
    }
  }

  @Test
  public void shouldFireExceptionIfOrderIdIsMissing() {

    assertThrows(
      GuardViolationException.class,
      // manual start by-passing the factory
      () -> engine.getRuntimeService().startProcessInstanceByKey(OrderApproval.KEY),
      "Guard violated by execution '6' in activity 'Order created'\nExpecting variable 'orderId' to be set, but it was not found.\n");

  }

  @Test
  public void shouldFireExceptionApproveDecisionIsMissing() {

    OrderApprovalInstanceFactory factory = new OrderApprovalInstanceFactory(engine.getRuntimeService());
    factory.start("1");

    // async after start
    Job asyncStart = engine.getManagementService().createJobQuery().singleResult();
    engine.getManagementService().executeJob(asyncStart.getId());
    Task task = engine.getTaskService().createTaskQuery().singleResult();

    assertThrows(
      ProcessEngineException.class,
      () -> engine.getTaskService().complete(task.getId()),
      "Guard violated in task 'Approve order' (taskId: '21')\nExpecting variable 'orderApproved' to be set, but it was not found.\n"
    );
  }

}
