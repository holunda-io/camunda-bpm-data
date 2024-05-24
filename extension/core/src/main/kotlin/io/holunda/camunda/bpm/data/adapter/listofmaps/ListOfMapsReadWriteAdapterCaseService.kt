package io.holunda.camunda.bpm.data.adapter.listofmaps

import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Read write adapter for case service access.
 *
 * @param [K] key type.
 * @param [V] value type.
 * @param caseService     case service to use.
 * @param caseExecutionId id of the execution to read from and write to.
 * @param variableName    name of the variable.
 * @param keyClazz key class.
 * @param valueClazz value class.
 */
class ListOfMapsReadWriteAdapterCaseService<K, V>(
  private val caseService: CaseService,
  private val caseExecutionId: String,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractListOfMapsReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  override fun getOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(
      getOrNull(
        caseService.getVariable(
          caseExecutionId, variableName
        )
      )
    )
  }

  override fun set(value: List<Map<K, V>>, isTransient: Boolean) {
    caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(
      getOrNull(
        caseService.getVariableLocal(
          caseExecutionId, variableName
        )
      )
    )
  }

  override fun setLocal(value: List<Map<K, V>>, isTransient: Boolean) {
    caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    caseService.removeVariable(caseExecutionId, variableName)
  }

  override fun removeLocal() {
    caseService.removeVariableLocal(caseExecutionId, variableName)
  }
}
