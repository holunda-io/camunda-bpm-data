package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.function.Function;

/**
 * Write adapter to write values.
 *
 * @param <T> type of values to write.
 */
public interface WriteAdapter<T> {

  /**
   * Writes a value.
   *
   * @param value value to write.
   */
  void set(T value);

  /**
   * Writes a value as a transient variable.
   *
   * @param value       value to write.
   * @param isTransient allows to specify if the variable is transient.
   */
  void set(T value, boolean isTransient);

  /**
   * Writes a local variable.
   *
   * @param value value to write.
   */
  void setLocal(T value);

  /**
   * Writes a local variable.
   *
   * @param value       value to write.
   * @param isTransient allows to specify if the variable is transient.
   */
  void setLocal(T value, boolean isTransient);

  /**
   * Removes a variable from the scope.
   */
  void remove();

  /**
   * Removes a local variable from the scope.
   */
  void removeLocal();

  /**
   * Updates a variable using provided value processor.
   *
   * @param valueProcessor function updating the value based on the old value.
   */
  void update(Function<T, T> valueProcessor);

  /**
   * Updates a local variable using provided value processor.
   *
   * @param valueProcessor function updating the value based on the old value.
   */
  void updateLocal(Function<T, T> valueProcessor);

  /**
   * Updates a variable using provided value processor.
   *
   * @param valueProcessor function updating the value based on the old value.
   * @param isTransient    transient flag.
   */
  void update(Function<T, T> valueProcessor, boolean isTransient);

  /**
   * Updates a local variable using provided value processor.
   *
   * @param valueProcessor function updating the value based on the old value.
   * @param isTransient    transient flag.
   */
  void updateLocal(Function<T, T> valueProcessor, boolean isTransient);

  /**
   * Constructs typed value.
   *
   * @param value       raw value.
   * @param isTransient transient flag.
   * @return typed value.
   */
  TypedValue getTypedValue(Object value, boolean isTransient);
}
