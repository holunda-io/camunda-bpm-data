package io.holunda.camunda.bpm.data.mockito

import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.runtimeServiceMockVerifier
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.runtimeServiceVariableMockBuilder
import org.camunda.bpm.engine.RuntimeService
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.exceptions.verification.TooManyActualInvocations
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
  fun verifyGet() {
    runtimeServiceVariableMockBuilder(runtimeService).initial(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(runtimeService, executionId).get()
    verifier.verifyGet(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetTimes() {
    runtimeServiceVariableMockBuilder(runtimeService).initial(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(runtimeService, executionId).get()
    VAR.from(runtimeService, executionId).get()
    assertThrows(TooManyActualInvocations::class.java) {
      verifier.verifyGet(VAR, executionId)
    }
    verifier.verifyGet(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetLocal() {
    runtimeServiceVariableMockBuilder(runtimeService).initialLocal(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(runtimeService, executionId).local
    verifier.verifyGetLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetLocalTimes() {
    runtimeServiceVariableMockBuilder(runtimeService).initialLocal(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(runtimeService, executionId).local
    VAR.from(runtimeService, executionId).local
    verifier.verifyGetLocal(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySet() {
    runtimeServiceVariableMockBuilder(runtimeService).define(VAR).build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).set("value")
    verifier.verifySet(VAR, "value", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetLocal() {
    runtimeServiceVariableMockBuilder(runtimeService).define(VAR).build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemove() {
    runtimeServiceVariableMockBuilder(runtimeService).initial(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).remove()
    verifier.verifyRemove(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveLocal() {
    runtimeServiceVariableMockBuilder(runtimeService).initialLocal(VAR, "localValue").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).remove()
    verifier.verifyRemoveLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetTimes() {
    runtimeServiceVariableMockBuilder(runtimeService).define(VAR).build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).set("value")
    VAR.on(runtimeService, executionId).set("value")
    verifier.verifySet(VAR, "value", executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetLocalTimes() {
    runtimeServiceVariableMockBuilder(runtimeService).define(VAR).build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).setLocal("valueLocal")
    VAR.on(runtimeService, executionId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveTimes() {
    runtimeServiceVariableMockBuilder(runtimeService).initial(VAR, "value").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).remove()
    VAR.on(runtimeService, executionId).remove()
    verifier.verifyRemove(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveLocalTimes() {
    runtimeServiceVariableMockBuilder(runtimeService).initialLocal(VAR, "localValue").build()
    val verifier = runtimeServiceMockVerifier(runtimeService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(runtimeService, executionId).remove()
    VAR.on(runtimeService, executionId).remove()
    verifier.verifyRemoveLocal(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

}
