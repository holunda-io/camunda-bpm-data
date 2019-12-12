package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.BOOLEAN
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DOUBLE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.INT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class RuntimeServiceAdapterITest : CamundaBpmDataITestBase() {

  @Autowired
  lateinit var delegateConfiguration: DelegateConfiguration

  @Test
  fun `should write to task service adapter`() {

    val variables = createVariables()

    given()
      .process_with_user_task_and_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readFromVariableScope.name}}")
      .and()
      .process_is_started_with_variables(variables = variables)
      .and()
      .process_waits_in_task()

    whenever()
      .execution_is_accessed_in_wait_state { runtimeService, executionId ->
        STRING_VAR.on(runtimeService, executionId).set(STRING.value)
        DATE_VAR.on(runtimeService, executionId).set(DATE.value)
        SHORT_VAR.on(runtimeService, executionId).set(SHORT.value)
        INT_VAR.on(runtimeService, executionId).set(INT.value)
        LONG_VAR.on(runtimeService, executionId).set(LONG.value)
        DOUBLE_VAR.on(runtimeService, executionId).set(DOUBLE.value)
        BOOLEAN_VAR.on(runtimeService, executionId).set(BOOLEAN.value)
        COMPLEX_VAR.on(runtimeService, executionId).set(COMPLEX.value)
        LIST_STRING_VAR.on(runtimeService, executionId).set(LIST_STRING.value)
        SET_STRING_VAR.on(runtimeService, executionId).set(SET_STRING.value)
        MAP_STRING_DATE_VAR.on(runtimeService, executionId).set(MAP_STRING_DATE.value)
      }
      .and()
      .task_is_completed()

    then()
      .variables_had_value(readValues = delegateConfiguration.vars, variablesWithValue = createKeyValuePairs())
  }

  @Test
  fun `should remove on runtime service adapter`() {

    given()
      .process_with_user_task_and_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readOptionalFromVariableScope.name}}")
      .and()
      .process_is_started_with_variables(variables = createVariableMapUntyped())
      .and()
      .process_waits_in_task()

    whenever()
      .execution_is_accessed_in_wait_state { runtimeService, executionId ->
        STRING_VAR.on(runtimeService, executionId).remove()
        LIST_STRING_VAR.on(runtimeService, executionId).remove()
        SET_STRING_VAR.on(runtimeService, executionId).remove()
        MAP_STRING_DATE_VAR.on(runtimeService, executionId).remove()
      }
      .and()
      .task_is_completed()

    then()
      .variables_had_not_value(delegateConfiguration.optionalVars,
        STRING_VAR,
        LIST_STRING_VAR,
        SET_STRING_VAR,
        MAP_STRING_DATE_VAR
      )
      .and()
      .variables_had_value(delegateConfiguration.optionalVars, setOf(LONG_VAR to Optional.of(LONG.value)))
  }

  @Test
  fun `should write to variables-map and read runtime adapter`() {

    val vars = HashMap<String, Any>()

    given()
      .process_with_user_task_is_deployed()
      .and()
      .process_is_started_with_variables(variables = createVariableMapUntyped())

    whenever()
      .execution_is_accessed_in_wait_state { runtimeService, executionId ->
        vars[STRING_VAR.name] = STRING_VAR.from(runtimeService, executionId).get()
        vars[DATE_VAR.name] = DATE_VAR.from(runtimeService, executionId).get()
        vars[SHORT_VAR.name] = SHORT_VAR.from(runtimeService, executionId).get()
        vars[INT_VAR.name] = INT_VAR.from(runtimeService, executionId).get()
        vars[LONG_VAR.name] = LONG_VAR.from(runtimeService, executionId).get()
        vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(runtimeService, executionId).get()
        vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(runtimeService, executionId).get()
        vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(runtimeService, executionId).get()
        vars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(runtimeService, executionId).get()
        vars[SET_STRING_VAR.name] = SET_STRING_VAR.from(runtimeService, executionId).get()
        vars[MAP_STRING_DATE_VAR.name] = MAP_STRING_DATE_VAR.from(runtimeService, executionId).get()
      }

    then()
      .variables_had_value(vars, variablesWithValue = createKeyValuePairs())
  }
}


