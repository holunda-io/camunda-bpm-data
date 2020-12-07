package io.holunda.camunda.bpm.data.acl.apply

import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Replaces variables of (global) scope with given variable map.
 */
object GlobalScopeReplaceStrategy : ValueApplicationStrategy {

  override fun apply(variableMap: VariableMap, variableScope: VariableScope): VariableScope =
    variableScope.apply {
      this.variables = variableMap
    }

  override fun toString(): String {
    return javaClass.canonicalName
  }
}
