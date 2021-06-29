package io.holunda.camunda.bpm.data.mockito

import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.CaseService
import org.mockito.verification.VerificationMode

/**
 * Verifier for a mocked runtime service.
 * Provides methods for easy verification.
 */
class CaseServiceMockVerifier(
  private val caseService: CaseService
) {

  /**
   * Verifies if the variable has been set globally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   * @param mode verification mode.
   */
  fun <T> verifySet(variableFactory: VariableFactory<T>, value: T, executionId: String, mode: VerificationMode) {
    verify(caseService, mode).setVariable(executionId, variableFactory.name, variableFactory.on(caseService, executionId).getTypedValue(value, false))
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
    verify(caseService, mode).setVariableLocal(executionId, variableFactory.name, variableFactory.on(caseService, executionId).getTypedValue(value, false))
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
    verify(caseService, mode).getVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been retrieved from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
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
    verify(caseService, mode).getVariableLocal(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been retrieved from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
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
    verify(caseService, mode).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
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
    verify(caseService, mode).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyRemoveLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verifyRemoveLocal(variableFactory, executionId, times(1))
  }

  /**
   * Verifies no more interaction has been performed with the mock.
   * @see com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
   */
  fun verifyNoMoreInteractions() {
    verifyNoMoreInteractions(caseService)
  }
}
