package io.holunda.camunda.bpm.data.adapter.list;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Read adapter for external task.
 *
 * @param <T> type of value.
 */
@SuppressWarnings("unchecked")
public class ListReadAdapterLockedExternalTask<T> implements ReadAdapter<List<T>> {

  private final LockedExternalTask lockedExternalTask;
  private final String variableName;
  private final Class<T> memberClazz;

  public ListReadAdapterLockedExternalTask(LockedExternalTask lockedExternalTask, String variableName, Class<T> memberClazz) {
    this.lockedExternalTask = lockedExternalTask;
    this.variableName = variableName;
    this.memberClazz = memberClazz;
  }

  @Override
  public List<T> get() {
    return null;
  }

  @Override
  @SuppressWarnings("java:S3655")
  public Optional<List<T>> getOptional() {
    return Optional.ofNullable(getOrNull(getValue()));
  }

  @Override
  public List<T> getLocal() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Optional<List<T>> getLocalOptional() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public List<T> getOrDefault(List<T> defaultValue) {
    return getOptional().orElse(defaultValue);
  }

  @Override
  public List<T> getLocalOrDefault(List<T> defaultValue) {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public List<T> getOrNull() {
    return getOrNull(getValue());
  }

  @Override
  public List<T> getLocalOrNull() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @SuppressWarnings("Duplicates")
  private List<T> getOrNull(T value) {
    if (value == null) {
      return null;
    }

    if (List.class.isAssignableFrom(value.getClass())) {
      List<?> valueAsList = (List<?>) value;
      if (valueAsList.isEmpty()) {
        return Collections.emptyList();
      } else {
        if (memberClazz.isAssignableFrom(valueAsList.iterator().next().getClass())) {
          return (List<T>) valueAsList;
        } else {
          throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.getName() + ", but was not found in " + valueAsList);
        }
      }
    }

    throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of type List from " + value);
  }

  private T getValue() {
    return (T) Optional.ofNullable(lockedExternalTask.getVariables()).map(it -> it.get(variableName)).get();
  }
}
