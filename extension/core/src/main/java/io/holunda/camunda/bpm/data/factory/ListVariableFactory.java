package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterCaseService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Variable factory of a base parametrized list type.
 *
 * @param <T> member type of the factory.
 */
public class ListVariableFactory<T> implements VariableFactory<List<T>> {

  @NotNull
  private final String name;

  @NotNull
  private final Class<T> memberClazz;

  public ListVariableFactory(@NotNull String name, @NotNull Class<T> memberClazz) {
    this.name = name;
    this.memberClazz = memberClazz;
  }

  @Override
  public WriteAdapter<List<T>> on(VariableScope variableScope) {
    return new ListReadWriteAdapterVariableScope<>(variableScope, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(VariableScope variableScope) {
    return new ListReadWriteAdapterVariableScope<>(variableScope, name, memberClazz);
  }

  @Override
  public WriteAdapter<List<T>> on(VariableMap variableMap) {
    return new ListReadWriteAdapterVariableMap<>(variableMap, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(VariableMap variableMap) {
    return new ListReadWriteAdapterVariableMap<>(variableMap, name, memberClazz);
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
  public WriteAdapter<List<T>> on(CaseService caseService, String caseExecutionId) {
    return new ListReadWriteAdapterCaseService<>(caseService, caseExecutionId, name, memberClazz);
  }

  @Override
  public ReadAdapter<List<T>> from(CaseService caseService, String caseExecutionId) {
    return new ListReadWriteAdapterCaseService<>(caseService, caseExecutionId, name, memberClazz);
  }

  @Override
  @NotNull
  public String getName() {
    return name;
  }

  @NotNull
  public Class<T> getMemberClass() {
    return memberClazz;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ListVariableFactory<?> that = (ListVariableFactory<?>) o;
    return name.equals(that.name) &&
      memberClazz.equals(that.memberClazz);
  }

  @Override public int hashCode() {
    return Objects.hash(name, memberClazz);
  }

  @Override
  public String toString() {
    return "ListVariableFactory{" +
        "name='" + name + '\'' +
        ", memberClazz=" + memberClazz +
        '}';
  }
}
