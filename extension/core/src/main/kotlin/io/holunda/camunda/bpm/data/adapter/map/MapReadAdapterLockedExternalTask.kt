package io.holunda.camunda.bpm.data.adapter.map

import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Read adapter for external task
 *
 * @param [K] type of key.
 * @param [V] type of value.
 * @param lockedExternalTask external task.
 * @param variableName name of the variable.
 */
class MapReadAdapterLockedExternalTask<K, V>(
  private val lockedExternalTask: LockedExternalTask,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractMapReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  private val value: Any?
    get() = Optional.ofNullable(lockedExternalTask.variables)
      .orElse(Variables.createVariables())[variableName]

  override fun getOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(
      getOrNull(
        value
      )
    )
  }

  override fun getLocal(): Map<K, V> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<Map<K, V>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrDefault(defaultValue: Map<K, V>): Map<K, V> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrNull(): Map<K, V> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun remove() {
    throw UnsupportedOperationException("Can't remove a variable on an external task")
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't remove a local variable on an external task")
  }

  override fun set(value: Map<K, V>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a variable on an external task")
  }

  override fun setLocal(value: Map<K, V>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on an external task")
  }

}
