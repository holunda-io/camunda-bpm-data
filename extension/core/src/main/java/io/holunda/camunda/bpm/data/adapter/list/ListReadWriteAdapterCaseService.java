package io.holunda.camunda.bpm.data.adapter.list;

import org.camunda.bpm.engine.CaseService;

import java.util.List;
import java.util.Optional;

/**
 * Read write adapter for case service access.
 *
 * @param <T> type of value.
 */
public class ListReadWriteAdapterCaseService<T> extends AbstractListReadWriteAdapter<T> {

  private final CaseService caseService;
  private final String caseExecutionId;

  /**
   * Constructs the adapter.
   *
   * @param caseService     case service to use.
   * @param caseExecutionId id of the execution to read from and write to.
   * @param variableName    name of the variable.
   * @param memberClazz     class of the variable.
   */
  public ListReadWriteAdapterCaseService(CaseService caseService, String caseExecutionId, String variableName, Class<T> memberClazz) {
    super(variableName, memberClazz);
    this.caseService = caseService;
    this.caseExecutionId = caseExecutionId;
  }

  @Override
  public Optional<List<T>> getOptional() {
    return Optional.ofNullable(getOrNull(caseService.getVariable(caseExecutionId, variableName)));
  }

  @Override
  public void set(List<T> value, boolean isTransient) {
    caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public Optional<List<T>> getLocalOptional() {
    return Optional.ofNullable(getOrNull(caseService.getVariableLocal(caseExecutionId, variableName)));
  }

  @Override
  public void setLocal(List<T> value, boolean isTransient) {
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
