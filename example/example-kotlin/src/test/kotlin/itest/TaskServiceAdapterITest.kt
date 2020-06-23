package io.holunda.camunda.bpm.data.itest

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
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_DATE_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT_LOCAL
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING_LOCAL
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.collections.HashMap

class TaskServiceAdapterITest : CamundaBpmDataITestBase() {

    @Autowired
    lateinit var delegateConfiguration: DelegateConfiguration

    @Test
    fun `should write to task service adapter`() {

        given()
            .process_with_user_task_and_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readFromVariableScope.name}}")
            .and()
            .process_is_started_with_variables(variables = createVariables())
            .and()
            .process_waits_in_task()

        whenever()
            .task_is_accessed_in_user_task { taskService, taskId ->
                STRING_VAR.on(taskService, taskId).set(STRING.value)
                DATE_VAR.on(taskService, taskId).set(DATE.value)
                SHORT_VAR.on(taskService, taskId).set(SHORT.value)
                INT_VAR.on(taskService, taskId).set(INT.value)
                LONG_VAR.on(taskService, taskId).set(LONG.value)
                DOUBLE_VAR.on(taskService, taskId).set(DOUBLE.value)
                BOOLEAN_VAR.on(taskService, taskId).set(BOOLEAN.value)
                COMPLEX_VAR.on(taskService, taskId).set(COMPLEX.value)
                LIST_STRING_VAR.on(taskService, taskId).set(LIST_STRING.value)
                SET_STRING_VAR.on(taskService, taskId).set(SET_STRING.value)
                MAP_STRING_LONG_VAR.on(taskService, taskId).set(MAP_STRING_LONG.value)
                COMPLEX_SET_VAR.on(taskService, taskId).set(Companion.Values.COMPLEX_SET.value)
                COMPLEX_LIST_VAR.on(taskService, taskId).set(Companion.Values.COMPLEX_LIST.value)
                COMPLEX_MAP_VAR.on(taskService, taskId).set(Companion.Values.COMPLEX_MAP.value)

            }
            .and()
            .task_is_completed()

        then()
            .variables_had_value(readValues = delegateConfiguration.vars, variablesWithValue = createKeyValuePairs())
    }

    @Test
    fun `should remove on task service adapter`() {

        given()
            .process_with_user_task_and_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readOptionalFromVariableScope.name}}")
            .and()
            .process_is_started_with_variables(variables = createVariableMapUntyped())
            .and()
            .process_waits_in_task()

        whenever()
            .task_is_accessed_in_user_task { taskService, taskId ->
                STRING_VAR.on(taskService, taskId).remove()
                LIST_STRING_VAR.on(taskService, taskId).remove()
                SET_STRING_VAR.on(taskService, taskId).remove()
                MAP_STRING_LONG_VAR.on(taskService, taskId).remove()
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
            .variables_had_value(delegateConfiguration.optionalVars,
                setOf(LONG_VAR to Optional.of(LONG.value))
            )
    }


    @Test
    fun `should write to variables-map and read task service adapter`() {

        val vars = HashMap<String, Any>()

        given()
            .process_with_user_task_is_deployed()
            .and()
            .process_is_started_with_variables(variables = createVariableMapUntyped())
            .and()
            .process_waits_in_task()

        whenever()
            .task_is_accessed_in_user_task { taskService, taskId ->
                vars[STRING_VAR.name] = STRING_VAR.from(taskService, taskId).get()
                vars[DATE_VAR.name] = DATE_VAR.from(taskService, taskId).get()
                vars[SHORT_VAR.name] = SHORT_VAR.from(taskService, taskId).get()
                vars[INT_VAR.name] = INT_VAR.from(taskService, taskId).get()
                vars[LONG_VAR.name] = LONG_VAR.from(taskService, taskId).get()
                vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(taskService, taskId).get()
                vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(taskService, taskId).get()
                vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(taskService, taskId).get()
                vars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(taskService, taskId).get()
                vars[SET_STRING_VAR.name] = SET_STRING_VAR.from(taskService, taskId).get()
                vars[MAP_STRING_LONG_VAR.name] = MAP_STRING_LONG_VAR.from(taskService, taskId).get()
                vars[COMPLEX_SET_VAR.name] = COMPLEX_SET_VAR.from(taskService, taskId).get()
                vars[COMPLEX_LIST_VAR.name] = COMPLEX_LIST_VAR.from(taskService, taskId).get()
                vars[COMPLEX_MAP_VAR.name] = COMPLEX_MAP_VAR.from(taskService, taskId).get()
            }

        then()
            .variables_had_value(readValues = vars, variablesWithValue = createKeyValuePairs())
    }

    @Test
    fun `should write local variables to task service adapter`() {

        given()
            .process_with_user_task_and_listener_is_deployed(delegateExpression = "\${${DelegateConfiguration::readLocalFromDelegateTask.name}}")
            .and()
            .process_is_started_with_variables(variables = createVariables())
            .and()
            .process_waits_in_task()

        whenever()
            .task_is_accessed_in_user_task { taskService, taskId ->
                STRING_VAR.on(taskService, taskId).setLocal(STRING_LOCAL.value)
                DATE_VAR.on(taskService, taskId).setLocal(DATE_LOCAL.value)
                SHORT_VAR.on(taskService, taskId).setLocal(SHORT_LOCAL.value)
                INT_VAR.on(taskService, taskId).setLocal(INT_LOCAL.value)
                LONG_VAR.on(taskService, taskId).setLocal(LONG_LOCAL.value)
                DOUBLE_VAR.on(taskService, taskId).setLocal(DOUBLE_LOCAL.value)
                BOOLEAN_VAR.on(taskService, taskId).setLocal(BOOLEAN_LOCAL.value)
                COMPLEX_VAR.on(taskService, taskId).setLocal(COMPLEX_LOCAL.value)
                LIST_STRING_VAR.on(taskService, taskId).setLocal(LIST_STRING_LOCAL.value)
                SET_STRING_VAR.on(taskService, taskId).setLocal(SET_STRING_LOCAL.value)
                MAP_STRING_LONG_VAR.on(taskService, taskId).setLocal(MAP_STRING_DATE_LOCAL.value)
                COMPLEX_SET_VAR.on(taskService, taskId).setLocal(Companion.Values.COMPLEX_SET_LOCAL.value)
                COMPLEX_LIST_VAR.on(taskService, taskId).setLocal(Companion.Values.COMPLEX_LIST_LOCAL.value)
                COMPLEX_MAP_VAR.on(taskService, taskId).setLocal(Companion.Values.COMPLEX_MAP_LOCAL.value)
            }
            .and()
            .task_is_completed()

        then()
            .variables_had_value(readValues = delegateConfiguration.vars, variablesWithValue = createKeyLocalValuePairs())
    }

}


