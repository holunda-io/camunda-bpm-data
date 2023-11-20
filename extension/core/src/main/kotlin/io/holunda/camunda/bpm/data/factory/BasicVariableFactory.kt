package io.holunda.camunda.bpm.data.factory

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.adapter.basic.*
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Variable factory of a base type(non parametrized).
 *
 * @param [T] type of the factory.
 * @param name  name of the variable.
 * @param variableClass class of the type.
 */
class BasicVariableFactory<T : Any?>(
  override val name: String,
  val variableClass: Class<T>
) : VariableFactory<T> {

  companion object {
    inline fun <reified T> forType(name: String) = BasicVariableFactory(name, T::class.java)
  }

  override fun on(variableScope: VariableScope): WriteAdapter<T> {
    return ReadWriteAdapterVariableScope(variableScope, name, variableClass)
  }

  override fun from(variableScope: VariableScope): ReadAdapter<T> {
    return ReadWriteAdapterVariableScope(variableScope, name, variableClass)
  }

  override fun on(variableMap: VariableMap): WriteAdapter<T> {
    return ReadWriteAdapterVariableMap(variableMap, name, variableClass)
  }

  override fun from(variableMap: VariableMap): ReadAdapter<T> {
    return ReadWriteAdapterVariableMap(variableMap, name, variableClass)
  }

  override fun on(runtimeService: RuntimeService, executionId: String): WriteAdapter<T> {
    return ReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      name,
      variableClass
    )
  }

  override fun from(runtimeService: RuntimeService, executionId: String): ReadAdapter<T> {
    return ReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      name,
      variableClass
    )
  }

  override fun on(taskService: TaskService, taskId: String): WriteAdapter<T> {
    return ReadWriteAdapterTaskService(
      taskService,
      taskId,
      name,
      variableClass
    )
  }

  override fun from(taskService: TaskService, taskId: String): ReadAdapter<T> {
    return ReadWriteAdapterTaskService(
      taskService,
      taskId,
      name,
      variableClass
    )
  }

  override fun on(caseService: CaseService, caseExecutionId: String): WriteAdapter<T> {
    return ReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      name,
      variableClass
    )
  }

  override fun from(caseService: CaseService, caseExecutionId: String): ReadAdapter<T> {
    return ReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      name,
      variableClass
    )
  }

  override fun from(lockedExternalTask: LockedExternalTask): ReadAdapter<T> {
    return ReadAdapterLockedExternalTask(
      lockedExternalTask,
      name,
      variableClass
    )
  }

  /**
   * Creates a reusable adapter builder using a runtime service.
   *
   * @param runtimeService runtime service to operate on.
   * @return adapter builder.
   */
  fun using(runtimeService: RuntimeService): BasicRuntimeServiceAdapterBuilder<T> {
    return BasicRuntimeServiceAdapterBuilder(this, runtimeService)
  }

  /**
   * Creates a reusable adapter builder using a task service.
   *
   * @param taskService task service to operate on.
   * @return adapter builder.
   */
  fun using(taskService: TaskService): BasicTaskServiceAdapterBuilder<T> {
    return BasicTaskServiceAdapterBuilder(this, taskService)
  }

  /**
   * Creates a reusable adapter builder using a case service.
   *
   * @param caseService case service to operate on.
   * @return adapter builder.
   */
  fun using(caseService: CaseService): BasicCaseServiceAdapterBuilder<T> {
    return BasicCaseServiceAdapterBuilder(this, caseService)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as BasicVariableFactory<*>
    return name == that.name && variableClass == that.variableClass
  }

  override fun hashCode(): Int {
    return Objects.hash(name, variableClass)
  }

  override fun toString(): String {
    return "BasicVariableFactory{" +
      "name='" + name + '\'' +
      ", clazz=" + variableClass +
      '}'
  }

  /**
   * Creates a builder to encapsulate the runtime service access.
   *
   * @param [T] type of builder.
   * @param basicVariableFactory variable factory to use.
   * @param runtimeService       task service to build adapter with.
   */
  class BasicRuntimeServiceAdapterBuilder<T: Any?>(
    private val basicVariableFactory: BasicVariableFactory<T>,
    private val runtimeService: RuntimeService
  ) {
    private fun readWriteAdapter(executionId: String): ReadWriteAdapterRuntimeService<T> {
      return ReadWriteAdapterRuntimeService(
        runtimeService,
        executionId,
        basicVariableFactory.name,
        basicVariableFactory.variableClass
      )
    }

    /**
     * Creates a write adapter on execution.
     *
     * @param executionId id identifying execution.
     * @return write adapter
     */
    fun on(executionId: String): WriteAdapter<T> {
      return readWriteAdapter(executionId)
    }

    /**
     * Creates a read adapter on execution.
     *
     * @param executionId id identifying execution.
     * @return read adapter.
     */
    fun from(executionId: String): ReadAdapter<T> {
      return readWriteAdapter(executionId)
    }
  }

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

  /**
   * Creates a builder to encapsulate the case service access.
   *
   * @param [T] type of builder.
   * @param basicVariableFactory variable factory to use.
   * @param caseService          task service to build adapter with.
   */
  class BasicCaseServiceAdapterBuilder<T: Any?>(private val basicVariableFactory: BasicVariableFactory<T>, private val caseService: CaseService) {
    private fun readWriteAdapter(caseExecutionId: String): ReadWriteAdapterCaseService<T> {
      return ReadWriteAdapterCaseService(
        caseService,
        caseExecutionId,
        basicVariableFactory.name,
        basicVariableFactory.variableClass
      )
    }

    /**
     * Creates a write adapter on caseExecution.
     *
     * @param caseExecutionId id identifying caseExecution.
     * @return write adapter
     */
    fun on(caseExecutionId: String): WriteAdapter<T> {
      return readWriteAdapter(caseExecutionId)
    }

    /**
     * Creates a read adapter on caseExecution.
     *
     * @param caseExecutionId id identifying caseExecution.
     * @return read adapter.
     */
    fun from(caseExecutionId: String): ReadAdapter<T> {
      return readWriteAdapter(caseExecutionId)
    }
  }
}
