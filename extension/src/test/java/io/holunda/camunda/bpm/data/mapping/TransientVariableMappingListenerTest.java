package io.holunda.camunda.bpm.data.mapping;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.engine.test.mock.MockExpressionManager;
import org.camunda.bpm.extension.mockito.CamundaMockito;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

@Deployment(resources = {"eventBasedSubprocess_no_transientMapping.bpmn", "eventBasedSubprocess_with_transientMapping.bpmn"})
public class TransientVariableMappingListenerTest {

  @Rule
  public final ProcessEngineRule camunda = camunda();

  private static final VariableFactory<String> FOO = stringVariable("foo");

  @Test
  public void signal_subprocess_with_variables_does_fails_by_setting_variables_on_processInstance() {
    ProcessInstance processInstance = camunda.getRuntimeService()
      .startProcessInstanceByKey("eventBasedSubprocess_no_transientMapping");

    BpmnAwareTests.assertThat(processInstance).isWaitingAt("event_wait_forever");

    camunda.getRuntimeService().createSignalEvent("startSubProcess_" + processInstance.getId())
      .setVariables(CamundaBpmData.builder()
        .set(FOO, "bar")
        .build())
      .send();

    assertThat(FOO.from(camunda.getTaskService(), BpmnAwareTests.task().getId()).get()).isEqualTo("bar");

    assertThatThrownBy(() ->
      assertThatVariableIsNotSetOnProcessInstance(processInstance, FOO)
    ).isInstanceOf(ComparisonFailure.class);

  }


  @Test
  public void signal_subprocess_with_variables_sets_does_not_set_variables_on_processInstance() {
    TransientVariableMappingListener mapping = CamundaMockito.registerInstance("mapper", new TransientVariableMappingListener("transient", true));

    ProcessInstance processInstance = camunda.getRuntimeService()
      .startProcessInstanceByKey("eventBasedSubprocess_with_transientMapping");

    BpmnAwareTests.assertThat(processInstance).isWaitingAt("event_wait_forever");

    camunda.getRuntimeService().createSignalEvent("startSubProcess_" + processInstance.getId())
      // Here the magic happens: all variables are wrapped in one transient variable
      .setVariables(mapping.wrap(CamundaBpmData.builder()
        .set(FOO, "bar")
        .build()))
      .send();

    assertThat(FOO.from(camunda.getTaskService(), BpmnAwareTests.task().getId()).get()).isEqualTo("bar");
    assertThatVariableIsNotSetOnProcessInstance(processInstance, FOO);

  }

  private void assertThatVariableIsNotSetOnProcessInstance(ProcessInstance processInstance, VariableFactory<?> variable) {
    assertThat(camunda.getRuntimeService()
      .getVariable(processInstance.getId(), variable.getName()))
      .isNull();
  }

  private static ProcessEngineRule camunda() {
    return new ProcessEngineRule(
      new StandaloneInMemProcessEngineConfiguration() {
        {
          history = HISTORY_FULL;
          databaseSchemaUpdate = DB_SCHEMA_UPDATE_TRUE;
          jobExecutorActivate = false;
          expressionManager = new MockExpressionManager();
        }
      }.buildProcessEngine());

  }
}
