package io.holunda.camunda.bpm.data.adapter.listofmaps

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.variable.value.TypedValue

/**
 * Base class for all list of maps read-write adapter.
 *
 * @param [K] key type.
 * @param [V] value type.
 * @param variableName name of variable.
 * @param keyClazz key class.
 * @param valueClazz value class.
 */
abstract class AbstractListOfMapsReadWriteAdapter<K, V>(
    variableName: String,
    protected val keyClazz: Class<K>,
    protected val valueClazz: Class<V>
) : AbstractReadWriteAdapter<List<Map<K, V>>>(variableName) {
  /**
   * Read the value of null.
   *
   * @param value raw value.
   * @return list or null.
   */
  protected fun getOrNull(value: Any?): List<Map<K, V>>? {
    if (value == null) {
      return null
    }
    if (MutableList::class.java.isAssignableFrom(value.javaClass)) {
      val valueAsList = value as List<*>
      return if (valueAsList.isEmpty()) {
        emptyList()
      } else {
        val valueAsMap = valueAsList.iterator().next() as Map<*, *>
        if (MutableMap::class.java.isAssignableFrom(valueAsMap.javaClass)) {
          val (key, value1) = valueAsMap.entries.iterator().next()
          if (keyClazz.isAssignableFrom(key!!.javaClass) &&
              valueClazz.isAssignableFrom(value1!!.javaClass)) {
            @Suppress("UNCHECKED_CAST")
            valueAsList as List<Map<K, V>>
          } else {
            throw WrongVariableTypeException(
              "Error reading $variableName: Wrong map type detected, expected Map<${keyClazz.name},${valueClazz.name}, but was not found in $valueAsMap"
            )
          }
        } else {
          throw WrongVariableTypeException(
              "Error reading $variableName: Wrong list type detected, expected List<Map<*,*>, but was not found in $valueAsList")
        }
      }
    }
    throw WrongVariableTypeException(
        "Error reading $variableName: Couldn't read value of type List of Maps from $value")
  }

  override fun getTypedValue(value: Any?, isTransient: Boolean): TypedValue {
    return getTypedValue(MutableList::class.java, value, isTransient)
  }
}
