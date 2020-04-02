package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.taskServiceMockVerifier
import io.holunda.camunda.bpm.data.mockito.CamundaBpmDataMockito.taskServiceVariableMockBuilder
import io.holunda.camunda.bpm.data.set
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Before
import org.junit.Test
import java.util.*

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
    fun verifyGetSuccess() {
        taskServiceVariableMockBuilder(taskService).inital(VAR, "value").build()
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        VAR.from(taskService, taskId).get()
        verifier.verifyGet(VAR, taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun testVerifyGetLocal() {
        taskServiceVariableMockBuilder(taskService).initialLocal(VAR, "value").build()
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        VAR.from(taskService, taskId).local
        verifier.verifyGetLocal(VAR, taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun verifySetSuccess() {
        taskServiceVariableMockBuilder(taskService).define(VAR).build()
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        VAR.on(taskService, taskId).set("value")
        verifier.verifySet(VAR, "value", taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun testVerifySetLocal() {
        taskServiceVariableMockBuilder(taskService).define(VAR).build()
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        VAR.on(taskService, taskId).setLocal("valueLocal")
        verifier.verifySetLocal(VAR, "valueLocal", taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun verifyRemoveSuccess() {
        taskServiceVariableMockBuilder(taskService).inital(VAR, "value").build()
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        VAR.on(taskService, taskId).remove()
        verifier.verifyRemove(VAR, taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun testVerifyRemoveLocal() {
        taskServiceVariableMockBuilder(taskService).initialLocal(VAR, "localValue").build()
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        VAR.on(taskService, taskId).remove()
        verifier.verifyRemoveLocal(VAR, taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun testVerifyComplete() {
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        taskService.complete(taskId)
        verifier.verifyComplete(taskId)
        verifier.verifyNoMoreInteractions()
    }

    @Test
    fun testVerifyCompleteWithVars() {
        val verifier = taskServiceMockVerifier(taskService)
        val taskId = UUID.randomUUID().toString()
        val vars = createVariables()
            .set(VAR, "someValue")
        taskService.complete(taskId, vars)
        verifier.verifyComplete(vars, taskId)
        verifier.verifyNoMoreInteractions()
    }


}