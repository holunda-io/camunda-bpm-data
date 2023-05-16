package io.holunda.camunda.bpm.data.adapter.list

import org.camunda.bpm.engine.TaskService
import java.util.*

/**
 * Read write adapter for task service access.
 *
 * @param [T] type of value.
 * @param taskService  task service to use.
 * @param taskId       id of the task to read from and write to.
 * @param variableName name of the variable.
 * @param memberClazz  class of the variable.
 */
class ListReadWriteAdapterTaskService<T>(
  private val taskService: TaskService,
  private val taskId: String,
  variableName: String,
  memberClazz: Class<T>
) : AbstractListReadWriteAdapter<T>(variableName, memberClazz) {

  override fun getOptional(): Optional<List<T>> {
    return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)))
  }

  override fun set(value: List<T>, isTransient: Boolean) {
    taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<List<T>> {
    return Optional.ofNullable(getOrNull(taskService.getVariableLocal(taskId, variableName)))
  }

  override fun setLocal(value: List<T>, isTransient: Boolean) {
    taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    taskService.removeVariable(taskId, variableName)
  }

  override fun removeLocal() {
    taskService.removeVariableLocal(taskId, variableName)
  }
}
