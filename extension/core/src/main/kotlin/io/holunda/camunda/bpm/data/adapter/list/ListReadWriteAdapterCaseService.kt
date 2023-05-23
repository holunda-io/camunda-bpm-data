package io.holunda.camunda.bpm.data.adapter.list

import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Read write adapter for case service access.
 *
 * @param [T] type of value.
 * @param caseService     case service to use.
 * @param caseExecutionId id of the execution to read from and write to.
 * @param variableName    name of the variable.
 * @param memberClazz     class of the variable.
 */
class ListReadWriteAdapterCaseService<T>(
  private val caseService: CaseService,
  private val caseExecutionId: String,
  variableName: String,
  memberClazz: Class<T>
) : AbstractListReadWriteAdapter<T>(variableName, memberClazz) {

  override fun getOptional(): Optional<List<T>> {
    return Optional.ofNullable(
      getOrNull(
        caseService.getVariable(
          caseExecutionId, variableName
        )
      )
    )
  }

  override fun set(value: List<T>, isTransient: Boolean) {
    caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<T>> {
    return Optional.ofNullable(
      getOrNull(
        caseService.getVariableLocal(
          caseExecutionId, variableName
        )
      )
    )
  }

  override fun setLocal(value: List<T>, isTransient: Boolean) {
    caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    caseService.removeVariable(caseExecutionId, variableName)
  }

  override fun removeLocal() {
    caseService.removeVariableLocal(caseExecutionId, variableName)
  }
}
