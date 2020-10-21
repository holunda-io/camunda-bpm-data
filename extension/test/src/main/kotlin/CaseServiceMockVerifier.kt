package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.CaseService

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
   */
  fun <T> verifySet(variableFactory: VariableFactory<T>, value: T, executionId: String) {
    verify(caseService).setVariable(executionId, variableFactory.name, variableFactory.on(caseService, executionId).getTypedValue(value, false))
  }

  /**
   * Verifies if the variable has been set locally.
   * @param variableFactory factory defining the variable.
   * @param value value to set.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifySetLocal(variableFactory: VariableFactory<T>, value: T, executionId: String) {
    verify(caseService).setVariableLocal(executionId, variableFactory.name, variableFactory.on(caseService, executionId).getTypedValue(value, false))
  }

  /**
   * Verifies if the variable has been retrieved from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyGet(variableFactory: VariableFactory<T>, executionId: String) {
    verify(caseService).getVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been retrieved from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyGetLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verify(caseService).getVariableLocal(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a global scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyRemove(variableFactory: VariableFactory<T>, executionId: String) {
    verify(caseService).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies if the variable has been removed from a local scope.
   * @param variableFactory factory defining the variable.
   * @param executionId execution id.
   * @param T type of variable.
   */
  fun <T> verifyRemoveLocal(variableFactory: VariableFactory<T>, executionId: String) {
    verify(caseService).removeVariable(executionId, variableFactory.name)
  }

  /**
   * Verifies no more interaction has been performed with the mock.
   * @see com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
   */
  fun verifyNoMoreInteractions() {
    verifyNoMoreInteractions(caseService)
  }
}
