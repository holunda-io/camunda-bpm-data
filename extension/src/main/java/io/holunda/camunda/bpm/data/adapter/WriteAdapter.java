package io.holunda.camunda.bpm.data.adapter;

/**
 * Write adapter to write values.
 * @param <T> type of values to write.
 */
public interface WriteAdapter<T> {

  /**
   * Writes a value.
   * @param value value to write.
   */
  void set(T value);

  /**
   * Writes a value as a transient variable.
   * @param value value to rite.
   * @param isTransient allows to specify if the variable is transient.
   */
  void set(T value, boolean isTransient);

  /**
   * Writes a local variable.
   * @param value value to write.
   */
  void setLocal(T value);

  /**
   * Writes a local variable.
   * @param value value to write.
   * @param isTransient allows to specify if the variable is transient.
   */
  void setLocal(T value, boolean isTransient);
}
