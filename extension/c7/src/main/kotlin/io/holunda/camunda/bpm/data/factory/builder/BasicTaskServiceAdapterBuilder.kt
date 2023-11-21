package io.holunda.camunda.bpm.data.factory.builder

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterTaskService
import io.holunda.camunda.bpm.data.factory.BasicVariableFactory
import org.camunda.bpm.engine.TaskService

/**
 * Creates a builder to encapsulate the task service access.
 *
 * @param [T] type of builder.
 * @param basicVariableFactory variable factory to use.
 * @param taskService          task service to build adapter with.
 */
class BasicTaskServiceAdapterBuilder<T: Any?>(private val basicVariableFactory: BasicVariableFactory<T>, private val taskService: TaskService) {
  private fun readWriteAdapter(taskId: String): ReadWriteAdapterTaskService<T> {
    return ReadWriteAdapterTaskService(
        taskService,
        taskId,
        basicVariableFactory.name,
        basicVariableFactory.variableClass
    )
  }

  /**
   * Creates a write adapter on task.
   *
   * @param taskId id identifying task.
   * @return write adapter
   */
  fun on(taskId: String): WriteAdapter<T> {
    return readWriteAdapter(taskId)
  }

  /**
   * Creates a read adapter on task.
   *
   * @param taskId id identifying task.
   * @return read adapter.
   */
  fun from(taskId: String): ReadAdapter<T> {
    return readWriteAdapter(taskId)
  }
}
