package io.holunda.camunda.bpm.data.adapter.set

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
class SetReadWriteAdapterTaskService<T>(
  private val taskService: TaskService,
  private val taskId: String,
  variableName: String,
  memberClazz: Class<T>
) : AbstractSetReadWriteAdapter<T>(variableName, memberClazz) {
  override fun getOptional(): Optional<Set<T>> {
    return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)))
  }

  override fun set(value: Set<T>, isTransient: Boolean) {
    taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun getLocalOptional(): Optional<Set<T>> {
    return Optional.ofNullable(getOrNull(taskService.getVariableLocal(taskId, variableName)))
  }

  override fun setLocal(value: Set<T>, isTransient: Boolean) {
    taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient))
  }

  override fun remove() {
    taskService.removeVariable(taskId, variableName)
  }

  override fun removeLocal() {
    taskService.removeVariableLocal(taskId, variableName)
  }
}
