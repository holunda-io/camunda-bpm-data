package io.holunda.camunda.bpm.data.example.process;

import com.google.common.collect.Lists;
import io.holunda.camunda.bpm.data.example.domain.Order;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.spring.boot.starter.test.helper.StandaloneInMemoryTestConfiguration;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

@Deployment(resources = "order_approval.bpmn")
public class GuardProcessTest {
    @Rule
    public final ProcessEngineRule rule = new StandaloneInMemoryTestConfiguration(
        Lists.newArrayList(new SpinProcessEnginePlugin())
    ).rule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
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

        thrown.expectMessage("Guard violated by execution '6' in activity 'Order created'");

        // manual start by-passing the factory
        rule.getRuntimeService().startProcessInstanceByKey(OrderApproval.KEY);
        fail("Should not get here");
    }

    @Test
    public void shouldFireExceptionApproveDecisionIsMissing() {

        thrown.expectMessage("Guard violated in task 'Approve order' (taskId: '21')");

        OrderApprovalInstanceFactory factory = new OrderApprovalInstanceFactory(rule.getRuntimeService());
        factory.start();

        // async after start
        Job asyncStart = rule.getManagementService().createJobQuery().singleResult();
        rule.getManagementService().executeJob(asyncStart.getId());

        Task task = rule.getTaskService().createTaskQuery().singleResult();
        rule.getTaskService().complete(task.getId());

        // async after user task
        Job job = rule.getManagementService().createJobQuery().singleResult();
        rule.getManagementService().executeJob(job.getId());

        fail("Should not get here");
    }

}
