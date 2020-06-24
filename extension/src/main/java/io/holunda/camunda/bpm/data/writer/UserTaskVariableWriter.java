package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * User task builder allowing for fluent variable setting.
 */
public class UserTaskVariableWriter implements LocalVariableWriter<UserTaskVariableWriter> {

  private final TaskService taskService;
  private final String taskId;

  /**
   * Creates a builder working on a user task.
   */
  public UserTaskVariableWriter(TaskService taskService, String taskId) {
    this.taskService = taskService;
    this.taskId = taskId;
  }

  @Override
  public VariableMap variables() {
    return this.taskService.getVariablesTyped(this.taskId);
  }

  @Override
  public VariableMap variablesLocal() {
    return this.taskService.getVariablesLocalTyped(this.taskId);
  }

  @Override
  public <T> UserTaskVariableWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  public <T> UserTaskVariableWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.taskService, this.taskId).set(value, isTransient);
    return this;
  }

  @Override
  public <T> UserTaskVariableWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  public <T> UserTaskVariableWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.taskService, this.taskId).setLocal(value, isTransient);
    return this;
  }

  @Override
  public <T> UserTaskVariableWriter remove(VariableFactory<T> factory) {
    factory.on(this.taskService, this.taskId).remove();
    return this;
  }

  @Override
  public <T> UserTaskVariableWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.taskService, this.taskId).removeLocal();
    return this;
  }

}
