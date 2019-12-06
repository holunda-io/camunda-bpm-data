package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;

/**
 * Read write adapter for runtime service access.
 * @param <T> type of value.
 */
public class ReadWriteAdapterRuntimeService<T> implements ReadAdapter<T>, WriteAdapter<T> {

  private final RuntimeService runtimeService;
  private final String executionId;
  private final String variableName;
  private final Class<T> clazz;

  /**
   * Constructs the adapter.
   * @param runtimeService runtime service to use.
   * @param executionId id of the execution to read from.
   * @param variableName name of the variable.
   * @param clazz class of the variable.
   */
  public ReadWriteAdapterRuntimeService(RuntimeService runtimeService, String executionId, String variableName, Class<T> clazz) {
    this.runtimeService = runtimeService;
    this.executionId = executionId;
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
    runtimeService.setVariable(executionId, variableName, typedValue);
  }

  @Override
  public void setLocal(T value) {
    setLocal(value, false);
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
    final TypedValue typedValue = ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
    runtimeService.setVariableLocal(executionId, variableName, typedValue);
  }

  @SuppressWarnings("unchecked")
  private T getOrNull() {
    final Object value = runtimeService.getVariable(executionId, variableName);

    if (value == null) {
      return null;
    }

    if (clazz.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read value of " + clazz + " from " + value);
  }

}
