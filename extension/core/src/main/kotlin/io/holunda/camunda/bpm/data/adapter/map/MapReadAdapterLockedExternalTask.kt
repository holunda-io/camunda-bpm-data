package io.holunda.camunda.bpm.data.adapter.map

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Read adapter for external task
 *
 * @param <K> type of key.
 * @param <V> type of value.
 * @param lockedExternalTask external task.
 * @param variableName name of the variable.
</V></K> */
class MapReadAdapterLockedExternalTask<K, V>(
  private val lockedExternalTask: LockedExternalTask,
  private val variableName: String,
  private val keyClazz: Class<K>,
  private val valueClazz: Class<V>
) : ReadAdapter<Map<K, V>> {
  override fun get(): Map<K, V> {
    return getOptional().get()
  }

  override fun getOptional(): Optional<Map<K, V>> {
    return Optional.ofNullable(getOrNull(value))
  }

  override fun getLocal(): Map<K, V> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<Map<K, V>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getOrDefault(defaultValue: Map<K, V>): Map<K, V> {
    return getOptional().orElse(defaultValue)
  }

  override fun getLocalOrDefault(defaultValue: Map<K, V>): Map<K, V> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getOrNull(): Map<K, V>? {
    return getOrNull(value)
  }

  override fun getLocalOrNull(): Map<K, V> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  private fun getOrNull(value: Any?): Map<K, V>? {
    if (value == null) {
      return null
    }
    if (MutableMap::class.java.isAssignableFrom(value.javaClass)) {
      val valueAsMap = value as Map<*, *>
      return if (valueAsMap.isEmpty()) {
        emptyMap()
      } else {
        val (key, value1) = valueAsMap.entries.iterator().next()
        if (keyClazz.isAssignableFrom(key!!.javaClass) && valueClazz.isAssignableFrom(value1!!.javaClass)) {
          valueAsMap as Map<K, V>
        } else {
          throw WrongVariableTypeException(
            "Error reading " + variableName + ": Wrong map type detected, expected Map<"
              + keyClazz.name + "," + valueClazz.name
              + ", but was not found in " + valueAsMap
          )
        }
      }
    }
    throw WrongVariableTypeException("Error reading $variableName: Couldn't read value of type Map from $value")
  }

  private val value: Any?
    get() = Optional.ofNullable(lockedExternalTask.variables)
      .orElse(Variables.createVariables())[variableName]
}
