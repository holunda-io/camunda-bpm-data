package io.holunda.camunda.bpm.data.adapter.listofmaps

import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*
import kotlin.collections.Map

/**
 * Read adapter for external task.
 *
 * @param [K] key type.
 * @param [V] value type.
 */
class ListOfMapsReadAdapterLockedExternalTask<K, V>(
  private val lockedExternalTask: LockedExternalTask,
  variableName: String,
  keyClazz: Class<K>,
  valueClazz: Class<V>
) : AbstractListOfMapsReadWriteAdapter<K, V>(variableName, keyClazz, valueClazz) {

  private val value: Any?
    get() = Optional.ofNullable(lockedExternalTask.variables)
      .orElse(Variables.createVariables())[variableName]

  override fun getOptional(): Optional<List<Map<K, V>>> {
    return Optional.ofNullable(
      getOrNull(
        value
      )
    )
  }

  override fun set(value: List<Map<K, V>>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a variable on an external task")
  }

  override fun setLocal(value: List<Map<K, V>>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on an external task")
  }

  override fun getLocal(): List<Map<K, V>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<List<Map<K, V>>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrDefault(defaultValue: List<Map<K, V>>): List<Map<K, V>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrNull(): List<Map<K, V>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun remove() {
    throw UnsupportedOperationException("Can't remove a variable on an external task")
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't remove a local variable on an external task")
  }
}
