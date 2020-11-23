package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.condition.*
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test

class VariableGuardConditionTest {

  companion object {
    private val STRING_VAR = stringVariable("stringVariable")
  }

  @Test
  fun test_exists() {

    val guard = VariablesGuard(STRING_VAR.exists())

    val result = guard.evaluate(createVariables())

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable 'stringVariable' to be set, but it was not found.")
  }

  @Test
  fun test_not_exists() {

    val guard = VariablesGuard(STRING_VAR.notExists())

    val vars = createVariables()
    STRING_VAR.on(vars).set("some")

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable 'stringVariable' not to be set, but it had a value of 'some'.")
  }

  @Test
  fun test_wrong_value() {

    val guard = VariablesGuard(STRING_VAR.hasValue("expected"))

    val vars = createVariables()
    STRING_VAR.on(vars).set("some")

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable 'stringVariable' to have value 'expected', but it was 'some'.")
  }

  @Test
  fun test_wrong_value_one_of() {

    val guard = VariablesGuard(STRING_VAR.hasOneOfValues(setOf("expected", "another expected")))

    val vars = createVariables()
    STRING_VAR.on(vars).set("some")

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable 'stringVariable' to be one of ['expected', 'another expected'], but it was 'some'.")
  }

  @Test
  fun test_matches() {

    val guard = VariablesGuard(STRING_VAR.matches { it.startsWith("expected") })

    val vars = createVariables()
    STRING_VAR.on(vars).set("some")

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable 'stringVariable' to match the condition, but its value 'some' has not.")
  }

}
