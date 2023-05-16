package io.holunda.camunda.bpm.data.adapter.set

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil.getTypedValue
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.variable.value.TypedValue

/**
 * Base class for all set type read write adapter.
 *
 * @param [T] member type.
 * @param variableName name of the variable.
 * @param memberClazz  member class.
 */
abstract class AbstractSetReadWriteAdapter<T>(variableName: String, protected val memberClazz: Class<T>) : AbstractReadWriteAdapter<Set<T>>(variableName) {
    /**
     * Retrieves the value or null.
     *
     * @param value raw value.
     * @return set or null.
     */
    protected fun getOrNull(value: Any?): Set<T>? {
        if (value == null) {
            return null
        }
        if (MutableSet::class.java.isAssignableFrom(value.javaClass)) {
            val valueAsList = value as Set<*>
            return if (valueAsList.isEmpty()) {
                emptySet()
            } else {
                if (memberClazz.isAssignableFrom(valueAsList.iterator().next()!!.javaClass)) {
                  @Suppress("UNCHECKED_CAST")
                  valueAsList as Set<T>
                } else {
                    throw WrongVariableTypeException("Error reading " + variableName + ": Wrong set type detected, expected " + memberClazz.name + ", but was not found in " + valueAsList)
                }
            }
        }
        throw WrongVariableTypeException("Error reading $variableName: Couldn't read value of type Set from $value")
    }

    override fun getTypedValue(value: Any?, isTransient: Boolean): TypedValue {
        return getTypedValue<MutableSet<*>>(MutableSet::class.java, value, isTransient)
    }
}
