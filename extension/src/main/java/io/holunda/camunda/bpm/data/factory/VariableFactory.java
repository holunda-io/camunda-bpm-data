package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterVariableScope;
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
public class VariableFactory<T> {

  private String name;
  private Class<T> clazz;

  /**
   * Creates variable factory for a given type and name.
   *
   * @param name  name of the variable.
   * @param clazz class of the type.
   */
  public VariableFactory(String name, Class<T> clazz) {
    this.name = name;
    this.clazz = clazz;
  }

  /**
   * Creates a write adapter for variable scope.
   *
   * @param variableScope underlying scope to work on.
   *
   * @return write adapter.
   */
  public WriteAdapter<T> on(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableScope underlying scope to work on.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  /**
   * Creates a write adapter for variable map.
   *
   * @param variableMap underlying scope to work on.
   *
   * @return write adapter.
   */
  public WriteAdapter<T> on(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  /**
   * Creates a read adapter on variable scope.
   *
   * @param variableMap underlying map to work on.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  /**
   * Creates a write adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   *
   * @return write adapter
   */
  public WriteAdapter<T> on(RuntimeService runtimeService, String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, clazz);
  }

  /**
   * Creates a read adapter on execution.
   *
   * @param runtimeService underlying runtime service to work on.
   * @param executionId    id identifying execution.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(RuntimeService runtimeService, String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, clazz);
  }

  /**
   * Creates a write adapter on task.
   *
   * @param taskService underlying runtime service to work on.
   * @param taskId      id identifying task.
   *
   * @return write adapter
   */
  public WriteAdapter<T> on(TaskService taskService, String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, name, clazz);
  }

  /**
   * Creates a read adapter on task.
   *
   * @param taskService underlying runtime service to work on.
   * @param taskId      id identifying task.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(TaskService taskService, String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, name, clazz);
  }

  /**
   * Creates a reusable adapter builder using a runtime service.
   *
   * @param runtimeService runtime service to operate on.
   *
   * @return adapter builder.
   */
  public RuntimeServiceAdapterBuilder<T> using(RuntimeService runtimeService) {
    return new RuntimeServiceAdapterBuilder<>(this, runtimeService);
  }

  /**
   * Creates a reusable adapter builder using a task service.
   *
   * @param taskService task service to operate on.
   *
   * @return adapter builder.
   */
  public TaskServiceAdapterBuilder<T> using(TaskService taskService) {
    return new TaskServiceAdapterBuilder<>(this, taskService);
  }

  /**
   * Retrieves the variable name.
   *
   * @return name of the variable.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Retrieves the variable class.
   *
   * @return class of the variable.
   */
  public Class<T> getVariableClass() {
    return clazz;
  }
}
