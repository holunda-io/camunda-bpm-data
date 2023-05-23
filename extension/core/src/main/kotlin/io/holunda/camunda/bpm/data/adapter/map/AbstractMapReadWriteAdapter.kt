package io.holunda.camunda.bpm.data.adapter.map

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.variable.value.TypedValue

/**
 * Base class for all map type read write adapter.
 *
 * @param [K]          key type.
 * @param [V]          value type.
 * @param variableName name of the variable.
 * @param keyClazz     key class.
 * @param valueClazz   value class.
 */
abstract class AbstractMapReadWriteAdapter<K, V>(
  variableName: String,
  protected val keyClazz: Class<K>,
  protected val valueClazz: Class<V>
) : AbstractReadWriteAdapter<Map<K, V>>(variableName) {
  /**
   * Retrieves the value or null.
   *
   * @param value raw value.
   * @return set or null.
   */
  protected fun getOrNull(value: Any?): Map<K, V>? {
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
          @Suppress("UNCHECKED_CAST")
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

  override fun getTypedValue(value: Any?, isTransient: Boolean): TypedValue {
    return getTypedValue<MutableMap<*, *>>(MutableMap::class.java, value, isTransient)
  }
}
