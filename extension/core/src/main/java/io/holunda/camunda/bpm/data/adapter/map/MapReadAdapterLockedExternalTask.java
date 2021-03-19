package io.holunda.camunda.bpm.data.adapter.map;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Read adapter for external task
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
@SuppressWarnings("unchecked")
public class MapReadAdapterLockedExternalTask<K, V> implements ReadAdapter<Map<K, V>> {

  private final LockedExternalTask lockedExternalTask;
  private final String variableName;
  private Class<K> keyClazz;
  private Class<V> valueClazz;

  public MapReadAdapterLockedExternalTask(LockedExternalTask lockedExternalTask, String variableName, Class<K> keyClazz, Class<V> valueClazz) {
    this.lockedExternalTask = lockedExternalTask;
    this.variableName = variableName;
    this.keyClazz = keyClazz;
    this.valueClazz = valueClazz;
  }

  @Override
  public Map<K, V> get() {
    return null;
  }

  @Override
  @SuppressWarnings("java:S3655")
  public Optional<Map<K,V>> getOptional() {
    return Optional.ofNullable(getOrNull(getValue()));
  }

  @Override
  public Map<K,V> getLocal() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Optional<Map<K,V>> getLocalOptional() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Map<K,V> getOrDefault(Map<K,V> defaultValue) {
    return getOptional().orElse(defaultValue);
  }

  @Override
  public Map<K,V> getLocalOrDefault(Map<K,V> defaultValue) {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @Override
  public Map<K,V> getOrNull() {
    return getOrNull(getValue());
  }

  @Override
  public Map<K,V> getLocalOrNull() {
    throw new UnsupportedOperationException("Can't get a local variable on an external task");
  }

  @SuppressWarnings("Duplicates")
  private Map<K,V> getOrNull(Object value) {
    if (value == null) {
      return null;
    }

    if (Map.class.isAssignableFrom(value.getClass())) {
      Map<?, ?> valueAsMap = (Map<?, ?>) value;
      if (valueAsMap.isEmpty()) {
        return Collections.emptyMap();
      } else {
        Map.Entry<?, ?> entry = valueAsMap.entrySet().iterator().next();
        if (keyClazz.isAssignableFrom(entry.getKey().getClass()) && valueClazz.isAssignableFrom(entry.getValue().getClass())) {
          return (Map<K, V>) valueAsMap;
        } else {
          throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong map type detected, expected Map<"
            + keyClazz.getName() + "," + valueClazz.getName()
            + ", but was not found in " + valueAsMap);
        }
      }
    }

    throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of type Map from " + value);
  }

  private Object getValue() {
    return Optional.ofNullable(lockedExternalTask.getVariables()).map(it -> it.get(variableName)).get();
  }
}
