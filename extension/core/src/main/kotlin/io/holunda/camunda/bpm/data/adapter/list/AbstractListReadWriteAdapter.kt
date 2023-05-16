package io.holunda.camunda.bpm.data.adapter.list

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.variable.value.TypedValue

/**
 * Base class for all list read-write adapter.
 *
 * @param [T] member type.
 * @param variableName name of variable.
 * @param memberClazz  member class.
 */
abstract class AbstractListReadWriteAdapter<T>(variableName: String, protected val memberClazz: Class<T>) :
  AbstractReadWriteAdapter<List<T>>(variableName) {
  /**
   * Read the value of null.
   *
   * @param value raw value.
   * @return list or null.
   */
  protected fun getOrNull(value: Any?): List<T>? {
    if (value == null) {
      return null
    }
    if (MutableList::class.java.isAssignableFrom(value.javaClass)) {
      val valueAsList = value as List<*>
      return if (valueAsList.isEmpty()) {
        emptyList()
      } else {
        if (memberClazz.isAssignableFrom(valueAsList.iterator().next()!!.javaClass)) {
          @Suppress("UNCHECKED_CAST")
          valueAsList as List<T>
        } else {
          throw WrongVariableTypeException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.name + ", but was not found in " + valueAsList)
        }
      }
    }
    throw WrongVariableTypeException("Error reading $variableName: Couldn't read value of type List from $value")
  }

  override fun getTypedValue(value: Any?, isTransient: Boolean): TypedValue {
    return getTypedValue<MutableList<*>>(MutableList::class.java, value, isTransient)
  }
}
