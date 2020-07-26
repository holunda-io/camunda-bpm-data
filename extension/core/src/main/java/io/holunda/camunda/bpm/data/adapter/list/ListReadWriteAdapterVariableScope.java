package io.holunda.camunda.bpm.data.adapter.list;

import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.List;
import java.util.Optional;

/**
 * Read-write adapter for variable scope.
 *
 * @param <T> type of value.
 */
public class ListReadWriteAdapterVariableScope<T> extends AbstractListReadWriteAdapter<T> {

  private final VariableScope variableScope;

  /**
   * Constructs the adapter.
   *
   * @param variableScope variable scope to access.
   * @param variableName  variable to access.
   * @param memberClazz   class of member variable value.
   */
  public ListReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> memberClazz) {
    super(variableName, memberClazz);
    this.variableScope = variableScope;
  }

  @Override
  public Optional<List<T>> getOptional() {
    return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)));
  }

  @Override
  public void set(List<T> value, boolean isTransient) {
    variableScope.setVariable(variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<List<T>> getLocalOptional() {
    return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)));
  }

  @Override
  public void setLocal(List<T> value, boolean isTransient) {
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
