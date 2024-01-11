package io.holunda.camunda.bpm.data.adapter.map

import org.camunda.bpm.engine.delegate.VariableScope
import java.util.*

/**
 * Read-write adapter for variable scope.
 *
 * @param [K] type of key.
 * @param [V] type of value.
 * @param variableScope variable scope to access.
 * @param variableName  variable to access.
 * @param keyClazz      class of variable key.
 * @param valueClazz    class of variable value.
 */
class MapReadWriteAdapterVariableScope<K, V>(
  private val variableScope: VariableScope,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractMapReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {
  override fun getOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)))
  }

  override fun set(value: Map<K, V>, isTransient: Boolean) {
    variableScope.setVariable(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)))
  }

  override fun setLocal(value: Map<K, V>, isTransient: Boolean) {
    variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    variableScope.removeVariable(variableName)
  }

  override fun removeLocal() {
    variableScope.removeVariableLocal(variableName)
  }
}
