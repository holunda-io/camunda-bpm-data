package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.runtimeServiceMockVerifier
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.runtimeServiceVariableMockBuilder
import org.camunda.bpm.engine.RuntimeService
import org.junit.Before
import org.junit.Test
import java.util.*

class RuntimeServiceVerifierTest {

  companion object {
    val VAR = stringVariable("var")
  }

  private val runtimeService = mock<RuntimeService>()

  @Before
  fun resetMocks() {
    reset(runtimeService)
  }

  @Test
  fun verifyGetSuccess() {
    runtimeServiceVariableMockBuilder(runtimeService).initial(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(runtimeService, executionId).get()
    verifier.verifyGet(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun testVerifyGetLocal() {
    runtimeServiceVariableMockBuilder(runtimeService).initialLocal(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(runtimeService, executionId).local
    verifier.verifyGetLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetSuccess() {
    runtimeServiceVariableMockBuilder(runtimeService).define(VAR).build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).set("value")
    verifier.verifySet(VAR, "value", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun testVerifySetLocal() {
    runtimeServiceVariableMockBuilder(runtimeService).define(VAR).build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveSuccess() {
    runtimeServiceVariableMockBuilder(runtimeService).initial(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).remove()
    verifier.verifyRemove(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun testVerifyRemoveLocal() {
    runtimeServiceVariableMockBuilder(runtimeService).initialLocal(VAR, "localValue").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).remove()
    verifier.verifyRemoveLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }
}
