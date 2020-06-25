package io.holunda.camunda.bpm.data.acl.transform

import org.camunda.bpm.engine.variable.VariableMap

/**
 * Transforms values to the internal representation protected by the ACL.
 */
@FunctionalInterface
interface VariableMapTransformer {
    /**
     * Performs transformation on variable map.
     * @param variableMap containing the values.
     * @return new variable map
     */
    fun transform(variableMap: VariableMap): VariableMap
}