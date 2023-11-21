package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.adapter.set.SetReadWriteAdapterCaseService;
import io.holunda.camunda.bpm.data.adapter.set.SetReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.set.SetReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.set.SetReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.set.SetReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SetVariableFactoryTest {

  private final SetVariableFactory<String> variableFactory = new SetVariableFactory<>("string", String.class);

  @Test
  public void shouldHaveNameAndVariableClass() {
    assertThat(variableFactory.getName()).isEqualTo("string");
    assertThat(variableFactory.getMemberClass()).isEqualTo(String.class);
  }

  @Test
  public void shouldHaveCorrectHashCodeAndEquals() {
    VariableFactory<Set<String>> foo = CamundaBpmData.setVariable("foo", String.class);

    assertThat(variableFactory).isEqualTo(variableFactory);
    assertThat(variableFactory.hashCode()).isEqualTo(variableFactory.hashCode());


    assertThat(variableFactory).isNotEqualTo(foo);
    assertThat(variableFactory.hashCode()).isNotEqualTo(foo.hashCode());
  }

  @Test
  public void shouldReturnAdapterForDelegateExecution() {
    DelegateExecution delegateExecution = mock(DelegateExecution.class);

    assertThat(variableFactory.on(delegateExecution)).isInstanceOf(SetReadWriteAdapterVariableScope.class);
    assertThat(variableFactory.from(delegateExecution)).isInstanceOf(SetReadWriteAdapterVariableScope.class);
  }

  @Test
  public void shouldReturnAdapterForVariableMap() {
    VariableMap variableMap = mock(VariableMap.class);

    assertThat(variableFactory.on(variableMap)).isInstanceOf(SetReadWriteAdapterVariableMap.class);
    assertThat(variableFactory.from(variableMap)).isInstanceOf(SetReadWriteAdapterVariableMap.class);
  }

  @Test
  public void shouldReturnAdapterForRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    String executionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(runtimeService, executionId)).isInstanceOf(SetReadWriteAdapterRuntimeService.class);
    assertThat(variableFactory.from(runtimeService, executionId)).isInstanceOf(SetReadWriteAdapterRuntimeService.class);
  }

  @Test
  public void shouldReturnAdapterForTaskService() {
    TaskService taskService = mock(TaskService.class);
    String taskId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(taskService, taskId)).isInstanceOf(SetReadWriteAdapterTaskService.class);
    assertThat(variableFactory.from(taskService, taskId)).isInstanceOf(SetReadWriteAdapterTaskService.class);
  }

  @Test
  public void shouldReturnAdapterForCaseService() {
    CaseService caseService = mock(CaseService.class);
    String caseExecutionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(caseService, caseExecutionId)).isInstanceOf(SetReadWriteAdapterCaseService.class);
    assertThat(variableFactory.from(caseService, caseExecutionId)).isInstanceOf(SetReadWriteAdapterCaseService.class);
  }

}
