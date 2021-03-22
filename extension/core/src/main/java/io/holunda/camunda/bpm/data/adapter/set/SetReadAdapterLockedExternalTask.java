package io.holunda.camunda.bpm.data.adapter.set;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Read adapter for external task.
 *
 * @param <T> type of value.
 */
@SuppressWarnings("unchecked")
public class SetReadAdapterLockedExternalTask<T> implements ReadAdapter<Set<T>> {

  private final LockedExternalTask lockedExternalTask;
  private final String variableName;
  private final Class<T> memberClazz;

  public SetReadAdapterLockedExternalTask(LockedExternalTask lockedExternalTask, String variableName, Class<T> memberClazz) {
    this.lockedExternalTask = lockedExternalTask;
    this.variableName = variableName;
    this.memberClazz = memberClazz;
  }

  @Override
  public Set<T> get() {
    return null;
  }

  @Override
  @SuppressWarnings("java:S3655")
  public Optional<Set<T>> getOptional() {
    return Optional.ofNullable(getOrNull());
  }

  @Override
  public Set<T> getLocal() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Optional<Set<T>> getLocalOptional() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Set<T> getOrDefault(Set<T> defaultValue) {
    return getOptional().orElse(defaultValue);
  }

  @Override
  public Set<T> getLocalOrDefault(Set<T> defaultValue) {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Set<T> getOrNull() {
    return getOrNull(getValue());
  }

  @Override
  public Set<T> getLocalOrNull() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @SuppressWarnings("Duplicates")
  private Set<T> getOrNull(T value) {
    if (value == null) {
      return null;
    }

    if (List.class.isAssignableFrom(value.getClass())) {
      List<?> valueAsSet = (List<?>) value;
      if (valueAsSet.isEmpty()) {
        return Collections.emptySet();
      } else {
        if (memberClazz.isAssignableFrom(valueAsSet.iterator().next().getClass())) {
          return (Set<T>) valueAsSet;
        } else {
          throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.getName() + ", but was not found in " + valueAsSet);
        }
      }
    }

    throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of type List from " + value);
  }

  private T getValue() {
    return (T) Optional.ofNullable(lockedExternalTask.getVariables()).map(it -> it.get(variableName)).get();
  }
}
