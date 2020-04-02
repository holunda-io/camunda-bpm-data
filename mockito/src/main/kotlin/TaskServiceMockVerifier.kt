package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Verifier for a mocked task service.
 * Provides methods for easy verification.
 */
class TaskServiceMockVerifier(
    private val taskService: TaskService
) {

    /**
     * Verifies if the variable has been set globally.
     * @param variableFactory factory defining the variable.
     * @param value value to set.
     * @param taskId task id.
     * @param T type of variable.
     */
    fun <T> verifySet(variableFactory: VariableFactory<T>, value: T, taskId: String) {
        verify(taskService).setVariable(taskId, variableFactory.name, variableFactory.on(taskService, taskId).getTypedValue(value, false))
    }

    /**
     * Verifies if the variable has been set locally.
     * @param variableFactory factory defining the variable.
     * @param value value to set.
     * @param taskId task id.
     * @param T type of variable.
     */
    fun <T> verifySetLocal(variableFactory: VariableFactory<T>, value: T, taskId: String) {
        verify(taskService).setVariableLocal(taskId, variableFactory.name, variableFactory.on(taskService, taskId).getTypedValue(value, false))
    }

    /**
     * Verifies if the variable has been retrieved from a global scope.
     * @param variableFactory factory defining the variable.
     * @param taskId task id.
     * @param T type of variable.
     */
    fun <T> verifyGet(variableFactory: VariableFactory<T>, taskId: String) {
        verify(taskService).getVariable(taskId, variableFactory.name)
    }

    /**
     * Verifies if the variable has been retrieved from a local scope.
     * @param variableFactory factory defining the variable.
     * @param taskId task id.
     * @param T type of variable.
     */
    fun <T> verifyGetLocal(variableFactory: VariableFactory<T>, taskId: String) {
        verify(taskService).getVariableLocal(taskId, variableFactory.name)
    }

    /**
     * Verifies if the variable has been removed from a global scope.
     * @param variableFactory factory defining the variable.
     * @param taskId task id.
     * @param T type of variable.
     */
    fun <T> verifyRemove(variableFactory: VariableFactory<T>, taskId: String) {
        verify(taskService).removeVariable(taskId, variableFactory.name)
    }

    /**
     * Verifies if the variable has been removed from a local scope.
     * @param variableFactory factory defining the variable.
     * @param taskId task id.
     * @param T type of variable.
     */
    fun <T> verifyRemoveLocal(variableFactory: VariableFactory<T>, taskId: String) {
        verify(taskService).removeVariable(taskId, variableFactory.name)
    }

    /**
     * Verifies if the task has been completed without variables.
     * @param taskId task id.
     */
    fun verifyComplete(taskId: String) {
        verify(taskService).complete(taskId)
    }

    /**
     * Verifies if the task has been completed with variables.
     * @param taskId task id.
     * @param variables variables to complete the task.
     */
    fun verifyComplete(variables: VariableMap, taskId: String) {
        verify(taskService).complete(taskId, variables)
    }

    /**
     * Verifies no more interaction has been performed with the mock.
     * @see com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
     */
    fun verifyNoMoreInteractions() {
        verifyNoMoreInteractions(taskService)
    }
}