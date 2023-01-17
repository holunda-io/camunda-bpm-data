package io.holunda.camunda.bpm.data.example.rest;

import io.holunda.camunda.bpm.data.example.domain.Order;
import org.camunda.bpm.engine.TaskService;
import org.camunda.community.mockito.service.TaskServiceStubBuilder;
import org.camunda.community.mockito.verify.TaskServiceVerification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.builder;
import static io.holunda.camunda.bpm.data.example.process.OrderApproval.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * Demonstrates the usage of Task Service Variable Mock Builder and Task Service Verifier.
 */
public class ApproveOrderTaskControllerTest {

  private final static Order order = new Order("ORDER-ID-1", new Date(), new ArrayList<>());
  private final TaskService taskService = mock(TaskService.class);
  private final TaskServiceVerification verifier = new TaskServiceVerification(taskService);
  private final ApproveOrderTaskController controller = new ApproveOrderTaskController(taskService);
  private String taskId;

  @BeforeEach
  public void prepareTest() {
    reset(taskService);
    taskId = UUID.randomUUID().toString();
  }

  @Test
  public void testLoadTask() {
    // given
    new TaskServiceStubBuilder(taskService)
      .defineAndInitialize(ORDER, order)
      .defineAndInitialize(ORDER_TOTAL, BigDecimal.ZERO)
      .build();
    // when
    ResponseEntity<ApproveTaskDto> responseEntity = controller.loadTask(taskId);
    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isEqualTo(new ApproveTaskDto(order, BigDecimal.ZERO));
    verifier.verifyGet(ORDER, taskId);
    verifier.verifyGet(ORDER_TOTAL, taskId);
    verifier.verifyNoMoreInteractions();
  }

  @Test
  public void testCompleteTask() {
    // when
    ApproveTaskCompleteDto response = new ApproveTaskCompleteDto(true);
    ResponseEntity<Void> responseEntity = controller.completeTask(taskId, response);
    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verifier.verifyComplete(builder().set(ORDER_APPROVED, response.getApproved()).build(), taskId);
    verifier.verifyNoMoreInteractions();
  }

}
