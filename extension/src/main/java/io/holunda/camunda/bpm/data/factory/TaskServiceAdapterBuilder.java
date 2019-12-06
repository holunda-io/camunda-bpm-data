package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.TaskService;

/**
 * Creates a builder to encapsulate the task service access.
 *
 * @param <T> type of builder.
 */
public class TaskServiceAdapterBuilder<T> {

  private final TaskService taskService;
  private final VariableFactory<T> variableFactory;

  /**
   * Constructs the builder.
   * @param variableFactory variable factory to use.
   * @param taskService task service to build adapter with.
   */
  public TaskServiceAdapterBuilder(VariableFactory<T> variableFactory, TaskService taskService) {
    this.taskService = taskService;
    this.variableFactory = variableFactory;
  }

  /**
   * Creates a write adapter on task.
   *
   * @param taskId id identifying task.
   *
   * @return write adapter
   */
  public WriteAdapter<T> on(String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, variableFactory.getName(), variableFactory.getVariableClass());
  }

  /**
   * Creates a read adapter on task.
   *
   * @param taskId id identifying task.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, variableFactory.getName(), variableFactory.getVariableClass());
  }

}
