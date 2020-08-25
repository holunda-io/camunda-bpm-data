package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.TaskService;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Allows reading multiple variable values from {@link TaskService#getVariable(String, String)}.
 */
public class TaskServiceVariableReader implements VariableReader {

  private final TaskService taskService;
  private final String taskId;

  /**
   * Constructs a reader operating on task service.
   *
   * @param taskService task service to operate on.
   * @param taskId      task id.
   */
  public TaskServiceVariableReader(TaskService taskService, String taskId) {
    this.taskService = taskService;
    this.taskId = taskId;
  }


  @NotNull
  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).getOptional();
  }

  @NotNull
  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).get();
  }

  @NotNull
  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).getLocal();
  }

  @NotNull
  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).getLocalOptional();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TaskServiceVariableReader that = (TaskServiceVariableReader) o;

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
