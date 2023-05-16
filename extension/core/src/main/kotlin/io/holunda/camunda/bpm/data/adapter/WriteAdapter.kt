package io.holunda.camunda.bpm.data.adapter

import org.camunda.bpm.engine.variable.value.TypedValue
import java.util.function.Function

/**
 * Write adapter to write values.
 *
 * @param [T] type of values to write.
 */
interface WriteAdapter<T> {
    /**
     * Writes a value.
     *
     * @param value value to write.
     */
    fun set(value: T)

    /**
     * Writes a value as a transient variable.
     *
     * @param value       value to write.
     * @param isTransient allows to specify if the variable is transient.
     */
    operator fun set(value: T, isTransient: Boolean)

    /**
     * Writes a local variable.
     *
     * @param value value to write.
     */
    fun setLocal(value: T)

    /**
     * Writes a local variable.
     *
     * @param value       value to write.
     * @param isTransient allows to specify if the variable is transient.
     */
    fun setLocal(value: T, isTransient: Boolean)

    /**
     * Removes a variable from the scope.
     */
    fun remove()

    /**
     * Removes a local variable from the scope.
     */
    fun removeLocal()

    /**
     * Updates a variable using provided value processor.
     * If the value is unchanged, the variable is not touched.
     *
     * @param valueProcessor function updating the value based on the old value.
     */
    fun update(valueProcessor: Function<T, T>)

    /**
     * Updates a local variable using provided value processor.
     * If the value is unchanged, the variable is not touched.
     *
     * @param valueProcessor function updating the value based on the old value.
     */
    fun updateLocal(valueProcessor: Function<T, T>)

    /**
     * Updates a variable using provided value processor.
     * <br></br>If the value is unchanged, the variable is not touched.
     *
     * @param valueProcessor function updating the value based on the old value.
     * @param isTransient    transient flag.
     */
    fun update(valueProcessor: Function<T, T>, isTransient: Boolean)

    /**
     * Updates a local variable using provided value processor.
     * <br></br>If the value is unchanged, the variable is not touched.
     *
     * @param valueProcessor function updating the value based on the old value.
     * @param isTransient    transient flag.
     */
    fun updateLocal(valueProcessor: Function<T, T>, isTransient: Boolean)

    /**
     * Constructs typed value.
     *
     * @param value       raw value.
     * @param isTransient transient flag.
     * @return typed value.
     */
    fun getTypedValue(value: Any?, isTransient: Boolean): TypedValue
}
