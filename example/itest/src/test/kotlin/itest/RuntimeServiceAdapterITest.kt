package io.holunda.camunda.bpm.data.itest

import com.google.common.util.concurrent.ExecutionList
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.BOOLEAN
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_LIST
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_MAP
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_SET
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DOUBLE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.INT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.ExecutionListener
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.OffsetDateTime
import java.util.*

class RuntimeServiceAdapterITest : CamundaBpmDataITestBase() {

    @Autowired
    lateinit var delegateConfiguration: DelegateConfiguration

    @Test
    fun `should write to runtime service adapter`() {

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
                MAP_STRING_LONG_VAR.on(runtimeService, executionId).set(MAP_STRING_LONG.value)
                COMPLEX_SET_VAR.on(runtimeService, executionId).set(COMPLEX_SET.value)
                COMPLEX_LIST_VAR.on(runtimeService, executionId).set(COMPLEX_LIST.value)
                COMPLEX_MAP_VAR.on(runtimeService, executionId).set(COMPLEX_MAP.value)
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
                MAP_STRING_LONG_VAR.on(runtimeService, executionId).remove()
            }
            .and()
            .task_is_completed()

        then()
            .variables_had_not_value(delegateConfiguration.optionalVars,
                STRING_VAR,
                LIST_STRING_VAR,
                SET_STRING_VAR,
                MAP_STRING_LONG_VAR
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
                vars[MAP_STRING_LONG_VAR.name] = MAP_STRING_LONG_VAR.from(runtimeService, executionId).get()
                vars[COMPLEX_SET_VAR.name] = COMPLEX_SET_VAR.from(runtimeService, executionId).get()
                vars[COMPLEX_LIST_VAR.name] = COMPLEX_LIST_VAR.from(runtimeService, executionId).get()
                vars[COMPLEX_MAP_VAR.name] = COMPLEX_MAP_VAR.from(runtimeService, executionId).get()
            }

        then()
            .variables_had_value(vars, variablesWithValue = createKeyValuePairs())
    }
}


