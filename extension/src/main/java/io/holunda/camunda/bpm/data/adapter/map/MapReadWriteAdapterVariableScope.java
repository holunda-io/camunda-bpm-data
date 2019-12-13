package io.holunda.camunda.bpm.data.adapter.map;

import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.Map;
import java.util.Optional;

/**
 * Read-write adapter for variable scope.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class MapReadWriteAdapterVariableScope<K, V> extends AbstractMapReadWriteAdapter<K, V> {

  private VariableScope variableScope;

  /**
   * Constructs the adapter.
   *
   * @param variableScope variable scope to access.
   * @param variableName  variable to access.
   * @param keyClazz      class of variable key.
   * @param valueClazz    class of variable value.
   */
  public MapReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<K> keyClazz, Class<V> valueClazz) {
    super(variableName, keyClazz, valueClazz);
    this.variableScope = variableScope;
  }

  @Override
  public Optional<Map<K, V>> getOptional() {
    return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)));
  }

  @Override
  public void set(Map<K, V> value, boolean isTransient) {
    variableScope.setVariable(variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<Map<K, V>> getLocalOptional() {
    return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)));
  }

  @Override
  public void setLocal(Map<K, V> value, boolean isTransient) {
    variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient));
  }
  @Override
  public void remove() {
    variableScope.removeVariable(variableName);
  }

  @Override
  public void removeLocal() {
    variableScope.removeVariableLocal(variableName);
  }

}
