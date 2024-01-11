package io.holunda.camunda.bpm.data.builder

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.writer.VariableMapWriter
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Builder to create [VariableMap] using [VariableFactory].
 */
class VariableMapBuilder {

    private val writer: VariableMapWriter = VariableMapWriter(Variables.createVariables())

  /**
     * Sets the value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param value           the value
     * @param <T>             type of value
     * @return current builder instance
    </T> */
    fun <T> set(variableFactory: VariableFactory<T>, value: T): VariableMapBuilder {
        writer.set(variableFactory, value)
        return this
    }

    /**
     * Sets the (transient) value for the provided variable and returns the builder (fluently).
     *
     * @param variableFactory the variable
     * @param value           the value
     * @param isTransient     if true, the variable is transient, default false.
     * @param <T>             type of value
     * @return current builder instance
    </T> */
    fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): VariableMapBuilder {
        writer.set(variableFactory, value, isTransient)
        return this
    }

    /**
     * Creates the variable map.
     *
     * @return instance of [VariableMap] containing set values
     */
    fun build(): VariableMap {
        return Variables.fromMap(Collections.unmodifiableMap(writer.variables()))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as VariableMapBuilder
        return writer == that.writer
    }

    override fun hashCode(): Int {
        return writer.hashCode()
    }
}
