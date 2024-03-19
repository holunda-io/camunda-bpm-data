package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.adapter.listofmaps.ListOfMapsReadWriteAdapterCaseService;
import io.holunda.camunda.bpm.data.adapter.listofmaps.ListOfMapsReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.listofmaps.ListOfMapsReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.listofmaps.ListOfMapsReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.listofmaps.ListOfMapsReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ListOfMapsVariableFactoryTest {

  private final ListOfMapsVariableFactory<String, Object> variableFactory = new ListOfMapsVariableFactory<>("string", String.class, Object.class);

  @Test
  void shouldHaveNameAndVariableClass() {
    assertThat(variableFactory.getName()).isEqualTo("string");
    assertThat(variableFactory.getKeyClass()).isEqualTo(String.class);
    assertThat(variableFactory.getValueClass()).isEqualTo(Object.class);
  }

  @Test
  void shouldHaveCorrectHashCodeAndEquals() {
    VariableFactory<List<Map<String, Object>>> foo = CamundaBpmData.listOfMapsVariable("foo", String.class, Object.class);

    assertThat(variableFactory).isEqualTo(variableFactory);
    assertThat(variableFactory.hashCode()).isEqualTo(variableFactory.hashCode());


    assertThat(variableFactory).isNotEqualTo(foo);
    assertThat(variableFactory.hashCode()).isNotEqualTo(foo.hashCode());
  }

  @Test
  void shouldReturnAdapterForDelegateExecution() {
    DelegateExecution delegateExecution = mock(DelegateExecution.class);

    assertThat(variableFactory.on(delegateExecution)).isInstanceOf(ListOfMapsReadWriteAdapterVariableScope.class);
    assertThat(variableFactory.from(delegateExecution)).isInstanceOf(ListOfMapsReadWriteAdapterVariableScope.class);
  }

  @Test
  void shouldReturnAdapterForVariableMap() {
    VariableMap variableMap = mock(VariableMap.class);

    assertThat(variableFactory.on(variableMap)).isInstanceOf(ListOfMapsReadWriteAdapterVariableMap.class);
    assertThat(variableFactory.from(variableMap)).isInstanceOf(ListOfMapsReadWriteAdapterVariableMap.class);
  }

  @Test
  void shouldReturnAdapterForRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    String executionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(runtimeService, executionId)).isInstanceOf(ListOfMapsReadWriteAdapterRuntimeService.class);
    assertThat(variableFactory.from(runtimeService, executionId)).isInstanceOf(ListOfMapsReadWriteAdapterRuntimeService.class);
  }

  @Test
  void shouldReturnAdapterForTaskService() {
    TaskService taskService = mock(TaskService.class);
    String taskId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(taskService, taskId)).isInstanceOf(ListOfMapsReadWriteAdapterTaskService.class);
    assertThat(variableFactory.from(taskService, taskId)).isInstanceOf(ListOfMapsReadWriteAdapterTaskService.class);
  }

  @Test
  void shouldReturnAdapterForCaseService() {
    CaseService caseService = mock(CaseService.class);
    String caseExecutionId = UUID.randomUUID().toString();

    assertThat(variableFactory.on(caseService, caseExecutionId)).isInstanceOf(ListOfMapsReadWriteAdapterCaseService.class);
    assertThat(variableFactory.from(caseService, caseExecutionId)).isInstanceOf(ListOfMapsReadWriteAdapterCaseService.class);
  }
}
