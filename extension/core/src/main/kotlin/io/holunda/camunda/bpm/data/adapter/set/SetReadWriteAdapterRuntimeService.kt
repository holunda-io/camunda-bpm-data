package io.holunda.camunda.bpm.data.adapter.set

import org.camunda.bpm.engine.RuntimeService
import java.util.*

/**
 * Read write adapter for runtime service access.
 *
 * @param [T] type of value.
 * @param runtimeService runtime service to use.
 * @param executionId    id of the execution to read from and write to.
 * @param variableName   name of the variable.
 * @param memberClazz    class of the variable.
 */
class SetReadWriteAdapterRuntimeService<T>(
  private val runtimeService: RuntimeService,
  private val executionId: String,
  variableName: String,
  memberClazz: Class<T>
) : AbstractSetReadWriteAdapter<T>(variableName, memberClazz) {
  override fun getOptional(): Optional<Set<T>> {
    return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName)))
  }

  override fun set(value: Set<T>, isTransient: Boolean) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Set<T>> {
    return Optional.ofNullable(
      getOrNull(
        runtimeService.getVariableLocal(
          executionId, variableName
        )
      )
    )
  }

  override fun setLocal(value: Set<T>, isTransient: Boolean) {
    runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    runtimeService.removeVariable(executionId, variableName)
  }

  override fun removeLocal() {
    runtimeService.removeVariableLocal(executionId, variableName)
  }
}
