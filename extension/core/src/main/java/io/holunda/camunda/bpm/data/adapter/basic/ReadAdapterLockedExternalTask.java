package io.holunda.camunda.bpm.data.adapter.basic;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.VariableNotFoundException;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;

import java.util.Optional;

/**
 * Read-write adapter for external task.
 *
 * @param <T> type of value.
 */
@SuppressWarnings("java:S1192")
public class ReadAdapterLockedExternalTask<T> implements ReadAdapter<T> {

  private final LockedExternalTask lockedExternalTask;
  private final String variableName;

  public ReadAdapterLockedExternalTask(LockedExternalTask lockedExternalTask, String variableName) {
    this.lockedExternalTask = lockedExternalTask;
    this.variableName = variableName;
  }

  @Override
  public T get() {
    return getOptional().orElseThrow(() -> new VariableNotFoundException("Couldn't find required variable '" + variableName + "'"));
  }

  @Override
  @SuppressWarnings("unchecked")
  public Optional<T> getOptional() {
    return (Optional<T>) Optional.ofNullable(lockedExternalTask.getVariables())
      .map(it -> it.get(variableName));
  }

  @Override
  public T getLocal() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Optional<T> getLocalOptional() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public T getOrDefault(T defaultValue) {
    return getOptional().orElse(defaultValue);
  }

  @Override
  public T getLocalOrDefault(T defaultValue) {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public T getOrNull() {
    return getOptional().orElse(null);
  }

  @Override
  public T getLocalOrNull() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

}
