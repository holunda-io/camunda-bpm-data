package io.holunda.camunda.bpm.data.acl.apply

import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap


/**
 * Interface describing the strategy to assign values.
 */
@FunctionalInterface
interface ValueApplicationStrategy {
  /**
   * Strategy to assign variables stored in a map to the given variable scope.
   */
  fun apply(variableMap: VariableMap, variableScope: VariableScope): VariableScope
}
