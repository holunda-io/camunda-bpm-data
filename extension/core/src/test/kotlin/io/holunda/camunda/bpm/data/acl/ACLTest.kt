package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin
import io.holunda.camunda.bpm.data.acl.transform.VariableMapTransformer
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.*
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.ProcessEngineConfiguration
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension
import org.camunda.bpm.engine.test.mock.MockExpressionManager
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.community.mockito.CamundaMockito.registerInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension


@Deployment(resources = ["eventBasedSubprocess_no_transientMapping.bpmn", "eventBasedSubprocess_with_transientMapping.bpmn"])
class TransientVariableMappingListenerTest {

  @Test
  fun `NO ACL signal sub-process with variables sets variables on processInstance`() {

    // given
    val processInstance = camunda.runtimeService.startProcessInstanceByKey("eventBasedSubprocess_no_transientMapping")
    BpmnAwareTests.assertThat(processInstance).isWaitingAt("event_wait_forever")

    // when
    val variables = CamundaBpmData.builder().set(FOO, "bar").build()
    camunda.runtimeService
      .createSignalEvent("startSubProcess_" + processInstance.id)
      .setVariables(variables)
      .send()

    // then
    FOO.hasValue("bar").assertFor(camunda.taskService, task().id)
    FOO.hasValue("bar").assertFor(camunda.runtimeService, processInstance.id)
  }

  @Test
  fun `ACL signal sub-process with variables does not set variables on processInstance`() {

    // given
    registerInstance("mapper", ACL_LR.getExecutionListener())

    val processInstance = camunda.runtimeService.startProcessInstanceByKey(
      "eventBasedSubprocess_with_transientMapping"
    )
    BpmnAwareTests.assertThat(processInstance).isWaitingAt("event_wait_forever")
    // when
    // Here the magic happens: all variables are wrapped in one transient variable
    val variables = ACL_LR.wrap(CamundaBpmData.builder().set(FOO, "bar").build())
    camunda.runtimeService
      .createSignalEvent("startSubProcess_" + processInstance.id)
      .setVariables(variables)
      .send()

    // then
    // global scope
    FOO.notExists().assertFor(camunda.runtimeService, processInstance.id)
    // local scope
    FOO.hasValue("bar").assertFor(camunda.taskService, task().id)
  }

  @Test
  fun `ACL signal sub-process with variables passes guard sets does not set variables on process instance`() {

    // given
    registerInstance("mapper", ACL_GTLR.getExecutionListener())

    val processInstance = camunda.runtimeService.startProcessInstanceByKey(
      "eventBasedSubprocess_with_transientMapping"
    )
    BpmnAwareTests.assertThat(processInstance).isWaitingAt("event_wait_forever")
    // when
    // Here the magic happens: all variables are wrapped in one transient variable
    val variables = ACL_GTLR.checkAndWrap(
      CamundaBpmData.builder()
        .set(FOO, "bar")
        .set(BAZ, 42L)
        .build()
    )
    // only one transient variable inside.
    assertThat(variables).containsOnlyKeys("transient_acl")

    camunda.runtimeService
      .createSignalEvent("startSubProcess_" + processInstance.id)
      .setVariables(variables)
      .send()

    // then
    // global scope
    FOO.notExists().assertFor(camunda.runtimeService, processInstance.id)
    // local scope
    FOO.hasValue("bar").assertFor(camunda.taskService, task().id)
  }

  companion object {

    private val FOO = CamundaBpmDataKotlin.stringVariable("foo")
    private val BAZ = CamundaBpmDataKotlin.longVariable("baz")
    private val BAZ_INTERNAL = CamundaBpmDataKotlin.longVariable("baz__int")

    private val ACL_LR = CamundaBpmDataMapper.identityReplace("transient", true)

    /**
     * This demonstrates the power of the Anti-Corruption-Layer.
     *
     * The warp method will check the pre-conditions and create the transient representation.
     * The ACL is attached as an execution listener to the signal and verifies the pre-condition,
     * and then performs a transformation of variables.
     *
     * The conditions are:
     * - variable foo eís set
     * - variable baz is set and its value is greater than 40L
     *
     * The transformations are:
     * - foo     -> foo
     * - baz / 2 -> baz_internal
     */
    private val ACL_GTLR = CamundaBpmDataACL.guardTransformingLocalReplace(
      variableName = "transient_acl",
      variablesGuard = VariablesGuard(
        listOf(
          BAZ.matches { it > 40L },
          FOO.exists()
        )
      ),
      variableMapTransformer = object : VariableMapTransformer {
        override fun transform(variableMap: VariableMap): VariableMap {
          return CamundaBpmData
            .builder()
            .set(BAZ_INTERNAL, BAZ.from(variableMap).get() / 2L)
            .set(FOO, FOO.from(variableMap).get())
            .build()
        }
      }
    )


    @RegisterExtension
    val camunda: ProcessEngineExtension = ProcessEngineExtension.builder().useProcessEngine(
      object : StandaloneInMemProcessEngineConfiguration() {
        init {
          history = ProcessEngineConfiguration.HISTORY_AUDIT
          databaseSchemaUpdate = ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE
          jobExecutorActivate = false
          expressionManager = MockExpressionManager()
        }
      }.buildProcessEngine()
    ).build()

  }

}

fun <T> VariableGuardCondition<T>.assertFor(taskService: TaskService, taskId: String) =
  assertThat(this.evaluate(taskService, taskId)).isEmpty()

fun <T> VariableGuardCondition<T>.assertFor(runtimeService: RuntimeService, processInstanceId: String) =
  assertThat(this.evaluate(runtimeService, processInstanceId)).isEmpty()
