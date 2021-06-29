package io.holunda.camunda.bpm.data.mockito

import org.mockito.kotlin.mock
import io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.TaskService
import org.junit.Test
import java.util.*

/**
 * This test demonstrates the mocking capabilities.
 */
class TaskServiceMockingTest {

  companion object {
    val ORDER_ID = stringVariable("orderId")
    val ORDER_FLAG = booleanVariable("orderFlag")
  }

  private val taskService = mock<TaskService>()
  private val testService = TaskServiceAwareService(taskService)

  @Test
  fun should_mock_task_service() {

    val taskId = UUID.randomUUID().toString()

    TaskServiceVariableMockBuilder(taskService)
      .initial(ORDER_ID, "initial-Value")
      .define(ORDER_FLAG)
      .build()

    testService.writeOrderId(taskId, "4712")
    val orderId = testService.readOrderId(taskId)

    assertThat(testService.localFlagExists(taskId)).isFalse
    testService.writeLocalFlag(taskId, true)
    assertThat(testService.localFlagExists(taskId)).isTrue
    val orderFlag = testService.readLocalFlag(taskId)

    assertThat(orderId).isEqualTo("4712")
    assertThat(orderFlag).isEqualTo(true)
  }


  /**
   * Test service.
   * @param taskService task service to work on.
   */
  class TaskServiceAwareService(val taskService: TaskService) {

    fun readOrderId(taskId: String): String =
      ORDER_ID.from(taskService, taskId).get()

    fun writeOrderId(taskId: String, value: String) =
      ORDER_ID.on(taskService, taskId).set(value)

    fun localFlagExists(taskId: String) =
      taskService.getVariablesLocal(taskId).containsKey(ORDER_FLAG.name)

    fun writeLocalFlag(taskId: String, flag: Boolean) =
      ORDER_FLAG.on(taskService, taskId).setLocal(flag)

    fun readLocalFlag(taskId: String): Boolean =
      ORDER_FLAG.from(taskService, taskId).local
  }
}
