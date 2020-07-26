package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Process execution builder allowing for fluent variable setting.
 */
public class CaseServiceVariableWriter implements VariableWriter<CaseServiceVariableWriter> {

  private final CaseService caseService;
  private final String caseExecutionId;

  /**
   * Creates a writer working on process execution.
   */
  public CaseServiceVariableWriter(CaseService caseService, String caseExecutionId) {
    this.caseService = caseService;
    this.caseExecutionId = caseExecutionId;
  }

  @Override
  @NotNull
  public VariableMap variables() {
    return this.caseService.getVariablesTyped(this.caseExecutionId);
  }

  @Override
  @NotNull
  public VariableMap variablesLocal() {
    return this.caseService.getVariablesLocalTyped(this.caseExecutionId);
  }

  @Override
  @NotNull
  public <T> CaseServiceVariableWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  @NotNull
  public <T> CaseServiceVariableWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.caseService, this.caseExecutionId).set(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> CaseServiceVariableWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  @NotNull
  public <T> CaseServiceVariableWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.caseService, this.caseExecutionId).setLocal(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> CaseServiceVariableWriter remove(VariableFactory<T> factory) {
    factory.on(this.caseService, this.caseExecutionId).remove();
    return this;
  }

  @Override
  @NotNull
  public <T> CaseServiceVariableWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.caseService, this.caseExecutionId).removeLocal();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CaseServiceVariableWriter that = (CaseServiceVariableWriter) o;

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
