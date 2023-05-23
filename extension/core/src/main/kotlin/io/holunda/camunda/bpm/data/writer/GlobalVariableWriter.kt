package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Inverting calls to [io.holunda.camunda.bpm.data.adapter.WriteAdapter].
 *
 * @param <S> type of concrete Writer for fluent usage.
</S> */
interface GlobalVariableWriter<S : GlobalVariableWriter<S>> {
    /**
     * Sets the value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param value           the value
     * @param <T>             type of value
     * @return current writer instance
     * @see [io.holunda.camunda.bpm.data.adapter.WriteAdapter.set]
    </T> */
    operator fun <T> set(variableFactory: VariableFactory<T>, value: T): S

    /**
     * Sets the (transient) value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param value           the value
     * @param isTransient     if true, the variable is transient, default false.
     * @param <T>             type of value
     * @return current writer instance
     * @see [io.holunda.camunda.bpm.data.adapter.WriteAdapter.set]
    </T> */
    operator fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): S

    /**
     * Sets the global value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param valueProcessor  function updating the value based on the old value.
     * @param <T>             type of value
     * @return current writer instance
     * @see [io.holunda.camunda.bpm.data.adapter.WriteAdapter.setLocal]
    </T> */
    fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): S

    /**
     * Updates the global (transient) value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param valueProcessor  function updating the value based on the old value.
     * @param isTransient     if true, the variable is transient
     * @param <T>             type of value
     * @return current writer instance
     * @see [io.holunda.camunda.bpm.data.adapter.WriteAdapter.setLocal]
    </T> */
    fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>, isTransient: Boolean): S

    /**
     * Removes the value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param <T>             type of value
     * @return current writer instance
     * @see [io.holunda.camunda.bpm.data.adapter.WriteAdapter.remove]
    </T> */
    fun <T> remove(variableFactory: VariableFactory<T>): S

    /**
     * Returns the resulting variables.
     *
     * @return variables.
     */
    fun variables(): VariableMap
}
