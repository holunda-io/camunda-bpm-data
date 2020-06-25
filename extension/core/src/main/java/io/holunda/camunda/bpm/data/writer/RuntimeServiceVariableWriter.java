package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Process execution builder allowing for fluent variable setting.
 */
public class RuntimeServiceVariableWriter implements VariableWriter<RuntimeServiceVariableWriter> {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Creates a writer working on process execution.
   */
  public RuntimeServiceVariableWriter(RuntimeService runtimeService, String executionId) {
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @Override
  @NotNull
  public VariableMap variables() {
    return this.runtimeService.getVariablesTyped(this.executionId);
  }

  @Override
  @NotNull
  public VariableMap variablesLocal() {
    return this.runtimeService.getVariablesLocalTyped(this.executionId);
  }

  @Override
  @NotNull
  public <T> RuntimeServiceVariableWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  @NotNull
  public <T> RuntimeServiceVariableWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.runtimeService, this.executionId).set(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> RuntimeServiceVariableWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  @NotNull
  public <T> RuntimeServiceVariableWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.runtimeService, this.executionId).setLocal(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> RuntimeServiceVariableWriter remove(VariableFactory<T> factory) {
    factory.on(this.runtimeService, this.executionId).remove();
    return this;
  }

  @Override
  @NotNull
  public <T> RuntimeServiceVariableWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.runtimeService, this.executionId).removeLocal();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RuntimeServiceVariableWriter that = (RuntimeServiceVariableWriter) o;

    if (!Objects.equals(runtimeService, that.runtimeService)) return false;
    return Objects.equals(executionId, that.executionId);
  }

  @Override
  public int hashCode() {
    int result = runtimeService != null ? runtimeService.hashCode() : 0;
    result = 31 * result + (executionId != null ? executionId.hashCode() : 0);
    return result;
  }
}
