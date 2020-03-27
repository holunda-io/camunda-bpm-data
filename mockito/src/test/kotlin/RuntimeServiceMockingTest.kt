package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.mock
import io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.RuntimeService
import org.junit.Test
import java.util.*

/**
 * This test demonstrates the mocking capabilities.
 */
class RuntimeServiceMockingTest {

    companion object {
        val ORDER_ID = stringVariable("orderId")
        val ORDER_FLAG = booleanVariable("orderFlag")
    }

    private val runtimeService = mock<RuntimeService>()
    private val testService = RuntimeServiceAwareService(runtimeService)

    @Test
    fun should_mock_task_service() {

        val executionId = UUID.randomUUID().toString()

        RuntimeServiceVariableMockBuilder(runtimeService)
            .setLocal(ORDER_ID, "initial-Value")
            .define(ORDER_FLAG)
            .build()

        testService.writeLocalOrderId(executionId, "4712")
        val orderId = testService.readLocalOrderId(executionId)

        assertThat(testService.flagExists(executionId)).isFalse()
        testService.writeFlag(executionId, true)
        assertThat(testService.flagExists(executionId)).isTrue()
        val orderFlag = testService.readFlag(executionId)

        assertThat(orderId).isEqualTo("4712")
        assertThat(orderFlag).isEqualTo(true)
    }


    /**
     * Test service.
     * @param runtimeService task service to work on.
     */
    class RuntimeServiceAwareService(val runtimeService: RuntimeService) {

        fun readLocalOrderId(executionId: String): String =
            ORDER_ID.from(runtimeService, executionId).local

        fun writeLocalOrderId(executionId: String, value: String) =
            ORDER_ID.on(runtimeService, executionId).setLocal(value)

        fun flagExists(executionId: String) =
            runtimeService.getVariables(executionId).containsKey(ORDER_FLAG.name)

        fun writeFlag(executionId: String, flag: Boolean) =
            ORDER_FLAG.on(runtimeService, executionId).set(flag)

        fun readFlag(executionId: String): Boolean =
            ORDER_FLAG.from(runtimeService, executionId).get()
    }
}