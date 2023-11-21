package io.holunda.camunda.bpm.data.adapter.map

import org.camunda.bpm.engine.TaskService
import java.util.*

/**
 * Read write adapter for task service access.
 *
 * @param [K] type of key.
 * @param [V] type of value.
 * @param taskService  task service to use.
 * @param taskId       id of the task to read from and write to.
 * @param variableName name of the variable.
 * @param keyClazz     class of the key of variable.
 * @param valueClazz   class of variable.
 */
class MapReadWriteAdapterTaskService<K, V>(
  private val taskService: TaskService,
  private val taskId: String,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractMapReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {
  override fun getOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)))
  }

  override fun set(value: Map<K, V>, isTransient: Boolean) {
    taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(getOrNull(taskService.getVariableLocal(taskId, variableName)))
  }

  override fun setLocal(value: Map<K, V>, isTransient: Boolean) {
    taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    taskService.removeVariable(taskId, variableName)
  }

  override fun removeLocal() {
    taskService.removeVariableLocal(taskId, variableName)
  }
}
