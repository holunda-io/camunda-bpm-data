package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.camunda.bpm.engine.CaseService
import org.junit.Before
import org.junit.Test
import java.util.*

class CaseServiceVerifierTest {

  companion object {
    val VAR = stringVariable("var")
  }

  private val caseService = mock<CaseService>()

  @Before
  fun resetMocks() {
    reset(caseService)
  }

  @Test
  fun verifyGet() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initial(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(caseService, executionId).get()
    verifier.verifyGet(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetTimes() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initial(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()

    VAR.from(caseService, executionId).get()
    VAR.from(caseService, executionId).get()

    verifier.verifyGet(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }


  @Test
  fun verifyGetLocal() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initialLocal(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(caseService, executionId).local
    verifier.verifyGetLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetLocalTimes() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initialLocal(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(caseService, executionId).local
    VAR.from(caseService, executionId).local
    verifier.verifyGetLocal(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySet() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).define(VAR).build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).set("value")
    verifier.verifySet(VAR, "value", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetTimes() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).define(VAR).build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).set("value")
    VAR.on(caseService, executionId).set("value")
    verifier.verifySet(VAR, "value", executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetLocal() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).define(VAR).build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetLocalTimes() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).define(VAR).build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).setLocal("valueLocal")
    VAR.on(caseService, executionId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemove() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initial(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).remove()
    verifier.verifyRemove(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveTimes() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initial(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).remove()
    VAR.on(caseService, executionId).remove()
    verifier.verifyRemove(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveLocal() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initialLocal(VAR, "localValue").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).remove()
    verifier.verifyRemoveLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveLocalTimes() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initialLocal(VAR, "localValue").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).remove()
    VAR.on(caseService, executionId).remove()
    verifier.verifyRemoveLocal(VAR, executionId, times(2))
    verifier.verifyNoMoreInteractions()
  }
}
