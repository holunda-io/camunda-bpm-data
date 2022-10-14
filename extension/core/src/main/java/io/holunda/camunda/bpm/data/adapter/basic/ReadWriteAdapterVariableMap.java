package io.holunda.camunda.bpm.data.adapter.basic;

import java.util.Optional;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Read-write adapter for variable map.
 *
 * @param <T> type of value.
 */
public class ReadWriteAdapterVariableMap<T> extends AbstractBasicReadWriteAdapter<T> {

  private final VariableMap variableMap;

  /**
   * Constructs the adapter.
   *
   * @param variableMap variable map to access.
   * @param variableName variable to access.
   * @param clazz class of variable value.
   */
  public ReadWriteAdapterVariableMap(VariableMap variableMap, String variableName, Class<T> clazz) {
    super(variableName, clazz);
    this.variableMap = variableMap;
  }

  @Override
  public Optional<T> getOptional() {
    return Optional.ofNullable(getOrNull(variableMap.get(variableName)));
  }

  @Override
  public void set(T value, boolean isTransient) {
    variableMap.putValueTyped(variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<T> getLocalOptional() {
    throw new UnsupportedOperationException("Can't get a local variable on a variable map");
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
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
