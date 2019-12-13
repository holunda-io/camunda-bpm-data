package io.holunda.camunda.bpm.data.adapter.list;

import org.camunda.bpm.engine.RuntimeService;

import java.util.List;
import java.util.Optional;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class ListReadWriteAdapterRuntimeService<T> extends AbstractListReadWriteAdapter<T> {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Constructs the adapter.
   *
   * @param runtimeService runtime service to use.
   * @param executionId    id of the execution to read from and write to.
   * @param variableName   name of the variable.
   * @param memberClazz    class of the variable.
   */
  public ListReadWriteAdapterRuntimeService(RuntimeService runtimeService, String executionId, String variableName, Class<T> memberClazz) {
    super(variableName, memberClazz);
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @Override
  public Optional<List<T>> getOptional() {
    return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName)));
  }

  @Override
  public void set(List<T> value, boolean isTransient) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<List<T>> getLocalOptional() {
    return Optional.ofNullable(getOrNull(runtimeService.getVariableLocal(executionId, variableName)));
  }

  @Override
  public void setLocal(List<T> value, boolean isTransient) {
    runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient));
  }
  @Override
  public void remove() {
    runtimeService.removeVariable(executionId, variableName);
  }

  @Override
  public void removeLocal() {
    runtimeService.removeVariableLocal(executionId, variableName);
  }
}
