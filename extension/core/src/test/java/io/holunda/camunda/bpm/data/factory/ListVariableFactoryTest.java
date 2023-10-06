package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterCaseService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ListVariableFactoryTest {

  private final ListVariableFactory<String> variableFactory = new ListVariableFactory<>("string", String.class);

  @Test
  public void shouldHaveNameAndVariableClass() {
    assertThat(variableFactory.getName()).isEqualTo("string");
    assertThat(variableFactory.getMemberClass()).isEqualTo(String.class);
  }

  @Test
  public void shouldHaveCorrectHashCodeAndEquals() {
    VariableFactory<List<String>> foo = CamundaBpmData.listVariable("foo", String.class);

    assertThat(variableFactory).isEqualTo(variableFactory);
    assertThat(variableFactory.hashCode()).isEqualTo(variableFactory.hashCode());


    assertThat(variableFactory).isNotEqualTo(foo);
    assertThat(variableFactory.hashCode()).isNotEqualTo(foo.hashCode());
  }

  @Test
  public void shouldReturnAdapterForDelegateExecution() {
    DelegateExecution delegateExecution = mock(DelegateExecution.class);

    assertThat(variableFactory.on(delegateExecution)).isInstanceOf(ListReadWriteAdapterVariableScope.class);
    assertThat(variableFactory.from(delegateExecution)).isInstanceOf(ListReadWriteAdapterVariableScope.class);
  }

  @Test
  public void shouldReturnAdapterForVariableMap() {
    VariableMap variableMap = mock(VariableMap.class);

    assertThat(variableFactory.on(variableMap)).isInstanceOf(ListReadWriteAdapterVariableMap.class);
    assertThat(variableFactory.from(variableMap)).isInstanceOf(ListReadWriteAdapterVariableMap.class);
  }

  @Test
  public void shouldReturnAdapterForRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    String executionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(runtimeService, executionId)).isInstanceOf(ListReadWriteAdapterRuntimeService.class);
    assertThat(variableFactory.from(runtimeService, executionId)).isInstanceOf(ListReadWriteAdapterRuntimeService.class);
  }

  @Test
  public void shouldReturnAdapterForTaskService() {
    TaskService taskService = mock(TaskService.class);
    String taskId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(taskService, taskId)).isInstanceOf(ListReadWriteAdapterTaskService.class);
    assertThat(variableFactory.from(taskService, taskId)).isInstanceOf(ListReadWriteAdapterTaskService.class);
  }

  @Test
  public void shouldReturnAdapterForCaseService() {
    CaseService caseService = mock(CaseService.class);
    String caseExecutionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(caseService, caseExecutionId)).isInstanceOf(ListReadWriteAdapterCaseService.class);
    assertThat(variableFactory.from(caseService, caseExecutionId)).isInstanceOf(ListReadWriteAdapterCaseService.class);
  }

}
