package io.holunda.camunda.bpm.data.adapter.basic

import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Read-write adapter for variable map.
 *
 * @param [T] type of value.
 * @param variableMap  variable map to access.
 * @param variableName variable to access.
 * @param clazz        class of variable value.
 */
class ReadWriteAdapterVariableMap<T : Any?>(private val variableMap: VariableMap, variableName: String, clazz: Class<T>) :
  AbstractBasicReadWriteAdapter<T>(variableName, clazz) {

  override fun getOptional(): Optional<T> {
    @Suppress("UNCHECKED_CAST")
    return Optional.ofNullable(getOrNull(variableMap[variableName])) as Optional<T>
  }

  override fun set(value: T, isTransient: Boolean) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<T> {
    throw UnsupportedOperationException("Can't get a local variable on a variable map")
  }

  override fun setLocal(value: T, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }

  override fun remove() {
    variableMap.remove(variableName)
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't set a local variable on a variable map")
  }
}
