package io.holunda.camunda.bpm.data.itest

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.BeforeScenario
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.integration.spring.JGivenStage
import org.assertj.core.api.Assertions
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.task.Task
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.BpmnModelInstance
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.beans.factory.annotation.Autowired

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

  @Autowired
  lateinit var delegateConfiguration: CamundaBpmDataITestBase.DelegateConfiguration

  @BeforeScenario
  fun cleanUp() {
    delegateConfiguration.vars.clear()
    delegateConfiguration.optionalVars.clear()
    delegateConfiguration.variableMap = Variables.createVariables()
  }

  fun process_with_delegate_is_deployed(
    processDefinitionKey: String = "process_with_delegate",
    delegateExpression: String = "\${serviceTaskDelegate}"
  ): ActionStage {

    val instance = Bpmn.createExecutableProcess(processDefinitionKey)
      .camundaHistoryTimeToLive(1)
      .startEvent("start")
      .serviceTask("service_task")
      .camundaDelegateExpression(delegateExpression)
      .endEvent("end")
      .done()

    deploy(processDefinitionKey, instance)

    return self()
  }

  fun process_with_user_task_is_deployed(
    processDefinitionKey: String = "process_with_user_task",
    taskDefinitionKey: String = "user_task"
  ): ActionStage {

    val instance = Bpmn.createExecutableProcess(processDefinitionKey)
      .camundaHistoryTimeToLive(1)
      .startEvent("start")
      .userTask(taskDefinitionKey)
      .endEvent("end")
      .done()

    deploy(processDefinitionKey, instance)

    return self()
  }

  fun process_with_user_task_and_delegate_is_deployed(
    processDefinitionKey: String = "process_with_user_task",
    taskDefinitionKey: String = "user_task",
    delegateExpression: String = "\${serviceTaskDelegate}"
  ): ActionStage {

    val instance = Bpmn.createExecutableProcess(processDefinitionKey)
      .camundaHistoryTimeToLive(1)
      .startEvent("start")
      .userTask(taskDefinitionKey)
      .serviceTask("service_task")
      .camundaDelegateExpression(delegateExpression)
      .endEvent("end")
      .done()
    deploy(processDefinitionKey, instance)

    return self()
  }

  fun process_with_user_task_and_listener_is_deployed(
    processDefinitionKey: String = "process_with_user_task_and_listener",
    taskDefinitionKey: String = "user_task",
    delegateExpression: String = "\${listenerDelegate}"
  ): ActionStage {

    val instance = Bpmn.createExecutableProcess(processDefinitionKey)
      .camundaHistoryTimeToLive(1)
      .startEvent("start")
      .userTask(taskDefinitionKey)
      .camundaTaskListenerDelegateExpression("complete", delegateExpression)
      .endEvent("end")
      .done()
    deploy(processDefinitionKey, instance)

    return self()
  }

  fun process_with_modifying_delegate_is_deployed(
    processDefinitionKey: String = "process_with_delegate",
    modifyingDelegateExpression: String = "\${modifyingServiceTaskDelegate}",
    delegateExpression: String = "\${serviceTaskDelegate}"
  ): ActionStage {

    val instance = Bpmn.createExecutableProcess(processDefinitionKey)
      .camundaHistoryTimeToLive(1)
      .startEvent("start")
      .serviceTask("modifying_service_task")
      .camundaDelegateExpression(modifyingDelegateExpression)
      .serviceTask("service_task")
      .camundaDelegateExpression(delegateExpression)
      .endEvent("end")
      .done()

    deploy(processDefinitionKey, instance)
    return self()
  }

  /**
   * Starts process with variables.
   */
  fun process_is_started_with_variables(
    processDefinitionKey: String = this.processDefinition.key,
    variables: VariableMap,
    expectedException: Class<out java.lang.Exception>? = null
  ): ActionStage {

    if (expectedException == null) {
      processInstance = runtimeService
        .startProcessInstanceByKey(processDefinitionKey, variables)
    } else {
      assertThrows(expectedException) {
        runtimeService
          .startProcessInstanceByKey(processDefinitionKey, variables)
      }
    }

    return self()
  }

  /**
   * Reads task.
   */
  fun process_waits_in_task(taskDefinitionKey: String = "user_task"): ActionStage {
    val query = taskService.createTaskQuery().processInstanceId(processInstance.id).taskDefinitionKey(taskDefinitionKey)
    Assertions.assertThat(query.count()).isEqualTo(1L)
    task = query.singleResult()
    return self()
  }

  /**
   * Completes the task.
   */
  fun task_is_completed(): ActionStage {
    taskService.complete(task.id)
    return self()
  }

  /**
   * Calls task callback with task service and task.
   */
  fun task_is_accessed_in_user_task(taskServiceTaskCallback: (taskService: TaskService, taskId: String) -> Unit): ActionStage {
    taskServiceTaskCallback.invoke(this.taskService, this.task.id)
    return self()
  }

  /**
   * Calls execution callback with runtime service and execution.
   */
  fun execution_is_accessed_in_wait_state(runtimeServiceExecutionCallback: (runtimeService: RuntimeService, executionId: String) -> Unit): ActionStage {
    runtimeServiceExecutionCallback.invoke(this.runtimeService, this.processInstance.id)
    return self()
  }

  /**
   * Deploys process model instance under specified process definition key.
   */
  private fun deploy(processDefinitionKey: String, modelInstance: BpmnModelInstance) {
    val deployment = repositoryService
      .createDeployment()
      .addModelInstance("$processDefinitionKey.bpmn", modelInstance)
      .name(processDefinitionKey)
      .deploy()

    processDefinition = repositoryService
      .createProcessDefinitionQuery()
      .deploymentId(deployment.id)
      .singleResult()
  }
}
