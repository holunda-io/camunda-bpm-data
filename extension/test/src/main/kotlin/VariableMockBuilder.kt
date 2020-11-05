package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockitokotlin2.whenever
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.delegate.VariableScope

/**
 * Mocks process variables within a given Variable scope
 */
class VariableMockBuilder {
  /**
   * Mocks the given variable globally with the given value within the given @VariableScope
   */
  fun <T> set(variableScope: VariableScope, variable: VariableFactory<T>, value: T) {
    whenever(variableScope.getVariable(variable.name)).thenReturn(value)
  }

  /**
   * Mocks the given variable locally with the given value within the given @VariableScope
   */
  fun <T> setLocal(variableScope: VariableScope, variable: VariableFactory<T>, value: T) {
    whenever(variableScope.getVariableLocal(variable.name)).thenReturn(value)
  }
}
