package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;

/**
 * Read-write adapter for variable scope.
 * @param <T> type of value.
 */
public class ReadWriteAdapterVariableScope<T> implements ReadAdapter<T>, WriteAdapter<T> {

  private VariableScope variableScope;
  private String variableName;
  private Class<T> clazz;

  /**
   * Constructs the adapter.
   * @param variableScope variable scope to access.
   * @param variableName variable to access.
   * @param clazz class of variable value.
   */
  public ReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> clazz) {
    this.variableScope = variableScope;
    this.variableName = variableName;
    this.clazz = clazz;
  }

  @Override
  public void set(T value) {
    set(value, false);
  }

  @Override
  public void set(T value, boolean isTransient) {
    final TypedValue typedValue = ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
    variableScope.setVariable(variableName, typedValue);
  }

  @Override
  public void setLocal(T value) {
    setLocal(value, false);
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
    variableScope.setVariableLocal(variableName, ValueWrapperUtil.getTypedValue(clazz, value, isTransient));
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

  @SuppressWarnings("unchecked")
  private T getOrNull() {
    final Object value = variableScope.getVariable(variableName);

    if(value == null) {
      return null;
    }

    if (clazz.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read value of " + clazz + " from " + value);
  }
}
