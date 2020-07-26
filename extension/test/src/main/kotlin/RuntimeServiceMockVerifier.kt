package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService

/**
 * Verifier for a mocked runtime service.
 * Provides methods for easy verification.
 */
class RuntimeServiceMockVerifier(
  private val runtimeService: RuntimeService
) {

  /**
   * Verifies if the variable has been set globally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifySet(variableFactory: VariableFactory<T>, value: T, executionId: String) {
    verify(runtimeService).setVariable(executionId, variableFactory.name, variableFactory.on(runtimeService, executionId).getTypedValue(value, false))
  }

  /**
   * Verifies if the variable has been set locally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifySetLocal(variableFactory: VariableFactory<T>, value: T, executionId: String) {
    verify(runtimeService).setVariableLocal(executionId, variableFactory.name, variableFactory.on(runtimeService, executionId).getTypedValue(value, false))
  }

  /**
   * Verifies if the variable has been retrieved from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyGet(variableFactory: VariableFactory<T>, executionId: String) {
    verify(runtimeService).getVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been retrieved from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyGetLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verify(runtimeService).getVariableLocal(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyRemove(variableFactory: VariableFactory<T>, executionId: String) {
    verify(runtimeService).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyRemoveLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verify(runtimeService).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies no more interaction has been performed with the mock.
   * @see com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
   */
  fun verifyNoMoreInteractions() {
    verifyNoMoreInteractions(runtimeService)
  }
}
