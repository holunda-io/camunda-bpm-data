package io.holunda.camunda.bpm.data.adapter.set;

import org.camunda.bpm.engine.TaskService;

import java.util.Optional;
import java.util.Set;

/**
 * Read write adapter for task service access.
 *
 * @param <T> type of value.
 */
public class SetReadWriteAdapterTaskService<T> extends AbstractSetReadWriteAdapter<T> {

  private final TaskService taskService;
  private final String taskId;

  /**
   * Constructs the adapter.
   *
   * @param taskService  task service to use.
   * @param taskId       id of the task to read from and write to.
   * @param variableName name of the variable.
   * @param memberClazz  class of the variable.
   */
  public SetReadWriteAdapterTaskService(TaskService taskService, String taskId, String variableName, Class<T> memberClazz) {
    super(variableName, memberClazz);
    this.taskService = taskService;
    this.taskId = taskId;
  }

  @Override
  public Optional<Set<T>> getOptional() {
    return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)));
  }

  @Override
  public void set(Set<T> value, boolean isTransient) {
    taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public void setLocal(Set<T> value, boolean isTransient) {
    taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient));
  }

}
