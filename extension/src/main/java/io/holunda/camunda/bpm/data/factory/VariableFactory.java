package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Typed variable factory.
 *
 * @param <T> type of the factory.
 */
public interface VariableFactory<T> {
  /**
   * Creates a write adapter for variable scope.
   *
   * @param variableScope underlying scope to work on.
   *
   * @return write adapter.
   */
  WriteAdapter<T> on(VariableScope variableScope);

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableScope underlying scope to work on.
   *
   * @return read adapter.
   */
  ReadAdapter<T> from(VariableScope variableScope);

  /**
   * Creates a write adapter for variable map.
   *
   * @param variableMap underlying scope to work on.
   *
   * @return write adapter.
   */
  WriteAdapter<T> on(VariableMap variableMap);

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableMap underlying map to work on.
   *
   * @return read adapter.
   */
  ReadAdapter<T> from(VariableMap variableMap);

  /**
   * Creates a write adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   *
   * @return write adapter
   */
  WriteAdapter<T> on(RuntimeService runtimeService, String executionId);

  /**
   * Creates a read adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   *
   * @return read adapter.
   */
  ReadAdapter<T> from(RuntimeService runtimeService, String executionId);

  /**
   * Creates a write adapter on task.
   *
   * @param taskService underlying runtime service to work on.
   * @param taskId      id identifying task.
   *
   * @return write adapter
   */
  WriteAdapter<T> on(TaskService taskService, String taskId);

  /**
   * Creates a read adapter on task.
   *
   * @param taskService underlying runtime service to work on.
   * @param taskId      id identifying task.
   *
   * @return read adapter.
   */
  ReadAdapter<T> from(TaskService taskService, String taskId);

  /**
   * Retrieves the variable name.
   *
   * @return name of the variable.
   */
  String getName();
}
