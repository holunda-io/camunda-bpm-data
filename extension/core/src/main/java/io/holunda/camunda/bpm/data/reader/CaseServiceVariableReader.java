package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.CaseService;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Allows reading multiple variable values from {@link CaseService#getVariable(String, String)}.
 */
public class CaseServiceVariableReader implements VariableReader {

  private final CaseService caseService;
  private final String caseExecutionId;

  /**
   * Constructs a reader operating on execution.
   *
   * @param caseService     runtime service to use.
   * @param caseExecutionId execution id.
   */
  public CaseServiceVariableReader(CaseService caseService, String caseExecutionId) {
    this.caseService = caseService;
    this.caseExecutionId = caseExecutionId;
  }

  @NotNull
  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(caseService, caseExecutionId).getOptional();
  }

  @NotNull
  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(caseService, caseExecutionId).get();
  }

  @NotNull
  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(caseService, caseExecutionId).getLocal();
  }

  @NotNull
  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(caseService, caseExecutionId).getLocalOptional();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CaseServiceVariableReader that = (CaseServiceVariableReader) o;

    if (!Objects.equals(caseService, that.caseService)) return false;
    return Objects.equals(caseExecutionId, that.caseExecutionId);
  }

  @Override
  public int hashCode() {
    int result = caseService != null ? caseService.hashCode() : 0;
    result = 31 * result + (caseExecutionId != null ? caseExecutionId.hashCode() : 0);
    return result;
  }
}
