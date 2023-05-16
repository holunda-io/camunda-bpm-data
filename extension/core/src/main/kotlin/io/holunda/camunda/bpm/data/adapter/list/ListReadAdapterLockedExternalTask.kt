package io.holunda.camunda.bpm.data.adapter.list

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Read adapter for external task.
 *
 * @param [T] type of value.
 */
class ListReadAdapterLockedExternalTask<T>(
    private val lockedExternalTask: LockedExternalTask,
    private val variableName: String,
    private val memberClazz: Class<T>
) : ReadAdapter<List<T>> {

    override fun get(): List<T> {
        return getOptional().get()
    }

    override fun getOptional(): Optional<List<T>> {
        return Optional.ofNullable(getOrNull(value))
    }

    override fun getLocal(): List<T> {
        throw UnsupportedOperationException("Can't get a local variable on an external task")
    }

    override fun getLocalOptional(): Optional<List<T>> {
        throw UnsupportedOperationException("Can't get a local variable on an external task")
    }

    override fun getOrDefault(defaultValue: List<T>): List<T> {
        return getOptional().orElse(defaultValue)
    }

    override fun getLocalOrDefault(defaultValue: List<T>): List<T> {
        throw UnsupportedOperationException("Can't get a local variable on an external task")
    }

    override fun getOrNull(): List<T>? {
        return getOrNull(value)
    }

    override fun getLocalOrNull(): List<T> {
        throw UnsupportedOperationException("Can't get a local variable on an external task")
    }

    private fun getOrNull(value: T?): List<T>? {
        if (value == null) {
            return null
        }
        if (MutableList::class.java.isAssignableFrom(value.javaClass)) {
            val valueAsList = value as List<*>
            return if (valueAsList.isEmpty()) {
                emptyList()
            } else {
                if (memberClazz.isAssignableFrom(valueAsList.iterator().next()!!.javaClass)) {
                    valueAsList as List<T>
                } else {
                    throw WrongVariableTypeException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.name + ", but was not found in " + valueAsList)
                }
            }
        }
        throw WrongVariableTypeException("Error reading $variableName: Couldn't read value of type List from $value")
    }

    private val value: T?
        get() = Optional.ofNullable(lockedExternalTask.variables)
            .orElse(Variables.createVariables())[variableName] as T?
}
