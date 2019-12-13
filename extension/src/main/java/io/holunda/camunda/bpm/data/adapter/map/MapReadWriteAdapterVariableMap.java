package io.holunda.camunda.bpm.data.adapter.map;

import org.camunda.bpm.engine.variable.VariableMap;

import java.util.Map;
import java.util.Optional;

/**
 * Read-write adapter for variable map.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class MapReadWriteAdapterVariableMap<K, V> extends AbstractMapReadWriteAdapter<K, V> {

  private final VariableMap variableMap;

  /**
   * Constructs the adapter.
   *
   * @param variableMap  variable map to access.
   * @param variableName variable to access.
   * @param keyClazz     class of variable key.
   * @param valueClazz   class of variable value.
   */
  public MapReadWriteAdapterVariableMap(VariableMap variableMap, String variableName, Class<K> keyClazz, Class<V> valueClazz) {
    super(variableName, keyClazz, valueClazz);
    this.variableMap = variableMap;
  }

  @Override
  public Optional<Map<K, V>> getOptional() {
    return Optional.ofNullable(getOrNull(variableMap.get(variableName)));
  }

  @Override
  public void set(Map<K, V> value, boolean isTransient) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<Map<K, V>> getLocalOptional() {
    throw new UnsupportedOperationException("Can't get a local variable on a variable map");
  }

  @Override
  public void setLocal(Map<K, V> value, boolean isTransient) {
    throw new UnsupportedOperationException("Can't set a local variable on a variable map");
  }

  @Override
  public void remove() {
    variableMap.remove(variableName);
  }

  @Override
  public void removeLocal() {
    throw new UnsupportedOperationException("Can't set a local variable on a variable map");
  }

}
