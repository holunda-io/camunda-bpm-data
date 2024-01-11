package io.holunda.camunda.bpm.data.adapter

import java.util.*

/**
 * Adapter to read variables.
 *
 * @param [T] type of value.
 */
interface ReadAdapter<T> {
  /**
   * Reads a variable.
   *
   * @return value.
   * @exception VariableNotFoundException if the required variable is missing or can't be read.
   */
  fun get(): T

  /**
   * Reads a variable and returns a value if exists or an empty.
   *
   * @return optional.
   */

  fun getOptional(): Optional<T>

  /**
   * Reads a local variable.
   *
   * @return value.
   * @exception VariableNotFoundException if the required variable is missing or can't be read.
   */
  fun getLocal(): T

  /**
   * Reads a local variable and returns a value if exists or an empty.
   *
   * @return optional.
   */
  fun getLocalOptional(): Optional<T>

  /**
   * Reads a variable and returns a value if exists or default.
   *
   * @param defaultValue the default value if the variable is not set
   * @return value or default
   */
  fun getOrDefault(defaultValue: T): T {
    return getOptional().orElse(defaultValue)
  }

  /**
   * Reads a local variable and returns a value if exists or default.
   *
   * @param defaultValue the default value if the variable is not set
   * @return value or default
   */
  fun getLocalOrDefault(defaultValue: T): T {
    return getLocalOptional().orElse(defaultValue)
  }

  /**
   * Reads a variable and returns a value if exists or null.
   *
   * @return value or `null`
   */
  fun getOrNull(): T? {
    return getOptional().orElse(null)
  }

  /**
   * Reads a local variable and returns a value if exists or null.
   *
   * @return value or `null`
   */
  fun getLocalOrNull(): T? {
    return getLocalOptional().orElse(null)
  }
}
