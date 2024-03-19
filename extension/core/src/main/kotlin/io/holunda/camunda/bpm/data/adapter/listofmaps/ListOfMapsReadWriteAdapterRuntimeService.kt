package io.holunda.camunda.bpm.data.adapter.listofmaps

import java.util.*
import org.camunda.bpm.engine.RuntimeService

/**
 * Read write adapter for runtime service access.
 *
 * @param [K] key type.
 * @param [V] value type.
 * @param runtimeService runtime service to use.
 * @param executionId id of the execution to read from and write to.
 * @param variableName name of the variable.
 * @param keyClazz key class.
 * @param valueClazz value class.
 */
class ListOfMapsReadWriteAdapterRuntimeService<K, V>(
    private val runtimeService: RuntimeService,
    private val executionId: String,
    variableName: String,
    keyClazz: Class<K>,
    valueClazz: Class<V>
) : AbstractListOfMapsReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  override fun getOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName)))
  }

  override fun set(value: List<Map<K, V>>, isTransient: Boolean) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(
        getOrNull(runtimeService.getVariableLocal(executionId, variableName)))
  }

  override fun setLocal(value: List<Map<K, V>>, isTransient: Boolean) {
    runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    runtimeService.removeVariable(executionId, variableName)
  }

  override fun removeLocal() {
    runtimeService.removeVariableLocal(executionId, variableName)
  }
}
