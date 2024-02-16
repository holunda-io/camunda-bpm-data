package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import java.util.*

/**
 * Inverting calls to [io.holunda.camunda.bpm.data.adapter.ReadAdapter].
 */
interface VariableReader {
    /**
     * Uses [io.holunda.camunda.bpm.data.adapter.ReadAdapter.getOptional] to access variable value.
     *
     * @param variableFactory the variable to read
     * @param <T>             type of value
     * @return value of variable or empty()
    </T> */
    fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T>

    /**
     * Uses [io.holunda.camunda.bpm.data.adapter.ReadAdapter.get]  to access variable value.
     *
     * @param variableFactory the variable to read
     * @param <T>             type of value
     * @return value of variable
     * @throws IllegalStateException if variable is not set
    </T> */
    operator fun <T> get(variableFactory: VariableFactory<T>): T

    /**
     * Uses [io.holunda.camunda.bpm.data.adapter.ReadAdapter.getLocal] to access variable value.
     *
     * @param variableFactory the variable to read
     * @param <T>             type of value
     * @return value of variable
     * @throws IllegalStateException if variable is not set
    </T> */
    fun <T> getLocal(variableFactory: VariableFactory<T>): T

    /**
     * Uses [io.holunda.camunda.bpm.data.adapter.ReadAdapter.getLocalOptional] ()}  to access variable value.
     *
     * @param variableFactory the variable to read
     * @param <T>             type of value
     * @return value of variable or empty()
    </T> */
    fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T>

    /**
     * Uses [io.holunda.camunda.bpm.data.adapter.ReadAdapter.getOptional] to access variable value. If the optional is empty returns `null`
     *
     * @param variableFactory the variable to read
     * @param <T>             type of value
     * @return value of variable or `null` if the variable is not present or has value `null`.
    </T> */
    fun <T> getOrNull(variableFactory: VariableFactory<T>): T? {
        return getOptional(variableFactory).orElse(null)
    }

    /**
     * Reads a variable and returns a value if exists or returns the provided default.
     *
     * @param variableFactory   the variable to read
     * @param defaultValue      the default value if the variable is not set
     * @return value or default
     */
    fun <T> getOrDefault(variableFactory: VariableFactory<T>, defaultValue: T?): T? {
        return getOptional(variableFactory).orElse(defaultValue)
    }

    /**
     * Uses [io.holunda.camunda.bpm.data.adapter.ReadAdapter.getLocalOptional] to access variable value. If the optional is empty returns `null`
     *
     * @param variableFactory the variable to read
     * @param <T>             type of value
     * @return value of variable or `null` if the variable is not present or has value `null`.
    </T> */
    fun <T> getLocalOrNull(variableFactory: VariableFactory<T>): T? {
        return getLocalOptional(variableFactory).orElse(null)
    }

    /**
     * Reads a local variable and returns a value if exists or returns the provided default.
     *
     * @param variableFactory   the variable to read
     * @param defaultValue      the default value if the variable is not set
     * @return value or default
     */
    fun <T> getLocalOrDefault(variableFactory: VariableFactory<T>, defaultValue: T): T {
        return getLocalOptional(variableFactory).orElse(defaultValue)
    }
}
