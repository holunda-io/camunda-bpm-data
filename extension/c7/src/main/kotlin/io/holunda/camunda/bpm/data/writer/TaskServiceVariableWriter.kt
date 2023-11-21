package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * User task builder allowing for fluent variable setting.
 */
class TaskServiceVariableWriter(
  private val taskService: TaskService,
  private val taskId: String
) : VariableWriter<TaskServiceVariableWriter>, C7ServiceVariableWriter<TaskServiceVariableWriter>() {

  companion object {
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T> of(variableFactory: VariableFactory<T>, taskService: TaskService, taskId: String): WriteAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.on(variableFactory, taskService, taskId)
        is ListVariableFactory<*> -> C7Adapters.on(variableFactory, taskService, taskId)
        is SetVariableFactory<*> -> C7Adapters.on(variableFactory, taskService, taskId)
        is MapVariableFactory<*, *> -> C7Adapters.on(variableFactory, taskService, taskId)
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      } as WriteAdapter<T>
  }

  override fun <T> getWriter(variableFactory: VariableFactory<T>): WriteAdapter<T> =
    of(variableFactory, taskService, taskId)

  override fun variables(): VariableMap {
    return taskService.getVariablesTyped(taskId)
  }

  override fun variablesLocal(): VariableMap {
    return taskService.getVariablesLocalTyped(taskId)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as TaskServiceVariableWriter
    return if (taskService != that.taskService) false else taskId == that.taskId
  }

  override fun hashCode(): Int {
    return Objects.hash(taskService, taskId)
  }
}
