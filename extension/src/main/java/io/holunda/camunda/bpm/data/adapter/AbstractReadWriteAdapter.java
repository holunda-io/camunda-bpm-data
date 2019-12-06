package io.holunda.camunda.bpm.data.adapter;

public abstract class AbstractReadWriteAdapter<T> implements ReadAdapter<T>, WriteAdapter<T> {
  protected final String variableName;

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
    return getOptional().orElseThrow(() -> new IllegalStateException("Couldn't find required variable " + variableName));
  }
}
