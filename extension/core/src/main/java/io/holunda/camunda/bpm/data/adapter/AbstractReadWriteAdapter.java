package io.holunda.camunda.bpm.data.adapter;

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
  public AbstractReadWriteAdapter(String variableName) {
    this.variableName = variableName;
  }

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
    T oldValue = get();
    T newValue = valueProcessor.apply(oldValue);
    if (!oldValue.equals(newValue)) {
      // touch only if the value changes
      set(newValue, isTransient);
    }
  }

  @Override
  public void updateLocal(Function<T, T> valueProcessor, boolean isTransient) {
    T oldValue = getLocal();
    T newValue = valueProcessor.apply(oldValue);
    if (!oldValue.equals(newValue)) {
      // touch only if the value changes
      setLocal(newValue, isTransient);
    }
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
