package io.holunda.camunda.bpm.data.adapter.map

import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Read-write adapter for variable map.
 *
 * @param [K]          type of key.
 * @param [V]          type of value.
 * @param variableMap  variable map to access.
 * @param variableName variable to access.
 * @param keyClazz     class of variable key.
 * @param valueClazz   class of variable value.
 */
class MapReadWriteAdapterVariableMap<K, V>(
  private val variableMap: VariableMap,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractMapReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {
  override fun getOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(getOrNull(variableMap[variableName]))
  }

  override fun set(value: Map<K, V>, isTransient: Boolean) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Map<K, V>> {
    throw UnsupportedOperationException("Can't get a local variable on a variable map")
  }

  override fun setLocal(value: Map<K, V>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }

  override fun remove() {
    variableMap.remove(variableName)
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }
}
