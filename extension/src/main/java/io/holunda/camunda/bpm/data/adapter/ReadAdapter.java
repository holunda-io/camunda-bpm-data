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
   */
  T get();

  /**
   * Reads a variable and returns a value if exists or an empty.
   * @return optional.
   */
  Optional<T> getOptional();

}
