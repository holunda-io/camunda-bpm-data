package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.itest.helper.OptionalValueStoringUsingAdapterVariableScopeServiceDelegate
import io.holunda.camunda.bpm.data.itest.helper.ValueStoringUsingAdapterVariableScopeServiceDelegate
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.camunda.bpm.engine.variable.Variables.stringValue
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.*

class TaskServiceAdapterWriteITest : CamundaBpmDataITestBase() {

  @Autowired
  lateinit var valueStoringUsingAdapterVariableScopeServiceDelegate: ValueStoringUsingAdapterVariableScopeServiceDelegate

  @Autowired
  lateinit var optionalValueStoringUsingAdapterVariableScopeServiceDelegate: OptionalValueStoringUsingAdapterVariableScopeServiceDelegate

  @Test
  fun `should write to task service adapter`() {

    val date = Date.from(Instant.now())
    val complexValue = ComplexDataStructure("string", 17, date)
    val listOfStrings = listOf("Hello", "World")
    val setOfStrings = setOf("Kermit", "Piggy")
    val map: Map<String, Date> = mapOf("Twelve" to date, "Eleven" to date)
    val variables = createVariables()

    given()
      .process_with_user_task_and_delegate_is_deployed(delegateExpression = "\${ValueStoringUsingAdapterVariableScopeServiceDelegate}")
      .and()
      .process_is_started_with_variables(variables = variables)
      .and()
      .process_waits_in_task()
    whenever()
      .variable_are_modified_in_task() { taskService, taskId ->
        STRING_VAR.on(taskService, taskId).set("value")
        DATE_VAR.on(taskService, taskId).set(date)
        SHORT_VAR.on(taskService, taskId).set(11.toShort())
        INT_VAR.on(taskService, taskId).set(123)
        LONG_VAR.on(taskService, taskId).set(812L)
        DOUBLE_VAR.on(taskService, taskId).set(12.0)
        BOOLEAN_VAR.on(taskService, taskId).set(true)
        COMPLEX_VAR.on(taskService, taskId).set(complexValue)
        LIST_STRING_VAR.on(taskService, taskId).set(listOfStrings)
        SET_STRING_VAR.on(taskService, taskId).set(setOfStrings)
        MAP_STRING_DATE_VAR.on(taskService, taskId).set(map)
      }
      .and()
      .task_is_completed()
    then()
      .variables_had_value(valueStoringUsingAdapterVariableScopeServiceDelegate.vars,
        STRING_VAR to "value",
        DATE_VAR to date,
        SHORT_VAR to 11.toShort(),
        INT_VAR to 123.toInt(),
        LONG_VAR to 812.toLong(),
        DOUBLE_VAR to 12.0.toDouble(),
        BOOLEAN_VAR to true,
        COMPLEX_VAR to complexValue,
        LIST_STRING_VAR to listOfStrings,
        SET_STRING_VAR to setOfStrings,
        MAP_STRING_DATE_VAR to map
      )
  }

  @Test
  fun `should remove on task service adapter`() {

    val date = Date.from(Instant.now())
    val complexValue = ComplexDataStructure("string", 17, date)
    val listOfStrings = listOf("Hello", "World")
    val setOfStrings = setOf("Kermit", "Piggy")
    val map: Map<String, Date> = mapOf("Twelve" to date, "Eleven" to date)
    val variables = createVariables()
      .putValueTyped(STRING_VAR.name, stringValue("string"))

    given()
      .process_with_user_task_and_delegate_is_deployed(delegateExpression = "\${OptionalValueStoringUsingAdapterVariableScopeServiceDelegate}")
      .and()
      .process_is_started_with_variables(variables = variables)
      .and()
      .process_waits_in_task()
    whenever()
      .variable_are_modified_in_task() { taskService, taskId ->
        STRING_VAR.on(taskService, taskId).remove()
      }
      .and()
      .task_is_completed()
    then()
      .variables_had_not_value(optionalValueStoringUsingAdapterVariableScopeServiceDelegate.vars,
        STRING_VAR
      )
  }

}


