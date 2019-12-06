package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;

/**
 * Read write adapter for task service access.
 *
 * @param <T> type of value.
 */
public class ReadWriteAdapterTaskService<T> implements ReadAdapter<T>, WriteAdapter<T> {

  private final TaskService taskService;
  private final String taskId;
  private final String variableName;
  private final Class<T> clazz;

  /**
   * Constructs the adapter.
   *
   * @param taskService  task service to use.
   * @param taskId       id of the task to read from and write to.
   * @param variableName name of the variable.
   * @param clazz        class of the variable.
   */
  public ReadWriteAdapterTaskService(TaskService taskService, String taskId, String variableName, Class<T> clazz) {
    this.taskService = taskService;
    this.taskId = taskId;
    this.variableName = variableName;
    this.clazz = clazz;
  }

  @Override
  public T get() {
    T value = getOrNull();
    if (value == null) {
      throw new IllegalStateException("Couldn't find required variable " + variableName);
    }
    return value;
  }

  @Override
  public Optional<T> getOptional() {
    return Optional.ofNullable(getOrNull());
  }

  @Override
  public void set(T value) {
    set(value, false);
  }

  @Override
  public void set(T value, boolean isTransient) {
    final TypedValue typedValue = ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
    taskService.setVariable(taskId, variableName, typedValue);
  }

  @Override
  public void setLocal(T value) {
    setLocal(value, false);
  }

  @Override
  public void setLocal(T value, boolean isTransient) {
    final TypedValue typedValue = ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
    taskService.setVariableLocal(taskId, variableName, typedValue);
  }

  @SuppressWarnings("unchecked")
  private T getOrNull() {
    final Object value = taskService.getVariable(taskId, variableName);

    if (value == null) {
      return null;
    }

    if (clazz.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read value of " + clazz + " from " + value);
  }

}
