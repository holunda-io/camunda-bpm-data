package io.holunda.camunda.bpm.data.adapter.list;

import org.camunda.bpm.engine.variable.VariableMap;

import java.util.List;
import java.util.Optional;

/**
 * Read-write adapter for variable map.
 *
 * @param <T> type of value.
 */
public class ListReadWriteAdapterVariableMap<T> extends AbstractListReadWriteAdapter<T> {

  private final VariableMap variableMap;

  /**
   * Constructs the adapter.
   *
   * @param variableMap  variable map to access.
   * @param variableName variable to access.
   * @param memberClazz  class of variable value.
   */
  public ListReadWriteAdapterVariableMap(VariableMap variableMap, String variableName, Class<T> memberClazz) {
    super(variableName, memberClazz);
    this.variableMap = variableMap;
  }

  @Override
  public Optional<List<T>> getOptional() {
    return Optional.ofNullable(getOrNull(variableMap.get(variableName)));
  }

  @Override
  public void set(List<T> value, boolean isTransient) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient));
  }

  @Override
  public void setLocal(List<T> value, boolean isTransient) {
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
