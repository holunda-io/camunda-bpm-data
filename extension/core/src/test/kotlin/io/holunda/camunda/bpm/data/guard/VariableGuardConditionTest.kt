package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.CamundaBpmData.customVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.condition.*
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import java.util.*
import java.util.function.Supplier
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.constraints.Email

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

  @Test
  fun test_matches_with_validation_message_supplier() {

    val guard = VariablesGuard(STRING_VAR.matches({ variableFactory, localLabel, option ->
      "Expecting$localLabel variable '${variableFactory.name}' to start with 'expected', but its value '${option.get()}' has not."
    }) { it.startsWith("expected") })

    val vars = createVariables()
    STRING_VAR.on(vars).set("some")

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable 'stringVariable' to start with 'expected', but its value 'some' has not.")
  }

  @Test
  fun test_matches_regex() {
    val value = "exreg"
    val valueVariable = stringVariable(value)
    val guard = VariablesGuard(valueVariable.matchesRegex(Regex("^regex.*")))

    val vars = createVariables()
    valueVariable.on(vars).set(value)

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable '$value' to match the regex '^regex.*', but its value '$value' has not.")
  }

  @Test
  fun test_matches_regex_with_display_name() {
    val value = "exreg"
    val valueVariable = stringVariable(value)
    val guard = VariablesGuard(valueVariable.matchesRegex(Regex("^regex.*"), "my Regex"))

    val vars = createVariables()
    valueVariable.on(vars).set(value)

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable '$value' to match the regex 'my Regex', but its value '$value' has not.")
  }

  @Test
  fun test_matches_email_regex() {
    val email = "b.testholisticon.de"
    val emailVariable = stringVariable(email)
    val guard = VariablesGuard(emailVariable.isEmail())

    val vars = createVariables()
    emailVariable.on(vars).set(email)

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable '$email' to match the regex 'E-Mail', but its value '$email' has not.")
  }

  @Test
  fun test_matches_uuid_regex() {
    val uuid = UUID.randomUUID().toString() + "1"
    val uuidVariable = stringVariable(uuid)
    val guard = VariablesGuard(uuidVariable.isUuid())

    val vars = createVariables()
    uuidVariable.on(vars).set(uuid)

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).isEqualTo("Expecting variable '$uuid' to match the regex 'UUID', but its value '$uuid' has not.")
  }

  @Test
  fun test_is_valid_bean() {
    val person = MyValidBean("peter")
    val personVariable = customVariable("person", MyValidBean::class.java)
    val validatorSupplier: Supplier<Validator> = Supplier { Validation.buildDefaultValidatorFactory().validator }
    val guard = VariablesGuard(personVariable.isValidBean(validatorSupplier))

    val vars = createVariables()
    personVariable.on(vars).set(person)

    val result = guard.evaluate(vars)

    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().message).startsWith("Expecting variable 'person' to be a valid bean, but its value '$person' has not.")
  }

}

data class MyValidBean(@field:Email val email: String)
