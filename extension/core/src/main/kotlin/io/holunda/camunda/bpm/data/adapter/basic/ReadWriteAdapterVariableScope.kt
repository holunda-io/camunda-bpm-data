package io.holunda.camunda.bpm.data.adapter.basic

import org.camunda.bpm.engine.delegate.VariableScope
import java.util.*

/**
 * Read-write adapter for variable scope.
 *
 * @param [T] type of value.
 * @param variableScope variable scope to access.
 * @param variableName  variable to access.
 * @param clazz         class of variable value.
 */
class ReadWriteAdapterVariableScope<T: Any>(
  private val variableScope: VariableScope,
  variableName: String,
  clazz: Class<T>
) : AbstractBasicReadWriteAdapter<T>(variableName, clazz) {
  override fun set(value: T, isTransient: Boolean) {
    variableScope.setVariable(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<T> {
    return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)))
  }

  override fun setLocal(value: T, isTransient: Boolean) {
    variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient))
  }

  override fun getOptional(): Optional<T> {
    return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)))
  }

  override fun remove() {
    variableScope.removeVariable(variableName)
  }

  override fun removeLocal() {
    variableScope.removeVariableLocal(variableName)
  }
}
