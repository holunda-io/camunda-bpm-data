package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService
import org.camunda.community.mockito.service.RuntimeServiceStubBuilder
import org.camunda.community.mockito.verify.RuntimeServiceVerification
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import java.util.*

/**
 * Test to verify that update is not touching a variable is the value has not changed.
 */
class SmartUpdateTest {

  companion object {
    val MY_VAR: VariableFactory<String> = stringVariable("myVar")
  }

  @Test
  fun should_not_touch_global() {
    val execId = UUID.randomUUID().toString()
    val runtime: RuntimeService = mock()
    val verifier = RuntimeServiceVerification(runtime)
    RuntimeServiceStubBuilder(runtime).defineAndInitialize(MY_VAR, "value").build()
    MY_VAR.on(runtime, execId).update { "value" }
    verifier.verifyGet(MY_VAR, execId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun should_not_touch_local() {
    val execId = UUID.randomUUID().toString()
    val runtime: RuntimeService = mock()
    val verifier = RuntimeServiceVerification(runtime)
    RuntimeServiceStubBuilder(runtime).defineAndInitializeLocal(MY_VAR, "value").build()
    MY_VAR.on(runtime, execId).updateLocal { "value" }
    verifier.verifyGetLocal(MY_VAR, execId)
    verifier.verifyNoMoreInteractions()
  }
}
