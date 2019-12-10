package io.holunda.camunda.bpm.data.adapter.set;

import org.camunda.bpm.engine.variable.VariableMap;

import java.util.Optional;
import java.util.Set;

/**
 * Read-write adapter for variable map.
 *
 * @param <T> type of value.
 */
public class SetReadWriteAdapterVariableMap<T> extends AbstractSetReadWriteAdapter<T> {

  private final VariableMap variableMap;

  /**
   * Constructs the adapter.
   *
   * @param variableMap  variable map to access.
   * @param variableName variable to access.
   * @param memberClazz  class of variable value.
   */
  public SetReadWriteAdapterVariableMap(VariableMap variableMap, String variableName, Class<T> memberClazz) {
    super(variableName, memberClazz);
    this.variableMap = variableMap;
  }

  @Override
  public Optional<Set<T>> getOptional() {
    return Optional.ofNullable(getOrNull(variableMap.get(variableName)));
  }

  @Override
  public void set(Set<T> value, boolean isTransient) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient));
  }

  @Override
  public void setLocal(Set<T> value, boolean isTransient) {
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
