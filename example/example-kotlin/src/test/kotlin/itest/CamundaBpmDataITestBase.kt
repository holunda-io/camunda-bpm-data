package io.holunda.camunda.bpm.data.itest

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.base.ScenarioTestBase
import com.tngtech.jgiven.integration.spring.EnableJGiven
import com.tngtech.jgiven.integration.spring.JGivenStage
import com.tngtech.jgiven.integration.spring.SpringScenarioTest
import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.itest.helper.ValueStoringUsingRuntimeService
import io.holunda.camunda.bpm.data.itest.helper.ValueStoringUsingTaskService
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.task.Task
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.model.bpmn.Bpmn
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * Alias for the when
 */
fun <G, W, T> ScenarioTestBase<G, W, T>.whenever() = `when`()

/**
 * Base for ITests.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = [TestApplication::class])
@ActiveProfiles("itest")
abstract class CamundaBpmDataITestBase : SpringScenarioTest<ActionStage, ActionStage, AssertStage>() {

  companion object {
    val STRING_VAR = CamundaBpmData.stringVariable("String Variable")
    val DATE_VAR = CamundaBpmData.dateVariable("Date Variable")
    val SHORT_VAR = CamundaBpmData.shortVariable("Short Variable")
    val INT_VAR = CamundaBpmData.intVariable("Int Variable")
    val LONG_VAR = CamundaBpmData.longVariable("Long Variable")
    val DOUBLE_VAR = CamundaBpmData.doubleVariable("Double Variable")
    val BOOLEAN_VAR = CamundaBpmData.booleanVariable("Boolean Variable")
    val COMPLEX_VAR = CamundaBpmData.customVariable("Complex Variable", ComplexDataStructure::class.java)
    val LIST_STRING_VAR = CamundaBpmData.listVariable("List Of String Variable", String::class.java)
    val SET_STRING_VAR = CamundaBpmData.setVariable("Set Of String Variable", String::class.java)
    val MAP_STRING_DATE_VAR = CamundaBpmData.mapVariable("Map Of String to Date Variable", String::class.java, Date::class.java)
  }
}


/**
 * Base action stage.
 */
@JGivenStage
class ActionStage : Stage<ActionStage>() {

  @Autowired
  @ProvidedScenarioState
  lateinit var repositoryService: RepositoryService

  @Autowired
  @ProvidedScenarioState
  lateinit var runtimeService: RuntimeService

  @Autowired
  @ProvidedScenarioState
  lateinit var taskService: TaskService

  @ProvidedScenarioState
  lateinit var processDefinition: ProcessDefinition

  @ProvidedScenarioState
  lateinit var processInstance: ProcessInstance

  @ProvidedScenarioState
  lateinit var task: Task

  fun process_with_delegate_is_deployed(
    processDefinitionKey: String = "process_with_delegate",
    delegateExpression: String = "\${serviceTaskDelegate}"
  ): ActionStage {

    val instance = Bpmn
      .createExecutableProcess(processDefinitionKey)
      .startEvent("start")
      .serviceTask("service_task")
      .camundaDelegateExpression(delegateExpression)
      .endEvent("end")
      .done()

    val deployment = repositoryService
      .createDeployment()
      .addModelInstance("$processDefinitionKey.bpmn", instance)
      .name(processDefinitionKey)
      .deploy()

    processDefinition = repositoryService
      .createProcessDefinitionQuery()
      .deploymentId(deployment.id)
      .singleResult()

    return self()
  }

  fun process_with_user_task_is_deployed(
    processDefinitionKey: String = "process_with_user_task",
    taskDefinitionKey: String = "user_task"
  ): ActionStage {

    val instance = Bpmn
      .createExecutableProcess(processDefinitionKey)
      .startEvent("start")
      .userTask(taskDefinitionKey)
      .endEvent("end")
      .done()

    val deployment = repositoryService
      .createDeployment()
      .addModelInstance("$processDefinitionKey.bpmn", instance)
      .name(processDefinitionKey)
      .deploy()

    processDefinition = repositoryService
      .createProcessDefinitionQuery()
      .deploymentId(deployment.id)
      .singleResult()

    return self()
  }

  fun process_with_user_task_and_delegate_is_deployed(
    processDefinitionKey: String = "process_with_user_task",
    taskDefinitionKey: String = "user_task",
    delegateExpression: String = "\${serviceTaskDelegate}"
  ): ActionStage {

    val instance = Bpmn
      .createExecutableProcess(processDefinitionKey)
      .startEvent("start")
      .userTask(taskDefinitionKey)
      .serviceTask("service_task")
      .camundaDelegateExpression(delegateExpression)
      .endEvent("end")
      .done()

    val deployment = repositoryService
      .createDeployment()
      .addModelInstance("$processDefinitionKey.bpmn", instance)
      .name(processDefinitionKey)
      .deploy()

    processDefinition = repositoryService
      .createProcessDefinitionQuery()
      .deploymentId(deployment.id)
      .singleResult()

    return self()
  }

  fun process_with_modifying_delegate_is_deployed(
    processDefinitionKey: String = "process_with_delegate",
    modifyingDelegateExpression: String = "\${modifyingServiceTaskDelegate}",
    delegateExpression: String = "\${serviceTaskDelegate}"
  ): ActionStage {

    val instance = Bpmn
      .createExecutableProcess(processDefinitionKey)
      .startEvent("start")
      .serviceTask("modifying_service_task")
      .camundaDelegateExpression(modifyingDelegateExpression)
      .serviceTask("service_task")
      .camundaDelegateExpression(delegateExpression)
      .endEvent("end")
      .done()

    val deployment = repositoryService
      .createDeployment()
      .addModelInstance("$processDefinitionKey.bpmn", instance)
      .name(processDefinitionKey)
      .deploy()

    processDefinition = repositoryService
      .createProcessDefinitionQuery()
      .deploymentId(deployment.id)
      .singleResult()

    return self()
  }


  fun process_is_started_with_variables(
    processDefinitionKey: String = this.processDefinition.key,
    variables: VariableMap
  ): ActionStage {

    processInstance = runtimeService
      .startProcessInstanceByKey(processDefinitionKey, variables)

    return self()
  }

  fun variable_are_read_from_execution(valueStoringUsingAdapterService: ValueStoringUsingRuntimeService): ActionStage {
    valueStoringUsingAdapterService.readValues(runtimeService, processInstance.id)
    return self()
  }

  fun variable_are_read_from_task(valueStoringUsingAdapterService: ValueStoringUsingTaskService, taskDefinitionKey: String = "user_task"): ActionStage {
    task = taskService
      .createTaskQuery().processInstanceId(processInstance.id).taskDefinitionKey(taskDefinitionKey).singleResult()

    valueStoringUsingAdapterService.readValues(taskService, task.id)
    return self()
  }

  fun process_waits_in_task(taskDefinitionKey: String = "user_task") : ActionStage {
    task = taskService
      .createTaskQuery().processInstanceId(processInstance.id).taskDefinitionKey(taskDefinitionKey).singleResult()
    return self()
  }

  fun task_is_completed() {
    taskService.complete(task.id)
  }

  fun variable_are_modified_in_task(taskWritingAdapter: (taskService: TaskService, taskId: String) -> Unit): ActionStage {
    taskWritingAdapter.invoke(this.taskService, this.task.id)
    return self()
  }

  fun variable_are_modified_in_wait_state(runtimeWritingAdapter: (runtimeService: RuntimeService, executionId: String) -> Unit): ActionStage {
    runtimeWritingAdapter.invoke(this.runtimeService, this.processInstance.id)
    return self()
  }

}

/**
 * Base assert stage.
 */
@JGivenStage
class AssertStage : Stage<AssertStage>() {

  fun variables_had_value(readValues: Map<String, Any>, vararg variableWithValue: Pair<VariableFactory<*>, Any>) {
    variableWithValue.forEach {
      assertThat(readValues).containsEntry(it.first.name, it.second)
    }
  }

  fun variables_had_not_value(readValues: Map<String, Any>, vararg variableWithValue: VariableFactory<*>) {
    val emptyOptional = Optional.empty<Any>()

    variableWithValue.forEach {
      assertThat(readValues).containsEntry(it.name, emptyOptional)
    }
  }
}


@EnableJGiven
@SpringBootApplication
class TestApplication


data class ComplexDataStructure(
  val stringValue: String,
  val intValue: Int,
  val dateValue: Date
) {
  @JsonIgnore
  private val valueToIgnore: String = "some hidden value"
}
