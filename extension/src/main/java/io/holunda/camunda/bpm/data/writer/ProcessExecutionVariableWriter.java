package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Process execution builder allowing for fluent variable setting.
 */
public class ProcessExecutionVariableWriter implements LocalVariableWriter<ProcessExecutionVariableWriter> {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Creates a writer working on process execution.
   */
  public ProcessExecutionVariableWriter(RuntimeService runtimeService, String executionId) {
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @Override
  public VariableMap variables() {
    return this.runtimeService.getVariablesTyped(this.executionId);
  }

  @Override
  public VariableMap variablesLocal() {
    return this.runtimeService.getVariablesLocalTyped(this.executionId);
  }

  @Override
  public <T> ProcessExecutionVariableWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  public <T> ProcessExecutionVariableWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.runtimeService, this.executionId).set(value, isTransient);
    return this;
  }

  @Override
  public <T> ProcessExecutionVariableWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  public <T> ProcessExecutionVariableWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.runtimeService, this.executionId).setLocal(value, isTransient);
    return this;
  }

  @Override
  public <T> ProcessExecutionVariableWriter remove(VariableFactory<T> factory) {
    factory.on(this.runtimeService, this.executionId).remove();
    return this;
  }

  @Override
  public <T> ProcessExecutionVariableWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.runtimeService, this.executionId).removeLocal();
    return this;
  }
}
