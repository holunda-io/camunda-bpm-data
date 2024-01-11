package io.holunda.camunda.bpm.data.adapter.map

import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Read write adapter for case service access.
 *
 * @param [K] type of key.
 * @param [V] type of value.
 * @param caseService     case service to use.
 * @param caseExecutionId id of the execution to read from and write to.
 * @param variableName    name of the variable.
 * @param keyClazz        class of the key variable.
 * @param valueClazz      class of the value variable.
</V></K> */
class MapReadWriteAdapterCaseService<K, V>(
    private val caseService: CaseService,
    private val caseExecutionId: String,
    variableName: String,
    keyClazz: Class<K>,
    valueClazz: Class<V>
) : AbstractMapReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {
    override fun getOptional(): Optional<Map<K, V>> {
        return Optional.ofNullable(
            getOrNull(
                caseService.getVariable(
                    caseExecutionId, variableName
                )
            )
        )
    }

    override fun set(value: Map<K, V>, isTransient: Boolean) {
        caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient))
    }

    override fun getLocalOptional(): Optional<Map<K, V>> {
        return Optional.ofNullable(
            getOrNull(
                caseService.getVariableLocal(
                    caseExecutionId, variableName
                )
            )
        )
    }

    override fun setLocal(value: Map<K, V>, isTransient: Boolean) {
        caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient))
    }

    override fun remove() {
        caseService.removeVariable(caseExecutionId, variableName)
    }

    override fun removeLocal() {
        caseService.removeVariableLocal(caseExecutionId, variableName)
    }
}
