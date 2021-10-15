package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.condition.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import java.util.function.Function

class CamundaBpmDataGuardsTest {

  companion object {
    private val FOO = stringVariable("foo")
    private val EMAIL = "b.test@holisticon.de"
    private val EMAIL_VARIABLE = stringVariable(EMAIL)
    private val MYUUID = UUID.randomUUID().toString()
    private val UUID_VARIABLE = stringVariable(MYUUID)
  }

  @Test
  fun `should construct exists condition`() {
    val condition = CamundaBpmDataGuards.exists(FOO)
    assertThat(condition).isInstanceOf(VariableExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.toString()).isEqualTo("Exists condition for variable 'foo'")
    assertThat(condition.evaluate(Optional.of("value"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(condition, Optional.empty(), "Expecting variable 'foo' to be set, but it was not found.")
    )
  }

  @Test
  fun `should construct exists local condition`() {
    val condition = CamundaBpmDataGuards.existsLocal(FOO)
    assertThat(condition).isInstanceOf(VariableExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.toString()).isEqualTo("Exists condition for local variable 'foo'")
    assertThat(condition.evaluate(Optional.of("value"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(condition, Optional.empty(), "Expecting local variable 'foo' to be set, but it was not found.")
    )
  }

  @Test
  fun `should construct notExists condition`() {
    val condition = CamundaBpmDataGuards.notExists(FOO)
    assertThat(condition).isInstanceOf(VariableNotExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.toString()).isEqualTo("NotExists condition for variable 'foo'")
    assertThat(condition.evaluate(Optional.empty())).isEmpty()
    assertThat(condition.evaluate(Optional.of("value"))).contains(
      GuardViolation(condition, Optional.of("value"), "Expecting variable 'foo' not to be set, but it had a value of 'value'.")
    )
  }

  @Test
  fun `should construct notExists local condition`() {
    val condition = CamundaBpmDataGuards.notExistsLocal(FOO)
    assertThat(condition).isInstanceOf(VariableNotExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.toString()).isEqualTo("NotExists condition for local variable 'foo'")
    assertThat(condition.evaluate(Optional.empty())).isEmpty()
    assertThat(condition.evaluate(Optional.of("value"))).contains(
      GuardViolation(condition, Optional.of("value"), "Expecting local variable 'foo' not to be set, but it had a value of 'value'.")
    )
  }

  @Test
  fun `should construct hasValue condition`() {
    val condition = CamundaBpmDataGuards.hasValue(FOO, "val")
    assertThat(condition).isInstanceOf(VariableValueGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.value).isEqualTo("val")
    assertThat(condition.toString()).isEqualTo("Value condition for variable 'foo', value 'val'")
    assertThat(condition.evaluate(Optional.of("val"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.exists(FOO), Optional.empty(), "Expecting variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("other"))).contains(
      GuardViolation(condition, Optional.of("other"), "Expecting variable 'foo' to have value 'val', but it was 'other'.")
    )
  }

  @Test
  fun `should construct hasValue local condition`() {
    val condition = CamundaBpmDataGuards.hasValueLocal(FOO, "valLocal")
    assertThat(condition).isInstanceOf(VariableValueGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.value).isEqualTo("valLocal")
    assertThat(condition.toString()).isEqualTo("Value condition for local variable 'foo', value 'valLocal'")
    assertThat(condition.evaluate(Optional.of("valLocal"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.existsLocal(FOO), Optional.empty(), "Expecting local variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("other"))).contains(
      GuardViolation(condition, Optional.of("other"), "Expecting local variable 'foo' to have value 'valLocal', but it was 'other'.")
    )
  }

  @Test
  fun `should construct hasOneOfValues condition`() {
    val condition = CamundaBpmDataGuards.hasOneOfValues(FOO, setOf("val1", "val2"))
    assertThat(condition).isInstanceOf(VariableValueOneOfGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.values).containsExactlyInAnyOrder("val1", "val2")
    assertThat(condition.toString()).isEqualTo("ValueIn condition for variable 'foo', values ['val1', 'val2']")
    assertThat(condition.evaluate(Optional.of("val1"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.exists(FOO), Optional.empty(), "Expecting variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("other"))).contains(
      GuardViolation(condition, Optional.of("other"), "Expecting variable 'foo' to be one of ['val1', 'val2'], but it was 'other'.")
    )
  }

  @Test
  fun `should construct hasOneOfValues local condition`() {
    val condition = CamundaBpmDataGuards.hasOneOfValuesLocal(FOO, setOf("valLocal1", "valLocal2"))
    assertThat(condition).isInstanceOf(VariableValueOneOfGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.values).containsExactlyInAnyOrder("valLocal1", "valLocal2")
    assertThat(condition.toString()).isEqualTo("ValueIn condition for local variable 'foo', values ['valLocal1', 'valLocal2']")
    assertThat(condition.evaluate(Optional.of("valLocal1"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.existsLocal(FOO), Optional.empty(), "Expecting local variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("other"))).contains(
      GuardViolation(condition, Optional.of("other"), "Expecting local variable 'foo' to be one of ['valLocal1', 'valLocal2'], but it was 'other'.")
    )
  }

  @Test
  fun `should construct matches condition`() {
    val condition = CamundaBpmDataGuards.matches(FOO) { it == "special_val" }
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.toString()).isEqualTo("Matches condition for variable 'foo'")
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.exists(FOO), Optional.empty(), "Expecting variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("mismatch"))).contains(
      GuardViolation(condition, Optional.of("mismatch"), "Expecting variable 'foo' to match the condition, but its value 'mismatch' has not.")
    )
  }

  @Test
  fun `should construct matches local condition`() {
    val condition = CamundaBpmDataGuards.matchesLocal(FOO) { it == "special_val_local" }
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val_local"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.existsLocal(FOO), Optional.empty(), "Expecting local variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("mismatch"))).contains(
      GuardViolation(condition, Optional.of("mismatch"), "Expecting local variable 'foo' to match the condition, but its value 'mismatch' has not.")
    )
  }

  @Test
  fun `should construct matchesRegex condition`() {
    val condition = CamundaBpmDataGuards.matchesRegex(FOO, "^special.*")
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.exists(FOO), Optional.empty(), "Expecting variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("mismatch"))).contains(
      GuardViolation(condition, Optional.of("mismatch"), "Expecting variable 'foo' to match the regex '^special.*', but its value 'mismatch' has not.")
    )
  }

  @Test
  fun `should construct matchesRegex local condition`() {
    val condition = CamundaBpmDataGuards.matchesRegexLocal(FOO, "^special.*")
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
    assertThat(condition.evaluate(Optional.empty())).contains(
      GuardViolation(CamundaBpmDataGuards.existsLocal(FOO), Optional.empty(), "Expecting local variable 'foo' to be set, but it was not found.")
    )
    assertThat(condition.evaluate(Optional.of("mismatch"))).contains(
      GuardViolation(condition, Optional.of("mismatch"), "Expecting local variable 'foo' to match the regex '^special.*', but its value 'mismatch' has not.")
    )
  }

  @Test
  fun `should construct matchesRegex with regexDisplayName condition`() {
    val condition = CamundaBpmDataGuards.matchesRegex(FOO, "^special.*", "special")
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
  }

  @Test
  fun `should construct matchesRegex with regexDisplayName local condition`() {
    val condition = CamundaBpmDataGuards.matchesRegexLocal(FOO, "^special.*", "special")
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
  }

  @Test
  fun `should construct matchesRegex E-Mail condition`() {
    val condition = CamundaBpmDataGuards.isEmail(EMAIL_VARIABLE)
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(EMAIL_VARIABLE)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of(EMAIL))).isEmpty()
  }

  @Test
  fun `should construct matchesRegex E-Mail local condition`() {
    val condition = CamundaBpmDataGuards.isEmailLocal(EMAIL_VARIABLE)
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(EMAIL_VARIABLE)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of(EMAIL))).isEmpty()
  }

  @Test
  fun `should construct matchesRegex UUID condition`() {
    val condition = CamundaBpmDataGuards.isUuid(UUID_VARIABLE)
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(UUID_VARIABLE)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of(MYUUID))).isEmpty()
  }

  @Test
  fun `should construct matchesRegex UUID local condition`() {
    val condition = CamundaBpmDataGuards.isUuidLocal(UUID_VARIABLE)
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(UUID_VARIABLE)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of(MYUUID))).isEmpty()
  }

  @Test
  fun `kotlin should construct exists condition`() {
    val condition = FOO.exists()
    assertThat(condition).isInstanceOf(VariableExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
  }

  @Test
  fun `kotlin should construct exists local condition`() {
    val condition = FOO.existsLocal()
    assertThat(condition).isInstanceOf(VariableExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
  }

  @Test
  fun `kotlin should construct notExists condition`() {
    val condition = FOO.notExists()
    assertThat(condition).isInstanceOf(VariableNotExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
  }

  @Test
  fun `kotlin should construct notExists local condition`() {
    val condition = FOO.notExistsLocal()
    assertThat(condition).isInstanceOf(VariableNotExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
  }

  @Test
  fun `kotlin should construct hasValue condition`() {
    val condition = FOO.hasValue("val")
    assertThat(condition).isInstanceOf(VariableValueGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.value).isEqualTo("val")
  }

  @Test
  fun `kotlin should construct hasValue local condition`() {
    val condition = FOO.hasValueLocal("valLocal")
    assertThat(condition).isInstanceOf(VariableValueGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.value).isEqualTo("valLocal")
  }

  @Test
  fun `kotlin should construct hasOneOfValues condition`() {
    val condition = FOO.hasOneOfValues(setOf("val1", "val2"))
    assertThat(condition).isInstanceOf(VariableValueOneOfGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.values).containsExactlyInAnyOrder("val1", "val2")
  }

  @Test
  fun `kotlin should construct hasOneOfValues local condition`() {
    val condition = FOO.hasOneOfValuesLocal(setOf("valLocal1", "valLocal2"))
    assertThat(condition).isInstanceOf(VariableValueOneOfGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.values).containsExactlyInAnyOrder("valLocal1", "valLocal2")
  }

  @Test
  fun `kotlin should construct matches condition`() {
    val condition = FOO.matches { it == "special_val" }
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
  }

  @Test
  fun `kotlin should construct matches local condition`() {
    val condition = FOO.matchesLocal { it == "special_val_local" }
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val_local"))).isEmpty()
  }

  @Test
  fun `kotlin should construct matchesRegex condition`() {
    val condition = FOO.matchesRegex(Regex("^special.*" ))
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for variable '${FOO.name}'")
  }

  @Test
  fun `kotlin should construct matchesRegex local condition`() {
    val condition = FOO.matchesRegexLocal(Regex("^special.*" ))
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for local variable '${FOO.name}'")
  }

  @Test
  fun `kotlin should construct matchesRegex with regexDisplayName condition`() {
    val condition = FOO.matchesRegex(Regex("^special.*") ,"special")
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for variable '${FOO.name}'")
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
  }

  @Test
  fun `kotlin should construct matchesRegex with regexDisplayName local condition`() {
    val condition = FOO.matchesRegexLocal(Regex("^special.*") ,"special")
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for local variable '${FOO.name}'")
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
  }

  @Test
  fun `kotlin should construct matchesRegex E-Mail condition`() {
    val condition = EMAIL_VARIABLE.isEmail()
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(EMAIL_VARIABLE)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for variable '${EMAIL_VARIABLE.name}'")
    assertThat(condition.evaluate(Optional.of(EMAIL))).isEmpty()
  }

  @Test
  fun `kotlin should construct matchesRegex E-Mail local condition`() {
    val condition = EMAIL_VARIABLE.isEmailLocal()
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(EMAIL_VARIABLE)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for local variable '${EMAIL_VARIABLE.name}'")
    assertThat(condition.evaluate(Optional.of(EMAIL))).isEmpty()
  }

  @Test
  fun `kotlin should construct matchesRegex UUID condition`() {
    val condition = UUID_VARIABLE.isUuid()
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(UUID_VARIABLE)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for variable '${UUID_VARIABLE.name}'")
    assertThat(condition.evaluate(Optional.of(MYUUID))).isEmpty()
  }

  @Test
  fun `kotlin should construct matchesRegex UUID local condition`() {
    val condition = UUID_VARIABLE.isUuidLocal()
    assertThat(condition).isInstanceOf(VariableMatchesRegexGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(UUID_VARIABLE)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.toString()).isEqualTo("MatchesRegex condition for local variable '${UUID_VARIABLE.name}'")
    assertThat(condition.evaluate(Optional.of(MYUUID))).isEmpty()
  }

}
