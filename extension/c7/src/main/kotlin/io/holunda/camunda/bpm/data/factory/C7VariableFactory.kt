package io.holunda.camunda.bpm.data.factory

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.C7Adapters.from
import io.holunda.camunda.bpm.data.factory.C7Adapters.on
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.VariableMap

/**
 * C7 Typed variable factory used in legacy adapters.
 *
 * @param [T] type of the factory.
 */
@Suppress("OVERRIDE_DEPRECATION") // remove this suppression as soon as the variable methods are removed.
sealed class C7VariableFactory<T> : VariableFactory<T> {

  /**
   * Factory type.
   */
  sealed class FactoryType(val name: String) {
    /**
     * Retrieves the corresponding factory.
     * @return factory.
     */
    abstract fun getFactory(): Any

    /**
     * Basic type.
     */
    class Basic<T>(name: String, private val variableClass: Class<T>) : FactoryType(name) {
      override fun getFactory() = BasicVariableFactory(name, variableClass)
    }

    /**
     * List type.
     */
    class List<T>(name: String, private val memberClass: Class<T>) : FactoryType(name) {
      override fun getFactory() = ListVariableFactory(name, memberClass)
    }

    /**
     * Set type.
     */
    class Set<T>(name: String, private val memberClass: Class<T>) : FactoryType(name) {
      override fun getFactory() = SetVariableFactory(name, memberClass)
    }

    /**
     * Map type.
     */
    class Map<K, V>(name: String, private val keyClass: Class<K>, private val valueClass: Class<V>) : FactoryType(name) {
      override fun getFactory() = MapVariableFactory(name, keyClass, valueClass)
    }
  }

  /**
   * Creates a write adapter for variable scope.
   *
   * @param variableScope underlying scope to work on.
   * @return write adapter.
   */
  abstract override fun on(variableScope: VariableScope): WriteAdapter<T>

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableScope underlying scope to work on.
   * @return read adapter.
   */
  abstract override fun from(variableScope: VariableScope): ReadAdapter<T>

  /**
   * Creates a write adapter for variable map.
   *
   * @param variableMap underlying scope to work on.
   * @return write adapter.
   */
  abstract override fun on(variableMap: VariableMap): WriteAdapter<T>

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableMap underlying map to work on.
   * @return read adapter.
   */
  abstract override fun from(variableMap: VariableMap): ReadAdapter<T>

  /**
   * Creates a write adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   * @return write adapter
   */
  abstract override fun on(runtimeService: RuntimeService, executionId: String): WriteAdapter<T>

  /**
   * Creates a read adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   * @return read adapter.
   */
  abstract override fun from(runtimeService: RuntimeService, executionId: String): ReadAdapter<T>

  /**
   * Creates a write adapter on task.
   *
   * @param taskService underlying task service to work on.
   * @param taskId      id identifying task.
   * @return write adapter
   */
  abstract override fun on(taskService: TaskService, taskId: String): WriteAdapter<T>

  /**
   * Creates a read adapter on task.
   *
   * @param taskService underlying task service to work on.
   * @param taskId      id identifying task.
   * @return read adapter.
   */
  abstract override fun from(taskService: TaskService, taskId: String): ReadAdapter<T>

  /**
   * Creates a write adapter on task.
   *
   * @param caseService     underlying case service to work on.
   * @param caseExecutionId id identifying case execution.
   * @return write adapter
   */
  abstract override fun on(caseService: CaseService, caseExecutionId: String): WriteAdapter<T>

  /**
   * Creates a read adapter on task.
   *
   * @param caseService     underlying case service to work on.
   * @param caseExecutionId id identifying case execution.
   * @return read adapter.
   */
  abstract override fun from(caseService: CaseService, caseExecutionId: String): ReadAdapter<T>

  /**
   * Creates a read adapter on external task.
   *
   * @param lockedExternalTask underlying external task to work on.
   * @return read adapter.
   */
  abstract override fun from(lockedExternalTask: LockedExternalTask): ReadAdapter<T>

  /**
   * Basic factory legacy adapter.
   */
  class BasicLegacyAdapter<T>(name: String, clazz: Class<T>) : C7VariableFactory<T>() {
    private val factoryType: FactoryType.Basic<T> = FactoryType.Basic(name, clazz)
    override val name: String
      get() = factoryType.name
    override fun on(variableScope: VariableScope) = on(factoryType.getFactory(), variableScope)
    override fun from(variableScope: VariableScope) = from(factoryType.getFactory(), variableScope)
    override fun on(variableMap: VariableMap) = on(factoryType.getFactory(), variableMap)
    override fun from(variableMap: VariableMap) = from(factoryType.getFactory(), variableMap)
    override fun on(runtimeService: RuntimeService, executionId: String) = on(factoryType.getFactory(), runtimeService, executionId)
    override fun from(runtimeService: RuntimeService, executionId: String) = from(factoryType.getFactory(), runtimeService, executionId)
    override fun on(taskService: TaskService, taskId: String) = on(factoryType.getFactory(), taskService, taskId)
    override fun from(taskService: TaskService, taskId: String) = from(factoryType.getFactory(), taskService, taskId)
    override fun on(caseService: CaseService, caseExecutionId: String) = on(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(caseService: CaseService, caseExecutionId: String) = from(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(lockedExternalTask: LockedExternalTask) = from(factoryType.getFactory(), lockedExternalTask)
  }

  /**
   * List factory legacy adapter.
   */
  class ListLegacyAdapter<T>(name: String, memberClass: Class<T>) : C7VariableFactory<List<T>>() {
    private val factoryType: FactoryType.List<T> = FactoryType.List(name, memberClass)
    override val name: String
      get() = factoryType.name
    override fun on(variableScope: VariableScope) = on(factoryType.getFactory(), variableScope)
    override fun from(variableScope: VariableScope) = from(factoryType.getFactory(), variableScope)
    override fun on(variableMap: VariableMap) = on(factoryType.getFactory(), variableMap)
    override fun from(variableMap: VariableMap) = from(factoryType.getFactory(), variableMap)
    override fun on(runtimeService: RuntimeService, executionId: String) = on(factoryType.getFactory(), runtimeService, executionId)
    override fun from(runtimeService: RuntimeService, executionId: String) = from(factoryType.getFactory(), runtimeService, executionId)
    override fun on(taskService: TaskService, taskId: String) = on(factoryType.getFactory(), taskService, taskId)
    override fun from(taskService: TaskService, taskId: String) = from(factoryType.getFactory(), taskService, taskId)
    override fun on(caseService: CaseService, caseExecutionId: String) = on(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(caseService: CaseService, caseExecutionId: String) = from(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(lockedExternalTask: LockedExternalTask) = from(factoryType.getFactory(), lockedExternalTask)
  }

  /**
   * Set factory legacy adapter.
   */
  class SetLegacyAdapter<T>(name: String, memberClass: Class<T>) : C7VariableFactory<Set<T>>() {
    private val factoryType: FactoryType.Set<T> = FactoryType.Set(name, memberClass)
    override val name: String
      get() = factoryType.name
    override fun on(variableScope: VariableScope) = on(factoryType.getFactory(), variableScope)
    override fun from(variableScope: VariableScope) = from(factoryType.getFactory(), variableScope)
    override fun on(variableMap: VariableMap) = on(factoryType.getFactory(), variableMap)
    override fun from(variableMap: VariableMap) = from(factoryType.getFactory(), variableMap)
    override fun on(runtimeService: RuntimeService, executionId: String) = on(factoryType.getFactory(), runtimeService, executionId)
    override fun from(runtimeService: RuntimeService, executionId: String) = from(factoryType.getFactory(), runtimeService, executionId)
    override fun on(taskService: TaskService, taskId: String) = on(factoryType.getFactory(), taskService, taskId)
    override fun from(taskService: TaskService, taskId: String) = from(factoryType.getFactory(), taskService, taskId)
    override fun on(caseService: CaseService, caseExecutionId: String) = on(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(caseService: CaseService, caseExecutionId: String) = from(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(lockedExternalTask: LockedExternalTask) = from(factoryType.getFactory(), lockedExternalTask)
  }

  /**
   * Map factory legacy adapter.
   */
  class MapLegacyAdapter<K, V>(name: String, keyClass: Class<K>, valueClass: Class<V>) : C7VariableFactory<Map<K, V>>() {
    private val factoryType: FactoryType.Map<K, V> = FactoryType.Map(name, keyClass, valueClass)
    override val name: String
      get() = factoryType.name
    override fun on(variableScope: VariableScope) = on(factoryType.getFactory(), variableScope)
    override fun from(variableScope: VariableScope) = from(factoryType.getFactory(), variableScope)
    override fun on(variableMap: VariableMap) = on(factoryType.getFactory(), variableMap)
    override fun from(variableMap: VariableMap) = from(factoryType.getFactory(), variableMap)
    override fun on(runtimeService: RuntimeService, executionId: String) = on(factoryType.getFactory(), runtimeService, executionId)
    override fun from(runtimeService: RuntimeService, executionId: String) = from(factoryType.getFactory(), runtimeService, executionId)
    override fun on(taskService: TaskService, taskId: String) = on(factoryType.getFactory(), taskService, taskId)
    override fun from(taskService: TaskService, taskId: String) = from(factoryType.getFactory(), taskService, taskId)
    override fun on(caseService: CaseService, caseExecutionId: String) = on(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(caseService: CaseService, caseExecutionId: String) = from(factoryType.getFactory(), caseService, caseExecutionId)
    override fun from(lockedExternalTask: LockedExternalTask) = from(factoryType.getFactory(), lockedExternalTask)
  }
}

