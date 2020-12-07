package io.holunda.camunda.bpm.data

import com.nhaarman.mockitokotlin2.mock
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.stringVariable
import io.holunda.camunda.bpm.data.mockito.RuntimeServiceMockVerifier
import io.holunda.camunda.bpm.data.mockito.RuntimeServiceVariableMockBuilder
import org.camunda.bpm.engine.RuntimeService
import org.junit.Test
import java.util.*

/**
 * Test to verify that update is not touching a variable is the value has not changed.
 */
class SmartUpdateTest {

  companion object {
    val MY_VAR = stringVariable("myVar")
  }

  @Test
  fun should_not_touch_global() {
    val execId = UUID.randomUUID().toString()
    val runtime: RuntimeService = mock()
    val verifier = RuntimeServiceMockVerifier(runtime)
    RuntimeServiceVariableMockBuilder(runtime).initial(MY_VAR, "value").build()
    MY_VAR.on(runtime, execId).update { "value" }
    verifier.verifyGet(MY_VAR, execId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun should_not_touch_local() {
    val execId = UUID.randomUUID().toString()
    val runtime: RuntimeService = mock()
    val verifier = RuntimeServiceMockVerifier(runtime)
    RuntimeServiceVariableMockBuilder(runtime).initialLocal(MY_VAR, "value").build()
    MY_VAR.on(runtime, execId).updateLocal { "value" }
    verifier.verifyGetLocal(MY_VAR, execId)
    verifier.verifyNoMoreInteractions()
  }
}
