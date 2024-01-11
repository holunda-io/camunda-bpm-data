package io.holunda.camunda.bpm.data.adapter.set

import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Read write adapter for runtime service access.
 *
 * @param [T]             type of value.
 * @param caseService     case service to use.
 * @param caseExecutionId id of the execution to read from and write to.
 * @param variableName    name of the variable.
 * @param memberClazz     class of the variable.
 */
class SetReadWriteAdapterCaseService<T>(
  private val caseService: CaseService,
  private val caseExecutionId: String,
  variableName: String,
  memberClazz: Class<T>
) : AbstractSetReadWriteAdapter<T>(variableName, memberClazz) {
  override fun getOptional(): Optional<Set<T>> {
    return Optional.ofNullable(
      getOrNull(
        caseService.getVariable(
          caseExecutionId, variableName
        )
      )
    )
  }

  override fun set(value: Set<T>, isTransient: Boolean) {
    caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Set<T>> {
    return Optional.ofNullable(
      getOrNull(
        caseService.getVariableLocal(
          caseExecutionId, variableName
        )
      )
    )
  }

  override fun setLocal(value: Set<T>, isTransient: Boolean) {
    caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    caseService.removeVariable(caseExecutionId, variableName)
  }

  override fun removeLocal() {
    caseService.removeVariableLocal(caseExecutionId, variableName)
  }
}
