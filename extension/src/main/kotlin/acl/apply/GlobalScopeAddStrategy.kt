package io.holunda.camunda.bpm.data.acl.apply

import io.holunda.camunda.bpm.data.acl.assign.ValueApplicationStrategy
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Adds variable map to global scope.
 */
object GlobalScopeAddStrategy : ValueApplicationStrategy {

    override fun apply(variableMap: VariableMap, variableScope: VariableScope): VariableScope =
        variableScope.apply {
            this.variables.putAll(variableMap)
        }
}