package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.TaskService
import java.util.*

/**
 * Allows reading multiple variable values from [TaskService.getVariable].
 * @param taskService task service to operate on.
 * @param taskId      task id.
 */
class TaskServiceVariableReader(
  private val taskService: TaskService,
  private val taskId: String
) : VariableReader, C7VariableReader() {

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, taskService: TaskService, taskId: String): ReadAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.from(variableFactory, taskService, taskId)
        is ListVariableFactory<*> -> C7Adapters.from(variableFactory, taskService, taskId)
        is SetVariableFactory<*> -> C7Adapters.from(variableFactory, taskService, taskId)
        is MapVariableFactory<*, *> -> C7Adapters.from(variableFactory, taskService, taskId)
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      } as ReadAdapter<T>

  }

  override fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T> =
    of(variableFactory, taskService, taskId)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as TaskServiceVariableReader
    return if (taskService != that.taskService) false else taskId == that.taskId
  }

  override fun hashCode(): Int {
    return Objects.hash(taskService, taskId)
  }
}
