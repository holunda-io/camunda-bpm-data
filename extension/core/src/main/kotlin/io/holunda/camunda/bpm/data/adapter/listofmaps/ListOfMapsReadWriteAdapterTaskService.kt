package io.holunda.camunda.bpm.data.adapter.listofmaps

import org.camunda.bpm.engine.TaskService
import java.util.*

/**
 * Read write adapter for task service access.
 *
 * @param [K] key type.
 * @param [V] value type.
 * @param taskService  task service to use.
 * @param taskId       id of the task to read from and write to.
 * @param variableName name of the variable.
 * @param keyClazz key class.
 * @param valueClazz value class.
 */
class ListOfMapsReadWriteAdapterTaskService<K, V>(
  private val taskService: TaskService,
  private val taskId: String,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractListOfMapsReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  override fun getOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)))
  }

  override fun set(value: List<Map<K, V>>, isTransient: Boolean) {
    taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(getOrNull(taskService.getVariableLocal(taskId, variableName)))
  }

  override fun setLocal(value: List<Map<K, V>>, isTransient: Boolean) {
    taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    taskService.removeVariable(taskId, variableName)
  }

  override fun removeLocal() {
    taskService.removeVariableLocal(taskId, variableName)
  }
}
