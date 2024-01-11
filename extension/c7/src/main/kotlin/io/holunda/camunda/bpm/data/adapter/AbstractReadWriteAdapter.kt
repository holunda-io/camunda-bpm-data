package io.holunda.camunda.bpm.data.adapter

import java.util.function.Function

/**
 * Abstract read write adapter.
 *
 * @param [T]          variable type.
 * @param variableName variable name,
 */
abstract class AbstractReadWriteAdapter<T>(protected val variableName: String) : ReadAdapter<T>, WriteAdapter<T> {
    override fun set(value: T) {
        set(value, false)
    }

    override fun setLocal(value: T) {
        setLocal(value, false)
    }

    override fun get(): T {
        return getOptional().orElseThrow { VariableNotFoundException("Couldn't find required variable '$variableName'") }
    }

    override fun getLocal(): T {
        return getLocalOptional().orElseThrow { VariableNotFoundException("Couldn't find required local variable '$variableName'") }
    }

    override fun update(valueProcessor: Function<T, T>, isTransient: Boolean) {
        val oldValue = get()
        val newValue = valueProcessor.apply(oldValue)
        if (oldValue != newValue) {
            // touch only if the value changes
            set(newValue, isTransient)
        }
    }

    override fun updateLocal(valueProcessor: Function<T, T>, isTransient: Boolean) {
        val oldValue = getLocal()
        val newValue = valueProcessor.apply(oldValue)
        if (oldValue != newValue) {
            // touch only if the value changes
            setLocal(newValue, isTransient)
        }
    }

    override fun update(valueProcessor: Function<T, T>) {
        update(valueProcessor, false)
    }

    override fun updateLocal(valueProcessor: Function<T, T>) {
        updateLocal(valueProcessor, false)
    }
}
