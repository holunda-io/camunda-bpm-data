package io.holunda.camunda.bpm.data.adapter.basic;

import org.camunda.bpm.engine.RuntimeService;

import java.util.Optional;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class ReadWriteAdapterRuntimeService<T> extends AbstractBasicReadWriteAdapter<T> {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Constructs the adapter.
   *
   * @param runtimeService runtime service to use.
   * @param executionId    id of the execution to read from and write to.
   * @param variableName   name of the variable.
   * @param clazz          class of the variable.
   */
  public ReadWriteAdapterRuntimeService(RuntimeService runtimeService, String executionId, String variableName, Class<T> clazz) {
    super(variableName, clazz);
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @Override
  public Optional<T> getOptional() {
    return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName)));
  }

  @Override
  public void set(T value, boolean isTransient) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<T> getLocalOptional() {
    return Optional.ofNullable(getOrNull(runtimeService.getVariableLocal(executionId, variableName)));
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
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
