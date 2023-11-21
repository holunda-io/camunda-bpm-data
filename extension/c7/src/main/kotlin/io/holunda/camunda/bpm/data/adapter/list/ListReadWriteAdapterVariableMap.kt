package io.holunda.camunda.bpm.data.adapter.list

import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Read-write adapter for variable map.
 *
 * @param <T> type of value.
 * @param variableMap  variable map to access.
 * @param variableName variable to access.
 * @param memberClazz  class of variable value.
</T> */
class ListReadWriteAdapterVariableMap<T>(
  private val variableMap: VariableMap,
  variableName: String,
  memberClazz: Class<T>
) : AbstractListReadWriteAdapter<T>(
  variableName, memberClazz
) {
  override fun getOptional(): Optional<List<T>> {
    return Optional.ofNullable(getOrNull(variableMap[variableName]))
  }

  override fun set(value: List<T>, isTransient: Boolean) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<T>> {
    throw UnsupportedOperationException("Can't get a local variable on a variable map")
  }

  override fun setLocal(value: List<T>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }

  override fun remove() {
    variableMap.remove(variableName)
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }
}
