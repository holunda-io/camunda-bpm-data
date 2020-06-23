package io.holunda.camunda.bpm.data.acl.transform

import org.camunda.bpm.engine.variable.VariableMap

object IdentityVariableMapTransformer : VariableMapTransformer {
    override fun transform(variableMap: VariableMap): VariableMap = variableMap
}