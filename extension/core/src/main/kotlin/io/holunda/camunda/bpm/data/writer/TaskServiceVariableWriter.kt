package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*
import java.util.function.Function

/**
 * User task builder allowing for fluent variable setting.
 */
class TaskServiceVariableWriter(private val taskService: TaskService, private val taskId: String) :
  VariableWriter<TaskServiceVariableWriter> {
  override fun variables(): VariableMap {
    return taskService.getVariablesTyped(taskId)
  }

  override fun variablesLocal(): VariableMap {
    return taskService.getVariablesLocalTyped(taskId)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T): TaskServiceVariableWriter {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId)[value] = isTransient
    return this
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T): TaskServiceVariableWriter {
    return this.setLocal(variableFactory, value, false)
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).setLocal(value, isTransient)
    return this
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).remove()
    return this
  }

  override fun <T> removeLocal(variableFactory: VariableFactory<T>): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).removeLocal()
    return this
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).update(valueProcessor)
    return this
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).update(valueProcessor, isTransient)
    return this
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>
  ): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).updateLocal(valueProcessor)
    return this
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): TaskServiceVariableWriter {
    variableFactory.on(taskService, taskId).updateLocal(valueProcessor, isTransient)
    return this
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
