package io.holunda.camunda.bpm.data.acl.transform

import org.camunda.bpm.engine.variable.VariableMap

/**
 * Performs no transformation (1:1 mapping).
 */
object IdentityVariableMapTransformer : VariableMapTransformer {
    override fun transform(variableMap: VariableMap): VariableMap = variableMap
}