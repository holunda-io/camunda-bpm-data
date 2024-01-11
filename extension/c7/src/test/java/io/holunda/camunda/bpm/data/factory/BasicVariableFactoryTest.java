package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterCaseService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterVariableScope;
import io.holunda.camunda.bpm.data.factory.builder.BasicCaseServiceAdapterBuilder;
import io.holunda.camunda.bpm.data.factory.builder.BasicRuntimeServiceAdapterBuilder;
import io.holunda.camunda.bpm.data.factory.builder.BasicTaskServiceAdapterBuilder;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BasicVariableFactoryTest {

  private final BasicVariableFactory<String> variableFactory = new BasicVariableFactory<>("string", String.class);

  @Test
  public void shouldHaveNameAndVariableClass() {
    assertThat(variableFactory.getName()).isEqualTo("string");
    assertThat(variableFactory.getVariableClass()).isEqualTo(String.class);
  }

  @Test
  public void shouldHaveCorrectHashCodeAndEquals() {
    VariableFactory<String> foo = CamundaBpmData.stringVariable("foo");

    assertThat(variableFactory).isEqualTo(variableFactory);
    assertThat(variableFactory.hashCode()).isEqualTo(variableFactory.hashCode());


    assertThat(variableFactory).isNotEqualTo(foo);
    assertThat(variableFactory.hashCode()).isNotEqualTo(foo.hashCode());
  }

  @Test
  public void shouldReturnAdapterForDelegateExecution() {
    DelegateExecution delegateExecution = mock(DelegateExecution.class);

    assertThat(variableFactory.on(delegateExecution)).isInstanceOf(ReadWriteAdapterVariableScope.class);
    assertThat(variableFactory.from(delegateExecution)).isInstanceOf(ReadWriteAdapterVariableScope.class);
  }

  @Test
  public void shouldReturnAdapterForVariableMap() {
    VariableMap variableMap = mock(VariableMap.class);

    assertThat(variableFactory.on(variableMap)).isInstanceOf(ReadWriteAdapterVariableMap.class);
    assertThat(variableFactory.from(variableMap)).isInstanceOf(ReadWriteAdapterVariableMap.class);
  }

  @Test
  public void shouldReturnAdapterForRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    String executionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(runtimeService, executionId)).isInstanceOf(ReadWriteAdapterRuntimeService.class);
    assertThat(variableFactory.from(runtimeService, executionId)).isInstanceOf(ReadWriteAdapterRuntimeService.class);
  }

  @Test
  public void shouldReturnAdapterForTaskService() {
    TaskService taskService = mock(TaskService.class);
    String taskId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(taskService, taskId)).isInstanceOf(ReadWriteAdapterTaskService.class);
    assertThat(variableFactory.from(taskService, taskId)).isInstanceOf(ReadWriteAdapterTaskService.class);
  }

  @Test
  public void shouldReturnAdapterForCaseService() {
    CaseService caseService = mock(CaseService.class);
    String caseExecutionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(caseService, caseExecutionId)).isInstanceOf(ReadWriteAdapterCaseService.class);
    assertThat(variableFactory.from(caseService, caseExecutionId)).isInstanceOf(ReadWriteAdapterCaseService.class);
  }

  @Test
  public void shouldReturnAdapterBuilderForRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    assertThat(C7Adapters.using(variableFactory, runtimeService)).isInstanceOf(BasicRuntimeServiceAdapterBuilder.class);
  }

  @Test
  public void shouldReturnAdapterBuilderForTaskService() {
    TaskService taskService = mock(TaskService.class);

    assertThat(C7Adapters.using(variableFactory, taskService)).isInstanceOf(BasicTaskServiceAdapterBuilder.class);
  }

  @Test
  public void shouldReturnAdapterBuilderForCaseService() {
    CaseService caseService = mock(CaseService.class);

    assertThat(C7Adapters.using(variableFactory, caseService)).isInstanceOf(BasicCaseServiceAdapterBuilder.class);
  }
}
