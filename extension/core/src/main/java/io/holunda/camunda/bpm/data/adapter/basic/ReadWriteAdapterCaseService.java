package io.holunda.camunda.bpm.data.adapter.basic;

import org.camunda.bpm.engine.CaseService;

import java.util.Optional;

/**
 * Read write adapter for case service access.
 *
 * @param <T> type of value.
 */
public class ReadWriteAdapterCaseService<T> extends AbstractBasicReadWriteAdapter<T> {

  private final CaseService caseService;
  private final String caseExecutionId;

  /**
   * Constructs the adapter.
   *
   * @param caseService     case service to use.
   * @param caseExecutionId id of the execution to read from and write to.
   * @param variableName    name of the variable.
   * @param clazz           class of the variable.
   */
  public ReadWriteAdapterCaseService(CaseService caseService, String caseExecutionId, String variableName, Class<T> clazz) {
    super(variableName, clazz);
    this.caseService = caseService;
    this.caseExecutionId = caseExecutionId;
  }

  @Override
  public Optional<T> getOptional() {
    return Optional.ofNullable(getOrNull(caseService.getVariable(caseExecutionId, variableName)));
  }

  @Override
  public void set(T value, boolean isTransient) {
    caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<T> getLocalOptional() {
    return Optional.ofNullable(getOrNull(caseService.getVariableLocal(caseExecutionId, variableName)));
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
    caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public void remove() {
    caseService.removeVariable(caseExecutionId, variableName);
  }

  @Override
  public void removeLocal() {
    caseService.removeVariableLocal(caseExecutionId, variableName);
  }
}
