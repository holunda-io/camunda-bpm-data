package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.List;

/**
 * Variable factory of a base parametrized list type.
 *
 * @param <T> member type of the factory.
 */
public class ListVariableFactory<T> implements VariableFactory<List<T>> {

  private final String name;
  private final Class<T> memberClazz;

  public ListVariableFactory(String name, Class<T> memberClazz) {
    this.name = name;
    this.memberClazz = memberClazz;
  }

  @Override
  public WriteAdapter<List<T>> on(VariableScope variableScope) {
    return new ListReadWriteAdapterVariableScope<T>(variableScope, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(VariableScope variableScope) {
    return new ListReadWriteAdapterVariableScope<T>(variableScope, name, memberClazz);
  }

  @Override
  public WriteAdapter<List<T>> on(VariableMap variableMap) {
    return new ListReadWriteAdapterVariableMap<T>(variableMap, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(VariableMap variableMap) {
    return new ListReadWriteAdapterVariableMap<T>(variableMap, name, memberClazz);
  }

  @Override
  public WriteAdapter<List<T>> on(RuntimeService runtimeService, String executionId) {
    return new ListReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(RuntimeService runtimeService, String executionId) {
    return new ListReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, memberClazz);
  }

  @Override
  public WriteAdapter<List<T>> on(TaskService taskService, String taskId) {
    return new ListReadWriteAdapterTaskService<>(taskService, taskId, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(TaskService taskService, String taskId) {
    return new ListReadWriteAdapterTaskService<>(taskService, taskId, name, memberClazz);
  }

  @Override
  public String getName() {
    return name;
  }

  public Class<T> getMemberClass() {
    return memberClazz;
  }
}
