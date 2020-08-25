package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.condition.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import java.util.function.Function

class CamundaBpmDataGuardsTest {

  val FOO = stringVariable("foo")

  @Test
  fun `should construct exists condition`() {
    val condition = CamundaBpmDataGuards.exists(FOO)
    assertThat(condition).isInstanceOf(VariableExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
  }

  @Test
  fun `should construct exists local condition`() {
    val condition = CamundaBpmDataGuards.exists(FOO, true)
    assertThat(condition).isInstanceOf(VariableExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
  }

  @Test
  fun `should construct notExists condition`() {
    val condition = CamundaBpmDataGuards.notExists(FOO)
    assertThat(condition).isInstanceOf(VariableNotExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
  }

  @Test
  fun `should construct notExists local condition`() {
    val condition = CamundaBpmDataGuards.notExists(FOO, true)
    assertThat(condition).isInstanceOf(VariableNotExistsGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
  }

  @Test
  fun `should construct hasValue condition`() {
    val condition = CamundaBpmDataGuards.hasValue(FOO, "val")
    assertThat(condition).isInstanceOf(VariableValueGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.value).isEqualTo("val")
  }

  @Test
  fun `should construct hasValue local condition`() {
    val condition = CamundaBpmDataGuards.hasValue(FOO, "valLocal", true)
    assertThat(condition).isInstanceOf(VariableValueGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.value).isEqualTo("valLocal")
  }

  @Test
  fun `should construct hasOneOfValues condition`() {
    val condition = CamundaBpmDataGuards.hasOneOfValues(FOO, setOf("val1", "val2"))
    assertThat(condition).isInstanceOf(VariableValueOneOfGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.values).containsExactlyInAnyOrder("val1", "val2")
  }

  @Test
  fun `should construct hasOneOfValues local condition`() {
    val condition = CamundaBpmDataGuards.hasOneOfValues(FOO, setOf("valLocal1", "valLocal2"), true)
    assertThat(condition).isInstanceOf(VariableValueOneOfGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.values).containsExactlyInAnyOrder("valLocal1", "valLocal2")
  }

  @Test
  fun `should construct matches condition`() {
    val condition = CamundaBpmDataGuards.matches(FOO, Function<String, Boolean> { it == "special_val" })
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(false)
    assertThat(condition.evaluate(Optional.of("special_val"))).isEmpty()
  }

  @Test
  fun `should construct matches local condition`() {
    val condition = CamundaBpmDataGuards.matches(FOO, Function<String, Boolean> { it == "special_val_local" }, true)
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val_local"))).isEmpty()
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
    val condition = FOO.exists(true)
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
    val condition = FOO.notExists(true)
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
    val condition = FOO.hasValue("valLocal", true)
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
    val condition = FOO.hasOneOfValues(setOf("valLocal1", "valLocal2"), true)
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
    val condition = FOO.matches(true) { it == "special_val_local" }
    assertThat(condition).isInstanceOf(VariableMatchesGuardCondition::class.java)
    assertThat(condition.variableFactory).isEqualTo(FOO)
    assertThat(condition.local).isEqualTo(true)
    assertThat(condition.evaluate(Optional.of("special_val_local"))).isEmpty()
  }

}
