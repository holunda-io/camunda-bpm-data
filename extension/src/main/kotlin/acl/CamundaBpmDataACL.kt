package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.acl.apply.GlobalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.apply.LocalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.transform.VariableMapTransformer
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Helper methods to create anti corruption layers.
 */
object CamundaBpmDataACL {

    /**
     * Constructs an ACL with a guard, maps variables using transformer and replaces them in a local scope.
     * @param variableName name of the transient variable to use.
     * @param variableMapTransformer transformer to map from external to internal representation.
     * @param variablesGuard preconditions protecting the ACL.
     */
    @JvmStatic
    fun guardTransformingLocalReplace(variableName: String, variablesGuard: VariablesGuard, variableMapTransformer: VariableMapTransformer) = AntiCorruptionLayer(
        precondition = variablesGuard,
        variableMapTransformer = variableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = LocalScopeReplaceStrategy
    )

    /**
     * Constructs an ACL with a guard, maps variables using transformer and replaces them in a global scope.
     * @param variableName name of the transient variable to use.
     * @param variableMapTransformer transformer to map from external to internal representation.
     * @param variablesGuard preconditions protecting the ACL.
     */
    @JvmStatic
    fun guardTransformingGlobalReplace(variableName: String, variablesGuard: VariablesGuard, variableMapTransformer: VariableMapTransformer) = AntiCorruptionLayer(
        precondition = variablesGuard,
        variableMapTransformer = variableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = GlobalScopeReplaceStrategy
    )

    /**
     * Constructs an ACL with a guard, maps variables using transformer and replaces them in a scope controlled by the .
     * @param variableName name of the transient variable to use.
     * @param local flag to control the scope.
     * @param variableMapTransformer transformer to map from external to internal representation.
     * @param variablesGuard preconditions protecting the ACL.
     */
    @JvmStatic
    fun guardTransformingReplace(variableName: String, local: Boolean, variablesGuard: VariablesGuard, variableMapTransformer: VariableMapTransformer) = if (local) {
        guardTransformingLocalReplace(variableName, variablesGuard, variableMapTransformer)
    } else {
        guardTransformingGlobalReplace(variableName, variablesGuard, variableMapTransformer)
    }

}