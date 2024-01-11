package io.holunda.camunda.bpm.data.factory

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.adapter.basic.*
import io.holunda.camunda.bpm.data.adapter.list.*
import io.holunda.camunda.bpm.data.adapter.map.*
import io.holunda.camunda.bpm.data.adapter.set.*
import io.holunda.camunda.bpm.data.factory.builder.BasicCaseServiceAdapterBuilder
import io.holunda.camunda.bpm.data.factory.builder.BasicRuntimeServiceAdapterBuilder
import io.holunda.camunda.bpm.data.factory.builder.BasicTaskServiceAdapterBuilder
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.VariableMap

object C7Adapters {

  @JvmStatic
  fun <T> on(factory: BasicVariableFactory<T>, runtimeService: RuntimeService, executionId: String): WriteAdapter<T> {
    return ReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> from(factory: BasicVariableFactory<T>, runtimeService: RuntimeService, executionId: String): ReadAdapter<T> {
    return ReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> on(factory: BasicVariableFactory<T>, variableScope: VariableScope): WriteAdapter<T> {
    return ReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> from(factory: BasicVariableFactory<T>, variableScope: VariableScope): ReadAdapter<T> {
    return ReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> on(factory: BasicVariableFactory<T>, variableMap: VariableMap): WriteAdapter<T> {
    return ReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> from(factory: BasicVariableFactory<T>, variableMap: VariableMap): ReadAdapter<T> {
    return ReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> on(factory: BasicVariableFactory<T>, taskService: TaskService, taskId: String): WriteAdapter<T> {
    return ReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> from(factory: BasicVariableFactory<T>, taskService: TaskService, taskId: String): ReadAdapter<T> {
    return ReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> on(factory: BasicVariableFactory<T>, caseService: CaseService, caseExecutionId: String): WriteAdapter<T> {
    return ReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> from(factory: BasicVariableFactory<T>, caseService: CaseService, caseExecutionId: String): ReadAdapter<T> {
    return ReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.variableClass
    )
  }

  @JvmStatic
  fun <T> from(factory: BasicVariableFactory<T>, lockedExternalTask: LockedExternalTask): ReadAdapter<T> {
    return ReadAdapterLockedExternalTask(
      lockedExternalTask,
      factory.name,
      factory.variableClass
    )
  }


  @JvmStatic
  fun <T> on(factory: ListVariableFactory<T>, variableScope: VariableScope): WriteAdapter<List<T>> {
    return ListReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: ListVariableFactory<T>, variableScope: VariableScope): ReadAdapter<List<T>> {
    return ListReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: ListVariableFactory<T>, variableMap: VariableMap): WriteAdapter<List<T>> {
    return ListReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: ListVariableFactory<T>, variableMap: VariableMap): ReadAdapter<List<T>> {
    return ListReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: ListVariableFactory<T>, runtimeService: RuntimeService, executionId: String): WriteAdapter<List<T>> {
    return ListReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: ListVariableFactory<T>, runtimeService: RuntimeService, executionId: String): ReadAdapter<List<T>> {
    return ListReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: ListVariableFactory<T>, taskService: TaskService, taskId: String): WriteAdapter<List<T>> {
    return ListReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: ListVariableFactory<T>, taskService: TaskService, taskId: String): ReadAdapter<List<T>> {
    return ListReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: ListVariableFactory<T>, caseService: CaseService, caseExecutionId: String): WriteAdapter<List<T>> {
    return ListReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: ListVariableFactory<T>, caseService: CaseService, caseExecutionId: String): ReadAdapter<List<T>> {
    return ListReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: ListVariableFactory<T>, lockedExternalTask: LockedExternalTask): ReadAdapter<List<T>> {
    return ListReadAdapterLockedExternalTask(
      lockedExternalTask,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: SetVariableFactory<T>, variableScope: VariableScope): WriteAdapter<Set<T>> {
    return SetReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: SetVariableFactory<T>, variableScope: VariableScope): ReadAdapter<Set<T>> {
    return SetReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: SetVariableFactory<T>, variableMap: VariableMap): WriteAdapter<Set<T>> {
    return SetReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: SetVariableFactory<T>, variableMap: VariableMap): ReadAdapter<Set<T>> {
    return SetReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: SetVariableFactory<T>, runtimeService: RuntimeService, executionId: String): WriteAdapter<Set<T>> {
    return SetReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: SetVariableFactory<T>, runtimeService: RuntimeService, executionId: String): ReadAdapter<Set<T>> {
    return SetReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: SetVariableFactory<T>, taskService: TaskService, taskId: String): WriteAdapter<Set<T>> {
    return SetReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: SetVariableFactory<T>, taskService: TaskService, taskId: String): ReadAdapter<Set<T>> {
    return SetReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> on(factory: SetVariableFactory<T>, caseService: CaseService, caseExecutionId: String): WriteAdapter<Set<T>> {
    return SetReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: SetVariableFactory<T>, caseService: CaseService, caseExecutionId: String): ReadAdapter<Set<T>> {
    return SetReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <T> from(factory: SetVariableFactory<T>, lockedExternalTask: LockedExternalTask): ReadAdapter<Set<T>> {
    return SetReadAdapterLockedExternalTask(
      lockedExternalTask,
      factory.name,
      factory.memberClass
    )
  }

  @JvmStatic
  fun <K, V> on(factory: MapVariableFactory<K, V>, variableScope: VariableScope): WriteAdapter<Map<K, V>> {
    return MapReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> from(factory: MapVariableFactory<K, V>, variableScope: VariableScope): ReadAdapter<Map<K, V>> {
    return MapReadWriteAdapterVariableScope(
      variableScope,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> on(factory: MapVariableFactory<K, V>, variableMap: VariableMap): WriteAdapter<Map<K, V>> {
    return MapReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> from(factory: MapVariableFactory<K, V>, variableMap: VariableMap): ReadAdapter<Map<K, V>> {
    return MapReadWriteAdapterVariableMap(
      variableMap,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> on(factory: MapVariableFactory<K, V>, runtimeService: RuntimeService, executionId: String): WriteAdapter<Map<K, V>> {
    return MapReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> from(factory: MapVariableFactory<K, V>, runtimeService: RuntimeService, executionId: String): ReadAdapter<Map<K, V>> {
    return MapReadWriteAdapterRuntimeService(
      runtimeService,
      executionId,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> on(factory: MapVariableFactory<K, V>, taskService: TaskService, taskId: String): WriteAdapter<Map<K, V>> {
    return MapReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> from(factory: MapVariableFactory<K, V>, taskService: TaskService, taskId: String): ReadAdapter<Map<K, V>> {
    return MapReadWriteAdapterTaskService(
      taskService,
      taskId,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> on(factory: MapVariableFactory<K, V>, caseService: CaseService, caseExecutionId: String): WriteAdapter<Map<K, V>> {
    return MapReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> from(factory: MapVariableFactory<K, V>, caseService: CaseService, caseExecutionId: String): ReadAdapter<Map<K, V>> {
    return MapReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  @JvmStatic
  fun <K, V> from(factory: MapVariableFactory<K, V>, lockedExternalTask: LockedExternalTask): ReadAdapter<Map<K, V>> {
    return MapReadAdapterLockedExternalTask(
      lockedExternalTask,
      factory.name,
      factory.keyClass,
      factory.valueClass
    )
  }

  /**
   * Creates a reusable adapter builder using a runtime service.
   *
   * @param runtimeService runtime service to operate on.
   * @return adapter builder.
   */
  @JvmStatic
  fun <T> using(factory: BasicVariableFactory<T>, runtimeService: RuntimeService): BasicRuntimeServiceAdapterBuilder<T> {
    return BasicRuntimeServiceAdapterBuilder(factory, runtimeService)
  }

  /**
   * Creates a reusable adapter builder using a task service.
   *
   * @param taskService task service to operate on.
   * @return adapter builder.
   */
  @JvmStatic
  fun <T> using(factory: BasicVariableFactory<T>, taskService: TaskService): BasicTaskServiceAdapterBuilder<T> {
    return BasicTaskServiceAdapterBuilder(factory, taskService)
  }

  /**
   * Creates a reusable adapter builder using a case service.
   *
   * @param caseService case service to operate on.
   * @return adapter builder.
   */
  @JvmStatic
  fun <T> using(factory: BasicVariableFactory<T>, caseService: CaseService): BasicCaseServiceAdapterBuilder<T> {
    return BasicCaseServiceAdapterBuilder(factory, caseService)
  }
}
