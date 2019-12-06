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
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.camunda.bpm.engine.runtime.ProcessInstance
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

  @ProvidedScenarioState
  lateinit var processDefinition: ProcessDefinition

  @ProvidedScenarioState
  lateinit var processInstance: ProcessInstance


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
    processDefinitionKey: String = "process_with_user_task"
  ): ActionStage {

  val instance = Bpmn
    .createExecutableProcess(processDefinitionKey)
    .startEvent("start")
    .userTask("user_task")
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
