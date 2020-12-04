package io.holunda.camunda.bpm.data.adapter;

import java.util.Optional;

/**
 * Adapter to read variables.
 *
 * @param <T> type of value.
 */
public interface ReadAdapter<T> {

  /**
   * Reads a variable.
   *
   * @return value.
   * @exception VariableNotFoundException if the required variable is missing or can't be read.
   */
  T get();

  /**
   * Reads a variable and returns a value if exists or an empty.
   *
   * @return optional.
   */
  Optional<T> getOptional();

  /**
   * Reads a local variable.
   *
   * @return value.
   * @exception VariableNotFoundException if the required variable is missing or can't be read.
   */
  T getLocal();

  /**
   * Reads a local variable and returns a value if exists or an empty.
   *
   * @return optional.
   */
  Optional<T> getLocalOptional();

  /**
   * Reads a variable and returns a value if exists or default.
   *
   * @param defaultValue the default value if the variable is not set
   * @return value or default
   */
  default T getOrDefault(T defaultValue) {
    return getOptional().orElse(defaultValue);
  }

  /**
   * Reads a local variable and returns a value if exists or default.
   *
   * @param defaultValue the default value if the variable is not set
   * @return value or default
   */
  default T getLocalOrDefault(T defaultValue) {
    return getLocalOptional().orElse(defaultValue);
  }

  /**
   * Reads a variable and returns a value if exists or null.
   *
   * @return value or <code>null</code>>
   */
  default T getOrNull() {
    return getOptional().orElse(null);
  }

  /**
   * Reads a local variable and returns a value if exists or null.
   *
   * @return value or <code>null</code>>
   */
  default T getLocalOrNull() {
    return getLocalOptional().orElse(null);
  }
}
