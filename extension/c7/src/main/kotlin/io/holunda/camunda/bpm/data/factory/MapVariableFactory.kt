package io.holunda.camunda.bpm.data.factory

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Variable factory of a base parametrized map type.
 *
 * @param [K]> member key type of the factory.
 * @param [V] member value type of the factory.
 */
class MapVariableFactory<K, V>(
  override val name: String,
  val keyClass: Class<K>,
  val valueClass: Class<V>
) : VariableFactory<Map<K, V>> {

  companion object {
    inline fun <reified K, reified V> forType(name: String) = MapVariableFactory(name, K::class.java, V::class.java)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as MapVariableFactory<*, *>
    return name == that.name && keyClass == that.keyClass && valueClass == that.valueClass
  }

  override fun hashCode(): Int {
    return Objects.hash(name, keyClass, valueClass)
  }

  override fun toString(): String {
    return "MapVariableFactory{" +
      "name='" + name + '\'' +
      ", keyClass=" + keyClass +
      ", valueClass=" + valueClass +
      '}'
  }

  /**
   * Creates a write adapter on variable scope.
   *
   * @param variableScope underlying scope to work on.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method on VariableScopeWriter",
    replaceWith = ReplaceWith(
      expression = "VariableScopeWriter.of(this, variableScope)",
      imports = ["io.holunda.camunda.bpm.data.writer.VariableScopeWriter"]
    ), level = DeprecationLevel.WARNING
  )
  override fun on(variableScope: VariableScope): WriteAdapter<Map<K, V>> = C7Adapters.on(this, variableScope)


  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableScope underlying scope to work on.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method on VariableScopeReader",
    replaceWith = ReplaceWith(
      expression = "VariableScopeReader.of(this, variableScope)",
      imports = ["io.holunda.camunda.bpm.data.reader.VariableScopeReader"]
    ), level = DeprecationLevel.WARNING
  )
  override fun from(variableScope: VariableScope): ReadAdapter<Map<K, V>> = C7Adapters.from(this, variableScope)

  /**
   * Creates a write adapter for variable map.
   *
   * @param variableMap underlying scope to work on.
   * @return write adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method on VariableMapWriter",
    replaceWith = ReplaceWith(
      expression = "VariableMapWriter.of(this, variableMap)",
      imports = ["io.holunda.camunda.bpm.data.writer.VariableMapWriter"]
    ), level = DeprecationLevel.WARNING
  )
  override fun on(variableMap: VariableMap): WriteAdapter<Map<K, V>> = C7Adapters.on(this, variableMap)

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableMap underlying map to work on.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of VariableMapReader",
    replaceWith = ReplaceWith(
      expression = "VariableMapReader.of(this, variableMap)",
      imports = ["io.holunda.camunda.bpm.data.reader.VariableMapReader"]
    ), level = DeprecationLevel.WARNING
  )
  override fun from(variableMap: VariableMap): ReadAdapter<Map<K, V>> = C7Adapters.from(this, variableMap)

  /**
   * Creates a write adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   * @return write adapter
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of RuntimeServiceVariableWriter",
    replaceWith = ReplaceWith(
      expression = "RuntimeServiceVariableWriter.of(this, runtimeService, executionId)",
      imports = ["io.holunda.camunda.bpm.data.writer.RuntimeServiceVariableWriter"]
    ), level = DeprecationLevel.WARNING
  )
  override fun on(runtimeService: RuntimeService, executionId: String): WriteAdapter<Map<K, V>> = C7Adapters.on(this, runtimeService, executionId)

  /**
   * Creates a read adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of RuntimeServiceVariableReader",
    replaceWith = ReplaceWith(
      expression = "RuntimeServiceVariableReader.of(this, runtimeService, executionId)",
      imports = ["io.holunda.camunda.bpm.data.reader.RuntimeServiceVariableReader"]
    ), level = DeprecationLevel.WARNING
  )
  override fun from(runtimeService: RuntimeService, executionId: String): ReadAdapter<Map<K, V>> = C7Adapters.from(this, runtimeService, executionId)

  /**
   * Creates a write adapter on task.
   *
   * @param taskService underlying task service to work on.
   * @param taskId      id identifying task.
   * @return write adapter
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of TaskServiceVariableWriter",
    replaceWith = ReplaceWith(
      expression = "TaskServiceVariableWriter.of(this, taskService, taskId)",
      imports = ["io.holunda.camunda.bpm.data.writer.TaskServiceVariableWriter"]
    ), level = DeprecationLevel.WARNING
  )
  override fun on(taskService: TaskService, taskId: String): WriteAdapter<Map<K, V>> = C7Adapters.on(this, taskService, taskId)

  /**
   * Creates a read adapter on task.
   *
   * @param taskService underlying task service to work on.
   * @param taskId      id identifying task.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of TaskServiceVariableReader",
    replaceWith = ReplaceWith(
      expression = "TaskServiceVariableReader.of(this, taskService, taskId)",
      imports = ["io.holunda.camunda.bpm.data.reader.TaskServiceVariableReader"]
    ), level = DeprecationLevel.WARNING
  )
  override fun from(taskService: TaskService, taskId: String): ReadAdapter<Map<K, V>> = C7Adapters.from(this, taskService, taskId)

  /**
   * Creates a write adapter on task.
   *
   * @param caseService     underlying case service to work on.
   * @param caseExecutionId id identifying case execution.
   * @return write adapter
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of CaseServiceVariableWriter",
    replaceWith = ReplaceWith(
      expression = "CaseServiceVariableWriter.of(this, caseService, caseExecutionId)",
      imports = ["io.holunda.camunda.bpm.data.writer.CaseServiceVariableWriter"]
    ), level = DeprecationLevel.WARNING
  )
  override fun on(caseService: CaseService, caseExecutionId: String): WriteAdapter<Map<K, V>> = C7Adapters.on(this, caseService, caseExecutionId)

  /**
   * Creates a read adapter on task.
   *
   * @param caseService     underlying case service to work on.
   * @param caseExecutionId id identifying case execution.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of CaseServiceVariableReader",
    replaceWith = ReplaceWith(
      expression = "CaseServiceVariableReader.of(this, caseService, caseExecutionId)",
      imports = ["io.holunda.camunda.bpm.data.reader.CaseServiceVariableReader"]
    ), level = DeprecationLevel.WARNING
  )
  override fun from(caseService: CaseService, caseExecutionId: String): ReadAdapter<Map<K, V>> = C7Adapters.from(this, caseService, caseExecutionId)

  /**
   * Creates a read adapter on external task.
   *
   * @param lockedExternalTask underlying external task to work on.
   * @return read adapter.
   */
  @Deprecated(
    message = "Deprecated in favour of static factory method of LockedExternalTaskReader",
    replaceWith = ReplaceWith(
      expression = "LockedExternalTaskReader.of(this, lockedExternalTask)",
      imports = ["io.holunda.camunda.bpm.data.reader.LockedExternalTaskReader"]
    ), level = DeprecationLevel.WARNING
  )
  override fun from(lockedExternalTask: LockedExternalTask): ReadAdapter<Map<K, V>> = C7Adapters.from(this, lockedExternalTask)

}
