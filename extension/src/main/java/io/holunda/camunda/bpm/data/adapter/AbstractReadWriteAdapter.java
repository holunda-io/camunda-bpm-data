package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.function.Function;

/**
 * Abstract read write adapter.
 *
 * @param <T> variable type.
 */
public abstract class AbstractReadWriteAdapter<T> implements ReadAdapter<T>, WriteAdapter<T> {

  /**
   * Variable name.
   */
  protected final String variableName;

  /**
   * Constructs the adapter.
   *
   * @param variableName variable name,
   */
  public AbstractReadWriteAdapter(String variableName) {this.variableName = variableName;}

  @Override
  public void set(T value) {
    set(value, false);
  }

  @Override
  public void setLocal(T value) {
    setLocal(value, false);
  }

  @Override
  public T get() {
    return getOptional().orElseThrow(() -> new VariableNotFoundException("Couldn't find required variable '" + variableName + "'"));
  }

  @Override
  public T getLocal() {
    return getLocalOptional().orElseThrow(() -> new VariableNotFoundException("Couldn't find required local variable '" + variableName + "'"));
  }

  @Override
  public void update(Function<T, T> valueProcessor, boolean isTransient) {
    set(valueProcessor.apply(get()), isTransient);
  }

  @Override
  public void updateLocal(Function<T, T> valueProcessor, boolean isTransient) {
    setLocal(valueProcessor.apply(getLocal()), isTransient);
  }

  @Override
  public void update(Function<T, T> valueProcessor) {
    update(valueProcessor, false);
  }

  @Override
  public void updateLocal(Function<T, T> valueProcessor) {
    updateLocal(valueProcessor, false);
  }

}
