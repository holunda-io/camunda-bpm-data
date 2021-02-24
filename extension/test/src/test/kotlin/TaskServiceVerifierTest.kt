package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.taskServiceMockVerifier
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.taskServiceVariableMockBuilder
import io.holunda.camunda.bpm.data.set
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.concurrent.timer

class TaskServiceVerifierTest {

  companion object {
    val VAR = stringVariable("var")
  }

  private val taskService = mock<TaskService>()

  @Before
  fun resetMocks() {
    reset(taskService)
  }

  @Test
  fun verifyGet() {
    taskServiceVariableMockBuilder(taskService).initial(VAR, "value").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.from(taskService, taskId).get()
    verifier.verifyGet(VAR, taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetLocal() {
    taskServiceVariableMockBuilder(taskService).initialLocal(VAR, "value").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.from(taskService, taskId).local
    verifier.verifyGetLocal(VAR, taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySet() {
    taskServiceVariableMockBuilder(taskService).define(VAR).build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).set("value")
    verifier.verifySet(VAR, "value", taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetLocal() {
    taskServiceVariableMockBuilder(taskService).define(VAR).build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemove() {
    taskServiceVariableMockBuilder(taskService).initial(VAR, "value").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).remove()
    verifier.verifyRemove(VAR, taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveLocal() {
    taskServiceVariableMockBuilder(taskService).initialLocal(VAR, "localValue").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).remove()
    verifier.verifyRemoveLocal(VAR, taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetTimes() {
    taskServiceVariableMockBuilder(taskService).initial(VAR, "value").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.from(taskService, taskId).get()
    VAR.from(taskService, taskId).get()
    verifier.verifyGet(VAR, taskId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyGetLocalTimes() {
    taskServiceVariableMockBuilder(taskService).initialLocal(VAR, "value").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.from(taskService, taskId).local
    VAR.from(taskService, taskId).local
    verifier.verifyGetLocal(VAR, taskId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetTimes() {
    taskServiceVariableMockBuilder(taskService).define(VAR).build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).set("value")
    VAR.on(taskService, taskId).set("value")
    verifier.verifySet(VAR, "value", taskId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifySetLocalTimes() {
    taskServiceVariableMockBuilder(taskService).define(VAR).build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).setLocal("valueLocal")
    VAR.on(taskService, taskId).setLocal("valueLocal")
    verifier.verifySetLocal(VAR, "valueLocal", taskId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveTimes() {
    taskServiceVariableMockBuilder(taskService).initial(VAR, "value").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).remove()
    VAR.on(taskService, taskId).remove()
    verifier.verifyRemove(VAR, taskId, times(2))
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyRemoveLocalTimes() {
    taskServiceVariableMockBuilder(taskService).initialLocal(VAR, "localValue").build()
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    VAR.on(taskService, taskId).remove()
    VAR.on(taskService, taskId).remove()
    verifier.verifyRemoveLocal(VAR, taskId, times(2))
    verifier.verifyNoMoreInteractions()
  }


  @Test
  fun verifyComplete() {
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    taskService.complete(taskId)
    verifier.verifyComplete(taskId)
    verifier.verifyNoMoreInteractions()
  }

  @Test
  fun verifyCompleteWithVars() {
    val verifier = taskServiceMockVerifier(taskService)
    val taskId = UUID.randomUUID().toString()
    val vars = createVariables()
      .set(VAR, "someValue")
    taskService.complete(taskId, vars)
    verifier.verifyComplete(vars, taskId)
    verifier.verifyNoMoreInteractions()
  }
}
