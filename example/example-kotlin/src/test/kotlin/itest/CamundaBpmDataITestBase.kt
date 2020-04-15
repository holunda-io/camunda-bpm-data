package io.holunda.camunda.bpm.data.itest

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.BeforeScenario
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.base.ScenarioTestBase
import com.tngtech.jgiven.integration.spring.EnableJGiven
import com.tngtech.jgiven.integration.spring.JGivenStage
import com.tngtech.jgiven.integration.spring.SpringScenarioTest
import io.holunda.camunda.bpm.data.CamundaBpmData.*
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.BOOLEAN
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.BOOLEAN_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DATE_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DOUBLE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DOUBLE_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.INT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.INT_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_STRING_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LONG_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_DATE_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING_LOCAL
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.delegate.TaskListener
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.task.Task
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.BpmnModelInstance
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Alias for the when
 */
fun <G, W, T> ScenarioTestBase<G, W, T>.whenever(): W = `when`()

/**
 * Base for ITests.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = [CamundaBpmDataITestBase.TestApplication::class])
@ActiveProfiles("itest")
abstract class CamundaBpmDataITestBase : SpringScenarioTest<ActionStage, ActionStage, AssertStage>() {

  companion object {

    val STRING_VAR: VariableFactory<String> = stringVariable("String Variable")
    val DATE_VAR: VariableFactory<Date> = dateVariable("Date Variable")
    val SHORT_VAR: VariableFactory<Short> = shortVariable("Short Variable")
    val INT_VAR: VariableFactory<Int> = intVariable("Int Variable")
    val LONG_VAR: VariableFactory<Long> = longVariable("Long Variable")
    val DOUBLE_VAR: VariableFactory<Double> = doubleVariable("Double Variable")
    val BOOLEAN_VAR: VariableFactory<Boolean> = booleanVariable("Boolean Variable")
    val COMPLEX_VAR: VariableFactory<ComplexDataStructure> = customVariable("Complex Variable", ComplexDataStructure::class.java)
    val LIST_STRING_VAR: VariableFactory<List<String>> = listVariable("List Of String Variable", String::class.java)
    val SET_STRING_VAR: VariableFactory<Set<String>> = setVariable("Set Of String Variable", String::class.java)
    val MAP_STRING_DATE_VAR: VariableFactory<Map<String, Date>> = mapVariable("Map Of String to Date Variable", String::class.java, Date::class.java)

    object Values {
      private val now = Date.from(Instant.now())
      private val yesterday = Date.from(Instant.now().minus(1, ChronoUnit.DAYS))

      val STRING = VariableValue(STRING_VAR, "value")
      val DATE = VariableValue(DATE_VAR, now)
      val SHORT = VariableValue(SHORT_VAR, 11.toShort())
      val INT = VariableValue(INT_VAR, 123)
      val LONG = VariableValue(LONG_VAR, 812L)
      val DOUBLE = VariableValue(DOUBLE_VAR, 12.0)
      val BOOLEAN = VariableValue(BOOLEAN_VAR, true)
      val COMPLEX = VariableValue(COMPLEX_VAR, ComplexDataStructure("string", 17, now))
      val LIST_STRING = VariableValue(LIST_STRING_VAR, listOf("Hello", "World"))
      val SET_STRING = VariableValue(SET_STRING_VAR, setOf("Kermit", "Piggy"))
      val MAP_STRING_DATE = VariableValue(MAP_STRING_DATE_VAR, mapOf("Twelve" to now, "Eleven" to now))

      val STRING_LOCAL = VariableValue(STRING_VAR, "localValue")
      val DATE_LOCAL = VariableValue(DATE_VAR, yesterday)
      val SHORT_LOCAL = VariableValue(SHORT_VAR, 12.toShort())
      val INT_LOCAL = VariableValue(INT_VAR, 124)
      val LONG_LOCAL = VariableValue(LONG_VAR, 815L)
      val DOUBLE_LOCAL = VariableValue(DOUBLE_VAR, 14.0)
      val BOOLEAN_LOCAL = VariableValue(BOOLEAN_VAR, false)
      val COMPLEX_LOCAL = VariableValue(COMPLEX_VAR, ComplexDataStructure("foobar", 12, yesterday))
      val LIST_STRING_LOCAL = VariableValue(LIST_STRING_VAR, listOf("Foo", "Bar"))
      val SET_STRING_LOCAL = VariableValue(SET_STRING_VAR, setOf("Homer", "Marge"))
      val MAP_STRING_DATE_LOCAL = VariableValue(MAP_STRING_DATE_VAR, mapOf("Ten" to yesterday, "Nine" to yesterday))
    }

    private val allValues = mapOf(
      STRING_VAR to STRING,
      DATE_VAR to DATE,
      SHORT_VAR to SHORT,
      INT_VAR to INT,
      LONG_VAR to LONG,
      DOUBLE_VAR to DOUBLE,
      BOOLEAN_VAR to BOOLEAN,
      COMPLEX_VAR to COMPLEX,
      LIST_STRING_VAR to LIST_STRING,
      SET_STRING_VAR to SET_STRING,
      MAP_STRING_DATE_VAR to MAP_STRING_DATE
    )

    private val allLocalValues = mapOf(
      STRING_VAR to STRING_LOCAL,
      DATE_VAR to DATE_LOCAL,
      SHORT_VAR to SHORT_LOCAL,
      INT_VAR to INT_LOCAL,
      LONG_VAR to LONG_LOCAL,
      DOUBLE_VAR to DOUBLE_LOCAL,
      BOOLEAN_VAR to BOOLEAN_LOCAL,
      COMPLEX_VAR to COMPLEX_LOCAL,
      LIST_STRING_VAR to LIST_STRING_LOCAL,
      SET_STRING_VAR to SET_STRING_LOCAL,
      MAP_STRING_DATE_VAR to MAP_STRING_DATE_LOCAL
    )

    fun createVariableMapUntyped(): VariableMap {
      val variables = createVariables()
      allValues.values.forEach {
        variables.putValue(it.variable.name, it.value)
      }
      return variables
    }

    fun createKeyValuePairs(): Set<Pair<VariableFactory<out Any>, Any>> {
      return allValues.entries.map { Pair(it.key, it.value.value) }.toSet()
    }

    fun createKeyLocalValuePairs(): Set<Pair<VariableFactory<out Any>, Any>> {
      return allLocalValues.entries.map { Pair(it.key, it.value.value) }.toSet()
    }
  }

  @Configuration
  class DelegateConfiguration {

    val vars = HashMap<String, Any>()
    val optionalVars = HashMap<String, Optional<*>>()
    var variableMap: VariableMap = createVariables()

    @Bean
    fun serviceWriteAdapter() = JavaDelegate { delegateExecution ->
      variableMap = delegateExecution.variablesTyped
    }

    @Bean
    fun readOptionalFromVariableScope() = JavaDelegate { delegateExecution ->
      optionalVars[STRING_VAR.name] = STRING_VAR.from(delegateExecution).optional
      optionalVars[DATE_VAR.name] = DATE_VAR.from(delegateExecution).optional
      optionalVars[SHORT_VAR.name] = SHORT_VAR.from(delegateExecution).optional
      optionalVars[INT_VAR.name] = INT_VAR.from(delegateExecution).optional
      optionalVars[LONG_VAR.name] = LONG_VAR.from(delegateExecution).optional
      optionalVars[DOUBLE_VAR.name] = DOUBLE_VAR.from(delegateExecution).optional
      optionalVars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(delegateExecution).optional
      optionalVars[COMPLEX_VAR.name] = COMPLEX_VAR.from(delegateExecution).optional
      optionalVars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(delegateExecution).optional
      optionalVars[SET_STRING_VAR.name] = SET_STRING_VAR.from(delegateExecution).optional
      optionalVars[MAP_STRING_DATE_VAR.name] = MAP_STRING_DATE_VAR.from(delegateExecution).optional
    }

    @Bean
    fun readFromVariableScope() = JavaDelegate { delegateExecution ->
      vars[STRING_VAR.name] = STRING_VAR.from(delegateExecution).get()
      vars[DATE_VAR.name] = DATE_VAR.from(delegateExecution).get()
      vars[SHORT_VAR.name] = SHORT_VAR.from(delegateExecution).get()
      vars[INT_VAR.name] = INT_VAR.from(delegateExecution).get()
      vars[LONG_VAR.name] = LONG_VAR.from(delegateExecution).get()
      vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(delegateExecution).get()
      vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(delegateExecution).get()
      vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(delegateExecution).get()
      vars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(delegateExecution).get()
      vars[SET_STRING_VAR.name] = SET_STRING_VAR.from(delegateExecution).get()
      vars[MAP_STRING_DATE_VAR.name] = MAP_STRING_DATE_VAR.from(delegateExecution).get()
    }

    @Bean
    fun readLocalFromVariableScope() = JavaDelegate { delegateExecution ->
      readLocalVarsFromVariableScope(variableScope = delegateExecution)
    }

    @Bean
    fun readLocalFromDelegateTask() = TaskListener { delegateTask ->
      readLocalVarsFromVariableScope(variableScope = delegateTask)
    }

    /**
     * Writes local properties from variable scope.
     */
    private fun readLocalVarsFromVariableScope(variableScope: VariableScope) {
      vars[STRING_VAR.name] = STRING_VAR.from(variableScope).local
      vars[DATE_VAR.name] = DATE_VAR.from(variableScope).local
      vars[SHORT_VAR.name] = SHORT_VAR.from(variableScope).local
      vars[INT_VAR.name] = INT_VAR.from(variableScope).local
      vars[LONG_VAR.name] = LONG_VAR.from(variableScope).local
      vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(variableScope).local
      vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(variableScope).local
      vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(variableScope).local
      vars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(variableScope).local
      vars[SET_STRING_VAR.name] = SET_STRING_VAR.from(variableScope).local
      vars[MAP_STRING_DATE_VAR.name] = MAP_STRING_DATE_VAR.from(variableScope).local
    }

    @Bean
    fun readNonExisting() = JavaDelegate { delegateExecution ->
      val nonExisting = stringVariable("non-existing")
      nonExisting.from(delegateExecution).get()
    }


    @Bean
    fun readFromVariableMap() = JavaDelegate { delegateExecution ->
      val variableMap = delegateExecution.variablesTyped

      vars[STRING_VAR.name] = STRING_VAR.from(variableMap).get()
      vars[DATE_VAR.name] = DATE_VAR.from(variableMap).get()
      vars[SHORT_VAR.name] = SHORT_VAR.from(variableMap).get()
      vars[INT_VAR.name] = INT_VAR.from(variableMap).get()
      vars[LONG_VAR.name] = LONG_VAR.from(variableMap).get()
      vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(variableMap).get()
      vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(variableMap).get()
      vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(variableMap).get()
      vars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(variableMap).get()
      vars[SET_STRING_VAR.name] = SET_STRING_VAR.from(variableMap).get()
      vars[MAP_STRING_DATE_VAR.name] = MAP_STRING_DATE_VAR.from(variableMap).get()
    }

    @Bean
    fun writeVariablesToScope() = JavaDelegate { delegateExecution ->
      STRING_VAR.on(delegateExecution).set(STRING.value)
      DATE_VAR.on(delegateExecution).set(DATE.value)
      SHORT_VAR.on(delegateExecution).set(SHORT.value)
      INT_VAR.on(delegateExecution).set(INT.value)
      LONG_VAR.on(delegateExecution).set(LONG.value)
      DOUBLE_VAR.on(delegateExecution).set(DOUBLE.value)
      BOOLEAN_VAR.on(delegateExecution).set(BOOLEAN.value)
      COMPLEX_VAR.on(delegateExecution).set(COMPLEX.value)
      LIST_STRING_VAR.on(delegateExecution).set(LIST_STRING.value)
      SET_STRING_VAR.on(delegateExecution).set(SET_STRING.value)
      MAP_STRING_DATE_VAR.on(delegateExecution).set(MAP_STRING_DATE.value)
    }

    @Bean
    fun writeVariablesToScopeAndLocal() = JavaDelegate { delegateExecution ->
      STRING_VAR.on(delegateExecution).set(STRING.value)
      DATE_VAR.on(delegateExecution).set(DATE.value)
      SHORT_VAR.on(delegateExecution).set(SHORT.value)
      INT_VAR.on(delegateExecution).set(INT.value)
      LONG_VAR.on(delegateExecution).set(LONG.value)
      DOUBLE_VAR.on(delegateExecution).set(DOUBLE.value)
      BOOLEAN_VAR.on(delegateExecution).set(BOOLEAN.value)
      COMPLEX_VAR.on(delegateExecution).set(COMPLEX.value)
      LIST_STRING_VAR.on(delegateExecution).set(LIST_STRING.value)
      SET_STRING_VAR.on(delegateExecution).set(SET_STRING.value)
      MAP_STRING_DATE_VAR.on(delegateExecution).set(MAP_STRING_DATE.value)

      STRING_VAR.on(delegateExecution).setLocal(STRING_LOCAL.value)
      DATE_VAR.on(delegateExecution).setLocal(DATE_LOCAL.value)
      SHORT_VAR.on(delegateExecution).setLocal(SHORT_LOCAL.value)
      INT_VAR.on(delegateExecution).setLocal(INT_LOCAL.value)
      LONG_VAR.on(delegateExecution).setLocal(LONG_LOCAL.value)
      DOUBLE_VAR.on(delegateExecution).setLocal(DOUBLE_LOCAL.value)
      BOOLEAN_VAR.on(delegateExecution).setLocal(BOOLEAN_LOCAL.value)
      COMPLEX_VAR.on(delegateExecution).setLocal(COMPLEX_LOCAL.value)
      LIST_STRING_VAR.on(delegateExecution).setLocal(LIST_STRING_LOCAL.value)
      SET_STRING_VAR.on(delegateExecution).setLocal(SET_STRING_LOCAL.value)
      MAP_STRING_DATE_VAR.on(delegateExecution).setLocal(MAP_STRING_DATE_LOCAL.value)
    }

    @Bean
    fun deleteVariablesFromScope() = JavaDelegate { delegateExecution ->
      STRING_VAR.on(delegateExecution).remove()
      LIST_STRING_VAR.on(delegateExecution).remove()
      SET_STRING_VAR.on(delegateExecution).remove()
      MAP_STRING_DATE_VAR.on(delegateExecution).remove()
    }
  }


  /**
   * Value holder.
   */
  data class VariableValue<T : Any?>(val variable: VariableFactory<T>, val value: T)

  /**
   * Complex data structure.
   */
  data class ComplexDataStructure(
    val stringValue: String,
    val intValue: Int,
    val dateValue: Date
  ) {
    @JsonIgnore
    val valueToIgnore: String = "some hidden value"
  }

  /**
   * Application to start
   */
  @EnableJGiven
  @ComponentScan
  @SpringBootConfiguration
  @EnableAutoConfiguration
  class TestApplication
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

  @Autowired
  lateinit var delegateConfiguration: CamundaBpmDataITestBase.DelegateConfiguration

  @BeforeScenario
  fun cleanUp() {
    delegateConfiguration.vars.clear()
    delegateConfiguration.optionalVars.clear()
    delegateConfiguration.variableMap = createVariables()
  }

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

    deploy(processDefinitionKey, instance)

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

    deploy(processDefinitionKey, instance)

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
    deploy(processDefinitionKey, instance)

    return self()
  }

  fun process_with_user_task_and_listener_is_deployed(
    processDefinitionKey: String = "process_with_user_task_and_listener",
    taskDefinitionKey: String = "user_task",
    delegateExpression: String = "\${listenerDelegate}"
  ): ActionStage {

    val instance = Bpmn
      .createExecutableProcess(processDefinitionKey)
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

    val instance = Bpmn
      .createExecutableProcess(processDefinitionKey)
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
    variables: VariableMap
  ): ActionStage {

    processInstance = runtimeService
      .startProcessInstanceByKey(processDefinitionKey, variables)

    return self()
  }

  /**
   * Reads task.
   */
  fun process_waits_in_task(taskDefinitionKey: String = "user_task"): ActionStage {
    val query = taskService.createTaskQuery().processInstanceId(processInstance.id).taskDefinitionKey(taskDefinitionKey)
    assertThat(query.count()).isEqualTo(1L)
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

/**
 * Base assert stage.
 */
@JGivenStage
class AssertStage : Stage<AssertStage>() {

  fun variables_had_value(readValues: Map<String, Any>, variablesWithValue: Set<Pair<VariableFactory<*>, Any>>): AssertStage {
    variablesWithValue.forEach {
      assertThat(readValues).containsEntry(it.first.name, it.second)
    }
    return self()
  }

  fun variables_had_not_value(readValues: Map<String, Any>, vararg variableWithValue: VariableFactory<*>): AssertStage {
    val emptyOptional = Optional.empty<Any>()

    variableWithValue.forEach {
      assertThat(readValues).containsEntry(it.name, emptyOptional)
    }
    return self()
  }
}
