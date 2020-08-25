package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Inverting calls to {@link io.holunda.camunda.bpm.data.adapter.WriteAdapter}.
 *
 * @param <S> type of concrete Writer for fluent usage.
 */
public interface GlobalVariableWriter<S extends GlobalVariableWriter<S>> {

  /**
   * Sets the value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value           the value
   * @param <T>             type of value
   * @return current writer instance
   * @see io.holunda.camunda.bpm.data.adapter.WriteAdapter#set(Object)
   */
  @NotNull <T> S set(VariableFactory<T> variableFactory, T value);

  /**
   * Sets the (transient) value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value           the value
   * @param isTransient     if true, the variable is transient, default false.
   * @param <T>             type of value
   * @return current writer instance
   * @see io.holunda.camunda.bpm.data.adapter.WriteAdapter#set(Object, boolean)
   */
  @NotNull <T> S set(VariableFactory<T> variableFactory, T value, boolean isTransient);

  /**
   * Sets the global value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param valueProcessor  function updating the value based on the old value.
   * @param <T>             type of value
   * @return current writer instance
   * @see WriteAdapter#setLocal(Object)
   */
  @NotNull <T> S update(VariableFactory<T> variableFactory, Function<T, T> valueProcessor);

  /**
   * Updates the global (transient) value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param valueProcessor  function updating the value based on the old value.
   * @param isTransient     if true, the variable is transient
   * @param <T>             type of value
   * @return current writer instance
   * @see WriteAdapter#setLocal(Object, boolean)
   */
  @NotNull <T> S update(VariableFactory<T> variableFactory, Function<T, T> valueProcessor, boolean isTransient);
  /**
   * Removes the value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param <T>             type of value
   * @return current writer instance
   * @see WriteAdapter#remove()
   */
  @NotNull <T> S remove(VariableFactory<T> variableFactory);

  /**
   * Returns the resulting variables.
   *
   * @return variables.
   */
  @NotNull
  VariableMap variables();

}
