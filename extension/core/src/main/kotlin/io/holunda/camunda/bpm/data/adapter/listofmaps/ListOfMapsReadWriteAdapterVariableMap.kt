package io.holunda.camunda.bpm.data.adapter.listofmaps

import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Read-write adapter for variable map.
 *
 * @param [K] key type.
 * @param [V] value type.
 * @param variableMap  variable map to access.
 * @param variableName variable to access.
 * @param keyClazz key class.
 * @param valueClazz value class.
</T> */
class ListOfMapsReadWriteAdapterVariableMap<K, V>(
  private val variableMap: VariableMap,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractListOfMapsReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  override fun getOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(getOrNull(variableMap[variableName]))
  }

  override fun set(value: List<Map<K, V>>, isTransient: Boolean) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<Map<K, V>>> {
    throw UnsupportedOperationException("Can't get a local variable on a variable map")
  }

  override fun setLocal(value: List<Map<K, V>>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }

  override fun remove() {
    variableMap.remove(variableName)
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }
}
