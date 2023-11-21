package io.holunda.camunda.bpm.data.adapter.map

import org.camunda.bpm.engine.RuntimeService
import java.util.*

/**
 * Read write adapter for runtime service access.
 *
 * @param [K] type of key.
 * @param [V] type of value.
 * @param runtimeService runtime service to use.
 * @param executionId    id of the execution to read from and write to.
 * @param variableName   name of the variable.
 * @param keyClazz       class of the key variable.
 * @param valueClazz     class of the value variable.
 */
class MapReadWriteAdapterRuntimeService<K, V>(
  private val runtimeService: RuntimeService,
  private val executionId: String,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractMapReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {
  override fun getOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(
      getOrNull(
        runtimeService.getVariable(
          executionId, variableName
        )
      )
    )
  }

  override fun set(value: Map<K, V>, isTransient: Boolean) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(
      getOrNull(
        runtimeService.getVariableLocal(
          executionId, variableName
        )
      )
    )
  }

  override fun setLocal(value: Map<K, V>, isTransient: Boolean) {
    runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    runtimeService.removeVariable(executionId, variableName)
  }

  override fun removeLocal() {
    runtimeService.removeVariableLocal(executionId, variableName)
  }
}
