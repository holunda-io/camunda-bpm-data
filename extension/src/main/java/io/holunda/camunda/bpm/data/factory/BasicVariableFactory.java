package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterVariableScope;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Variable factory of a base type(non parametrized).
 *
 * @param <T> type of the factory.
 */
public class BasicVariableFactory<T> implements VariableFactory<T> {

  private final String name;
  private final Class<T> clazz;

  /**
   * Creates variable factory for a given type and name.
   *
   * @param name  name of the variable.
   * @param clazz class of the type.
   */
  public BasicVariableFactory(String name, Class<T> clazz) {
    this.name = name;
    this.clazz = clazz;
  }

  @Override
  public WriteAdapter<T> on(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(RuntimeService runtimeService, String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(RuntimeService runtimeService, String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(TaskService taskService, String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, name, clazz);
  }

  @Override
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

  @Override
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
