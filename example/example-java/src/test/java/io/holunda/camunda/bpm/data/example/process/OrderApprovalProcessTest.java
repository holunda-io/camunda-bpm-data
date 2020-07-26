package io.holunda.camunda.bpm.data.example.process;


import com.google.common.collect.Lists;
import io.holunda.camunda.bpm.data.builder.VariableMapBuilder;
import io.holunda.camunda.bpm.data.example.domain.Order;
import io.holunda.camunda.bpm.data.example.domain.OrderPosition;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.spring.boot.starter.test.helper.StandaloneInMemoryTestConfiguration;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;

import static io.holunda.camunda.bpm.data.example.process.OrderApproval.Elements.*;
import static io.holunda.camunda.bpm.data.example.process.OrderApproval.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@Deployment(resources = "order_approval.bpmn")
public class OrderApprovalProcessTest {

  @Rule
  public final ProcessEngineRule rule = new StandaloneInMemoryTestConfiguration(
    Lists.newArrayList(new SpinProcessEnginePlugin())
  ).rule();

  private OrderApprovalInstanceFactory factory;


  @Before
  public void register() {
    factory = new OrderApprovalInstanceFactory(rule.getRuntimeService());
    OrderApproval config = new OrderApproval();
    Mocks.register("guardExecutionListener", config.guardExecutionListener());
    Mocks.register("guardTaskListener", config.guardTaskListener());
    Mocks.register("orderApproval", new MockOrderApproval());
  }

  @Test
  public void shouldDeploy() {
    // empty method body checks deployment
  }

  @Test
  public void shouldStartAsync() {
    OrderApprovalInstance instance = factory.start("1");

    assertThat(instance.get()).isStarted();
    assertThat(instance.get()).isWaitingAt(element(start_order_created));
  }

  @Test
  public void shouldStartAndWaitInUserTask() {
    OrderApprovalInstance instance = factory.start("1");

    assertThat(instance.get()).isStarted();

    // pass async on start
    execute(job());

    assertThat(instance.get()).isWaitingAt(element(user_approve_order));
  }

  @Test
  public void shouldStartAndWaitInUserTaskAndApprove() {
    OrderApprovalInstance instance = factory.start("1");

    assertThat(instance.get()).isStarted();

    // pass async on start
    execute(job());

    // complete user task
    complete(task(), new VariableMapBuilder().set(ORDER_APPROVED, true).build());
    // pass async oafter user task
    execute(job());

    assertThat(instance.get()).isEnded();
    assertThat(instance.get()).hasPassed(element(end_order_approved));
  }

  @Test
  public void shouldStartAndWaitInUserTaskAndReject() {
    OrderApprovalInstance instance = factory.start("1");

    assertThat(instance.get()).isStarted();

    // pass async on start
    execute(job());

    // complete user task
    complete(task(), new VariableMapBuilder().set(ORDER_APPROVED, false).build());
    // pass async oafter user task
    execute(job());

    assertThat(instance.get()).isEnded();
    assertThat(instance.get()).hasPassed(element(end_order_rejected));
  }


  /**
   * Stub for the test.
   */
  static class MockOrderApproval {
    public JavaDelegate loadOrder() {
      return execution -> {
        ORDER.on(execution).set(new Order("1", Date.from(Instant.now()), Lists.newArrayList(
          new OrderPosition("Pencil", BigDecimal.valueOf(1.99), 3L),
          new OrderPosition("Sheet", BigDecimal.valueOf(0.17), 3L)
        )));
      };
    }

    public JavaDelegate calculateOrderPositions() {
      return execution -> {
        ORDER_TOTAL.on(execution).set(BigDecimal.valueOf(6.48));
      };
    }

    public JavaDelegate writeOrderTotal() {
      return execution -> {
      };
    }
  }

}
