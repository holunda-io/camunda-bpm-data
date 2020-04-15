package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.adapter.VariableNotFoundException
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class VariableScopeAdapterITest : CamundaBpmDataITestBase() {

  @Suppress("RedundantVisibilityModifier")
  @get: Rule
  public val thrown = ExpectedException.none()

  @Autowired
  lateinit var delegateConfiguration: DelegateConfiguration

  @Test
  fun `should write to variable scope and read`() {

    given()
      .process_with_modifying_delegate_is_deployed(
        modifyingDelegateExpression = "\${${DelegateConfiguration::writeVariablesToScope.name}}",
        delegateExpression = "\${${DelegateConfiguration::readFromVariableScope.name}}"
      )
    whenever()
      .process_is_started_with_variables(variables = createVariables())
    then()
      .variables_had_value(readValues = delegateConfiguration.vars, variablesWithValue = createKeyValuePairs())
  }

  @Test
  fun `should write to variables-map and read using adapter`() {

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readFromVariableScope.name}}")
    whenever()
      .process_is_started_with_variables(variables = createVariableMapUntyped())
    then()
      .variables_had_value(delegateConfiguration.vars, createKeyValuePairs())
  }

  @Test
  fun `should remove to variable scope and read`() {

    given()
      .process_with_modifying_delegate_is_deployed(
        modifyingDelegateExpression = "\${${DelegateConfiguration::deleteVariablesFromScope.name}}",
        delegateExpression = "\${${DelegateConfiguration::readOptionalFromVariableScope.name}}"
      )
    whenever()
      .process_is_started_with_variables(variables = createVariableMapUntyped())

    then()
      .variables_had_not_value(delegateConfiguration.optionalVars,
        STRING_VAR,
        LIST_STRING_VAR,
        SET_STRING_VAR,
        MAP_STRING_DATE_VAR
      )
      .and()
      .variables_had_value(delegateConfiguration.optionalVars,
        setOf(LONG_VAR to Optional.of(Companion.Values.LONG.value))
      )

  }


  @Test
  fun `Should throw correct exceptions`() {

    thrown.expect(VariableNotFoundException::class.java)

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readNonExisting.name}}")
    whenever()
      .process_is_started_with_variables(variables = createVariableMapUntyped())
  }

  @Test
  fun `should write to local variable scope and read`() {

    given()
      .process_with_modifying_delegate_is_deployed(
        modifyingDelegateExpression = "\${${DelegateConfiguration::writeVariablesToScopeAndLocal.name}}",
        delegateExpression = "\${${DelegateConfiguration::readLocalFromVariableScope.name}}"
      )
    whenever()
      .process_is_started_with_variables(variables = createVariables())
    then()
      .variables_had_value(readValues = delegateConfiguration.vars, variablesWithValue = createKeyLocalValuePairs())
  }

}


