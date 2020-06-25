package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

/**
 * Inverting calls to {@link WriteAdapter}.
 *
 * @param <S> type of concrete Writer for fluent usage.
 */
public interface LocalVariableWriter<S extends LocalVariableWriter<S>> extends VariableWriter<S> {

  /**
   * Sets the local value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value the value
   * @param <T> type of value
   * @return current writer instance
   * @see WriteAdapter#setLocal(Object)
   */
  <T> S setLocal(VariableFactory<T> variableFactory, T value);

  /**
   * Sets the local (transient) value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value the value
   * @param isTransient if true, the variable is transient
   * @param <T> type of value
   * @return current writer instance
   * @see WriteAdapter#setLocal(Object, boolean)
   */
  <T> S setLocal(VariableFactory<T> variableFactory, T value, boolean isTransient);

  /**
   * Removes the local value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param <T> type of value
   * @return current writer instance
   * @see WriteAdapter#removeLocal()
   */
  <T> S removeLocal(VariableFactory<T> variableFactory);

  /**
   * Returns the resulting local variables.
   *
   * @return local variables.
   */
  @NotNull
  VariableMap variablesLocal();
}
