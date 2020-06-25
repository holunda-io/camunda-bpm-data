package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * User task builder allowing for fluent variable setting.
 */
public class TaskServiceVariableWriter implements VariableWriter<TaskServiceVariableWriter> {

  private final TaskService taskService;
  private final String taskId;

  /**
   * Creates a builder working on a user task.
   */
  public TaskServiceVariableWriter(TaskService taskService, String taskId) {
    this.taskService = taskService;
    this.taskId = taskId;
  }

  @Override
  @NotNull
  public VariableMap variables() {
    return this.taskService.getVariablesTyped(this.taskId);
  }

  @Override
  @NotNull
  public VariableMap variablesLocal() {
    return this.taskService.getVariablesLocalTyped(this.taskId);
  }

  @Override
  @NotNull
  public <T> TaskServiceVariableWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  @NotNull
  public <T> TaskServiceVariableWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.taskService, this.taskId).set(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> TaskServiceVariableWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  @NotNull
  public <T> TaskServiceVariableWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.taskService, this.taskId).setLocal(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> TaskServiceVariableWriter remove(VariableFactory<T> factory) {
    factory.on(this.taskService, this.taskId).remove();
    return this;
  }

  @Override
  @NotNull
  public <T> TaskServiceVariableWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.taskService, this.taskId).removeLocal();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TaskServiceVariableWriter that = (TaskServiceVariableWriter) o;

    if (!Objects.equals(taskService, that.taskService)) return false;
    return Objects.equals(taskId, that.taskId);
  }

  @Override
  public int hashCode() {
    int result = taskService != null ? taskService.hashCode() : 0;
    result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
    return result;
  }
}
