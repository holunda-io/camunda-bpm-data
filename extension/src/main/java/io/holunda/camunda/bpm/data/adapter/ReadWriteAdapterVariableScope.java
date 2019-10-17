package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.delegate.VariableScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ReadWriteAdapterVariableScope<T> implements ReadAdapter<T>, WriteAdapter<T> {

  private static final Logger LOG = LoggerFactory.getLogger(ReadWriteAdapterVariableScope.class);
  private VariableScope variableScope;
  private String variableName;
  private Class<T> clazz;

  public ReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> clazz) {
    this.variableScope = variableScope;
    this.variableName = variableName;
    this.clazz = clazz;
  }

  @Override
  public void set(T value) {
    variableScope.setVariable(variableName, ValueWrapperUtil.getTypedValue(clazz, value, false));
  }

  @Override
  public void set(T value, boolean isTransient) {
    variableScope.setVariable(variableName, ValueWrapperUtil.getTypedValue(clazz, value, isTransient));
  }

  @Override
  public void setLocal(T value) {
    variableScope.setVariableLocal(variableName, ValueWrapperUtil.getTypedValue(clazz, value, false));
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
    Object value = variableScope.getVariable(variableName);

    if(value == null)
      return null;

    if (clazz.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read TypedValue of " + clazz + " from " + value);
  }
}
