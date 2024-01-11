package io.holunda.camunda.bpm.data.acl.apply

import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Replaces variables of local scope with given variable map.
 */
object LocalScopeReplaceStrategy : ValueApplicationStrategy {

  override fun apply(variableMap: VariableMap, variableScope: VariableScope): VariableScope =
    variableScope.apply {
      this.variablesLocal = variableMap
    }

  override fun toString(): String {
    return javaClass.canonicalName
  }
}
