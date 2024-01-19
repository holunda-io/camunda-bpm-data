package io.holunda.camunda.bpm.data.adapter.listofmaps

import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import org.camunda.bpm.engine.delegate.VariableScope
import java.util.*

/**
 * Read-write adapter for variable scope.
 *
 * @param [K] key type.
 * @param [V] value type.
 * @param variableScope variable scope to access.
 * @param variableName  variable to access.
 * @param keyClazz key class.
 * @param valueClazz value class.
 */
class ListOfMapsReadWriteAdapterVariableScope<K, V>(
  private val variableScope: VariableScope,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractListOfMapsReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  override fun getOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)))
  }

  override fun set(value: List<Map<K, V>>, isTransient: Boolean) {
    variableScope.setVariable(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)))
  }

  override fun setLocal(value: List<Map<K, V>>, isTransient: Boolean) {
    variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    variableScope.removeVariable(variableName)
  }

  override fun removeLocal() {
    variableScope.removeVariableLocal(variableName)
  }
}
