package io.holunda.camunda.bpm.data.adapter.basic

import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Read write adapter for case service access.
 *
 * @param [T] type of value.
 * @param caseService     case service to use.
 * @param caseExecutionId id of the execution to read from and write to.
 * @param variableName    name of the variable.
 * @param clazz           class of the variable.
 */
class ReadWriteAdapterCaseService<T : Any?>(
  private val caseService: CaseService,
  private val caseExecutionId: String,
  variableName: String,
  clazz: Class<T>
) : AbstractBasicReadWriteAdapter<T>(variableName, clazz) {

  override fun getOptional(): Optional<T> {
    return Optional.ofNullable(getOrNull(caseService.getVariable(caseExecutionId, variableName))) as Optional<T>
  }

  override fun set(value: T, isTransient: Boolean) {
    caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<T> {
    return Optional.ofNullable(getOrNull(caseService.getVariableLocal(caseExecutionId, variableName))) as Optional<T>
  }

  override fun setLocal(value: T, isTransient: Boolean) {
    caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    caseService.removeVariable(caseExecutionId, variableName)
  }

  override fun removeLocal() {
    caseService.removeVariableLocal(caseExecutionId, variableName)
  }
}
