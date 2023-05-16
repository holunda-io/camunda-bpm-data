package io.holunda.camunda.bpm.data.adapter.list

import org.camunda.bpm.engine.delegate.VariableScope
import java.util.*

/**
 * Read-write adapter for variable scope.
 *
 * @param [T] type of value.
 * @param variableScope variable scope to access.
 * @param variableName  variable to access.
 * @param memberClazz   class of member variable value.
 */
class ListReadWriteAdapterVariableScope<T>(
  private val variableScope: VariableScope,
  variableName: String,
  memberClazz: Class<T>
) : AbstractListReadWriteAdapter<T>(variableName, memberClazz) {

  override fun getOptional(): Optional<List<T>> {
    return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)))
  }

  override fun set(value: List<T>, isTransient: Boolean) {
    variableScope.setVariable(variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<T>> {
    return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)))
  }

  override fun setLocal(value: List<T>, isTransient: Boolean) {
    variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    variableScope.removeVariable(variableName)
  }

  override fun removeLocal() {
    variableScope.removeVariableLocal(variableName)
  }
}
