package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
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
  fun verifyGetSuccess() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initial(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(caseService, executionId).get()
    verifier.verifyGet(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun testVerifyGetLocal() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initialLocal(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.from(caseService, executionId).local
    verifier.verifyGetLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetSuccess() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).define(VAR).build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).set("value")
    verifier.verifySet(VAR, "value", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun testVerifySetLocal() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).define(VAR).build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveSuccess() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initial(VAR, "value").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).remove()
    verifier.verifyRemove(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun testVerifyRemoveLocal() {
    CamundaBpmDataMockito.caseServiceVariableMockBuilder(caseService).initialLocal(VAR, "localValue").build()
    val verifier = CamundaBpmDataMockito.caseServiceMockVerifier(caseService)
    val executionId = UUID.randomUUID().toString()
    VAR.on(caseService, executionId).remove()
    verifier.verifyRemoveLocal(VAR, executionId)
    verifier.verifyNoMoreInteractions()
  }
}
