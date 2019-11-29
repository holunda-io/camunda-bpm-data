package io.holunda.camunda.bpm.data.adapter;

import java.util.Optional;

/**
 * Adapter to read variables.
 * @param <T> type of value.
 */
public interface ReadAdapter<T> {

  /**
   * Reads a variable.
   * @return value.
   * @exception IllegalStateException if the required variable is missing or can't be read.
   */
  T get();

  /**
   * Reads a variable and returns a value if exists or an empty.
   * @return optional.
   * @exception IllegalStateException if the required variable can't be read.
   */
  Optional<T> getOptional();

}
