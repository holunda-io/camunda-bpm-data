package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.acl.apply.GlobalScopeAddStrategy
import io.holunda.camunda.bpm.data.acl.apply.GlobalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.apply.LocalScopeAddStrategy
import io.holunda.camunda.bpm.data.acl.apply.LocalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.transform.IdentityVariableMapTransformer
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
     * Constructs an ACL without a guard, maps variables using transformer and replaces them in a local scope.
     * @param variableName name of the transient variable to use.
     * @param variableMapTransformer transformer to map from external to internal representation.
     */
    @JvmStatic
    fun transformingLocalReplace(variableName: String, variableMapTransformer: VariableMapTransformer) = AntiCorruptionLayer(
        precondition = VariablesGuard.EMPTY,
        variableMapTransformer = variableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = LocalScopeReplaceStrategy
    )

    /**
     * Constructs an ACL without a guard, maps variables 1:1 and replaces them in a local scope.
     * @param variableName name of the transient variable to use.
     */
    @JvmStatic
    fun identityLocalReplace(variableName: String) = AntiCorruptionLayer(
        precondition = VariablesGuard.EMPTY,
        variableMapTransformer = IdentityVariableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = LocalScopeReplaceStrategy
    )

    /**
     * Constructs an ACL without a guard, maps variables 1:1 and replaces them in a global scope.
     * @param variableName name of the transient variable to use.
     */
    @JvmStatic
    fun identityGlobalReplace(variableName: String) = AntiCorruptionLayer(
        precondition = VariablesGuard.EMPTY,
        variableMapTransformer = IdentityVariableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = GlobalScopeReplaceStrategy
    )

    /**
     * Constructs an ACL without a guard, maps variables 1:1 and adds them in a global scope.
     * @param variableName name of the transient variable to use.
     */
    @JvmStatic
    fun identityGlobalAdd(variableName: String) = AntiCorruptionLayer(
        precondition = VariablesGuard.EMPTY,
        variableMapTransformer = IdentityVariableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = GlobalScopeAddStrategy
    )

    /**
     * Constructs an ACL without a guard, maps variables 1:1 and adds them in a local scope.
     * @param variableName name of the transient variable to use.
     */
    @JvmStatic
    fun identityLocalAdd(variableName: String) = AntiCorruptionLayer(
        precondition = VariablesGuard.EMPTY,
        variableMapTransformer = IdentityVariableMapTransformer,
        factory = CamundaBpmData.customVariable(variableName, VariableMap::class.java),
        valueApplicationStrategy = LocalScopeAddStrategy
    )

}