package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;

/**
 * Read-write adapter for variable map.
 *
 * @param <T> type of value.
 */
public class ReadWriteAdapterVariableMap<T> implements ReadAdapter<T>, WriteAdapter<T> {

  private final VariableMap variableMap;
  private final String variableName;
  private final Class<T> clazz;

  /**
   * Constructs the adapter.
   *
   * @param variableMap  variable map to access.
   * @param variableName variable to access.
   * @param clazz        class of variable value.
   */
  public ReadWriteAdapterVariableMap(VariableMap variableMap, String variableName, Class<T> clazz) {
    this.variableMap = variableMap;
    this.variableName = variableName;
    this.clazz = clazz;
  }

  @Override
  public T get() {
    T value = getOrNull();
    if (value == null) {
      throw new IllegalStateException("Couldn't find required variable " + variableName);
    }
    return value;
  }

  @Override
  public Optional<T> getOptional() {
    return Optional.ofNullable(getOrNull());
  }

  @Override
  public void set(T value) {
    set(value, false);
  }

  @Override
  public void set(T value, boolean isTransient) {
    final TypedValue typedValue = ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
    variableMap.putValueTyped(variableName, typedValue);
  }

  @Override
  public void setLocal(T value) {
    throw new UnsupportedOperationException("Can't set a local variable on a variable map");
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
    throw new UnsupportedOperationException("Can't set a local variable on a variable map");
  }

  @SuppressWarnings("unchecked")
  private T getOrNull() {
    final Object value = variableMap.get(variableName);

    if (value == null) {
      return null;
    }

    if (clazz.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read value of " + clazz + " from " + value);
  }

}
