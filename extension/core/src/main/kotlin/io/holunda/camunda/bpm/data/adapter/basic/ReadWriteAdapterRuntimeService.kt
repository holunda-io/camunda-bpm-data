package io.holunda.camunda.bpm.data.adapter.basic

import org.camunda.bpm.engine.RuntimeService
import java.util.*

/**
 * Read write adapter for runtime service access.
 *
 * @param [T] type of value.
 * @param runtimeService runtime service to use.
 * @param executionId    id of the execution to read from and write to.
 * @param variableName   name of the variable.
 * @param clazz          class of the variable.
 */
class ReadWriteAdapterRuntimeService<T : Any?>(
  private val runtimeService: RuntimeService,
  private val executionId: String,
  variableName: String,
  clazz: Class<T>
) : AbstractBasicReadWriteAdapter<T>(variableName, clazz) {
  override fun getOptional(): Optional<T> {
    return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName))) as Optional<T>
  }

  override fun set(value: T, isTransient: Boolean) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<T> {
    return Optional.ofNullable(getOrNull(runtimeService.getVariableLocal(executionId, variableName))) as Optional<T>
  }

  override fun setLocal(value: T, isTransient: Boolean) {
    runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    runtimeService.removeVariable(executionId, variableName)
  }

  override fun removeLocal() {
    runtimeService.removeVariableLocal(executionId, variableName)
  }
}
