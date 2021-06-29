package io.holunda.camunda.bpm.data.mockito

import org.mockito.kotlin.mock
import io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.CaseService
import org.junit.Test
import java.util.*

/**
 * This test demonstrates the mocking capabilities.
 */
class CaseServiceMockingTest {

  companion object {
    val ORDER_ID = stringVariable("orderId")
    val ORDER_FLAG = booleanVariable("orderFlag")
  }

  private val caseService = mock<CaseService>()
  private val testService = CaseServiceAwareService(caseService)

  @Test
  fun should_mock_task_service() {

    val executionId = UUID.randomUUID().toString()

    CaseServiceVariableMockBuilder(caseService)
      .initialLocal(ORDER_ID, "initial-Value")
      .define(ORDER_FLAG)
      .build()

    testService.writeLocalOrderId(executionId, "4712")
    val orderId = testService.readLocalOrderId(executionId)

    assertThat(testService.flagExists(executionId)).isFalse
    testService.writeFlag(executionId, true)
    assertThat(testService.flagExists(executionId)).isTrue
    val orderFlag = testService.readFlag(executionId)

    assertThat(orderId).isEqualTo("4712")
    assertThat(orderFlag).isEqualTo(true)
  }


  /**
   * Test service.
   * @param runtimeService task service to work on.
   */
  class CaseServiceAwareService(val caseService: CaseService) {

    fun readLocalOrderId(executionId: String): String =
      ORDER_ID.from(caseService, executionId).local

    fun writeLocalOrderId(executionId: String, value: String) =
      ORDER_ID.on(caseService, executionId).setLocal(value)

    fun flagExists(executionId: String) =
      caseService.getVariables(executionId).containsKey(ORDER_FLAG.name)

    fun writeFlag(executionId: String, flag: Boolean) =
      ORDER_FLAG.on(caseService, executionId).set(flag)

    fun readFlag(executionId: String): Boolean =
      ORDER_FLAG.from(caseService, executionId).get()
  }
}
