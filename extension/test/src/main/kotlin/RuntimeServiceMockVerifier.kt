package io.holunda.camunda.bpm.data.mockito

import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService
import org.mockito.verification.VerificationMode

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
   * @param mode verification mode.
   * @param T type of variable.
   */
  fun <T> verifySet(variableFactory: VariableFactory<T>, value: T, executionId: String, mode: VerificationMode) {
    verify(runtimeService, mode).setVariable(executionId, variableFactory.name, variableFactory.on(runtimeService, executionId).getTypedValue(value, false))
  }

  /**
   * Verifies if the variable has been set globally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifySet(variableFactory: VariableFactory<T>, value: T, executionId: String) {
    verifySet(variableFactory, value, executionId, times(1))
  }

  /**
   * Verifies if the variable has been set locally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifySetLocal(variableFactory: VariableFactory<T>, value: T, executionId: String, mode: VerificationMode) {
    verify(runtimeService, mode).setVariableLocal(executionId, variableFactory.name, variableFactory.on(runtimeService, executionId).getTypedValue(value, false))
  }

  /**
   * Verifies if the variable has been set locally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifySetLocal(variableFactory: VariableFactory<T>, value: T, executionId: String) {
    verifySetLocal(variableFactory, value, executionId, times(1))
  }

  /**
   * Verifies if the variable has been retrieved from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyGet(variableFactory: VariableFactory<T>, executionId: String, mode: VerificationMode) {
    verify(runtimeService, mode).getVariable(executionId, variableFactory.name)
  }

  fun <T> verifyGet(variableFactory: VariableFactory<T>, executionId: String) {
    verifyGet(variableFactory, executionId, times(1))
  }

  /**
   * Verifies if the variable has been retrieved from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyGetLocal(variableFactory: VariableFactory<T>, executionId: String, mode: VerificationMode) {
    verify(runtimeService, mode).getVariableLocal(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been retrieved from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyGetLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verifyGetLocal(variableFactory, executionId, times(1))
  }

  /**
   * Verifies if the variable has been removed from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyRemove(variableFactory: VariableFactory<T>, executionId: String, mode: VerificationMode) {
    verify(runtimeService, mode).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyRemove(variableFactory: VariableFactory<T>, executionId: String) {
    verifyRemove(variableFactory, executionId, times(1))
  }

  /**
   * Verifies if the variable has been removed from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyRemoveLocal(variableFactory: VariableFactory<T>, executionId: String, mode: VerificationMode) {
    verify(runtimeService, mode).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifyRemoveLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verifyRemoveLocal(variableFactory, executionId, times(1))
  }

  /**
   * Verifies no more interaction has been performed with the mock.
   * @see com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
   */
  fun verifyNoMoreInteractions() {
    verifyNoMoreInteractions(runtimeService)
  }
}
