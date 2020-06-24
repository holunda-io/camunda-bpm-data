package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Inverting calls to {@link io.holunda.camunda.bpm.data.adapter.WriteAdapter}.
 *
 * @param <S> type of concrete Writer for fluent usage.
 */
public interface VariableWriter<S extends VariableWriter<S>> {

  /**
   * Sets the value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value the value
   * @param <T> type of value
   * @return current writer instance
   * @see io.holunda.camunda.bpm.data.adapter.WriteAdapter#set(Object)
   */
  <T> S set(VariableFactory<T> variableFactory, T value);

  /**
   * Sets the (transient) value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value the value
   * @param isTransient if true, the variable is transient, default false.
   * @param <T> type of value
   * @return current writer instance
   * @see io.holunda.camunda.bpm.data.adapter.WriteAdapter#set(Object, boolean)
   */
  <T> S set(VariableFactory<T> variableFactory, T value, boolean isTransient);

  /**
   * Removes the value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param <T> type of value
   * @return current writer instance
   * @see WriteAdapter#remove()
   */
  <T> S remove(VariableFactory<T> variableFactory);

  /**
   * Returns the resulting variables.
   *
   * @return variables.
   */
  VariableMap variables();

}
