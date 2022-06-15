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

  private final ReadAdapter<T> readAdapter;

  public ReadAdapterLockedExternalTask(LockedExternalTask lockedExternalTask, String variableName, Class<T> clazz) {
    readAdapter = new ReadWriteAdapterVariableMap<>(lockedExternalTask.getVariables(), variableName, clazz);
  }

  @Override
  public T get() {
    return readAdapter.get();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Optional<T> getOptional() {
    return readAdapter.getOptional();
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
    return readAdapter.getOrDefault(defaultValue);
  }

  @Override
  public T getLocalOrDefault(T defaultValue) {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public T getOrNull() {
    return readAdapter.getOrNull();
  }

  @Override
  public T getLocalOrNull() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

}
