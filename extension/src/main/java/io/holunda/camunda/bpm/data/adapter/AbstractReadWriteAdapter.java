package io.holunda.camunda.bpm.data.adapter;

/**
 * Abstract read write adapter.
 * @param <T> variable type.
 */
public abstract class AbstractReadWriteAdapter<T> implements ReadAdapter<T>, WriteAdapter<T> {

  /**
   * Variable name.
   */
  protected final String variableName;

  /**
   * Constructs the adapter.
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
    return getOptional().orElseThrow(() -> new VariableNotFoundException("Couldn't find required variable " + variableName));
  }

  @Override
  public T getLocal() {
    return getLocalOptional().orElseThrow(() -> new VariableNotFoundException("Couldn't find required local variable " + variableName));
  }
}
