package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.hasOneOfValues
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.hasValue
import io.holunda.camunda.bpm.data.guard.GuardViolation
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.VariablesGuard.Companion.ALL
import io.holunda.camunda.bpm.data.guard.VariablesGuard.Companion.ONE_OF
import org.assertj.core.api.Assertions.assertThat
import org.camunda.community.mockito.delegate.DelegateExecutionFake
import org.junit.jupiter.api.Test
import java.util.*

class VariablesGuardTest {

  private val FOO = stringVariable("foo")
  private val BAR = stringVariable("bar")
  private val c1 = exists(FOO)
  private val c2 = hasValue(FOO, "bar")
  private val c3 = hasOneOfValues(FOO, setOf("bar", "baz"))
  private val c4 = exists(BAR)

  @Test
  fun equals() {
    val g1 = VariablesGuard(c1)
    val g1Explicit = VariablesGuard(null, listOf(c1), ALL)
    val g2 = VariablesGuard(listOf(c2, c3))
    val g3 = VariablesGuard(listOf(c2, c3))

    assertThat(g1).isNotEqualTo(g2)
    assertThat(g1).isEqualTo(g1Explicit)

    assertThat(g2).isNotSameAs(g3)
    assertThat(g2).isEqualTo(g3)
  }

  @Test
  fun fromExisting() {
    val g1 = VariablesGuard.EMPTY
    val g2 = g1.fromExisting(c1).fromExisting(c2).fromExisting(c3)
    assertThat(g2).isEqualTo(VariablesGuard(listOf(c1, c2, c3)))
  }

  @Test
  fun shouldUseNameInToString() {
    assertThat(VariablesGuard("MyVariablesGuard", listOf(exists(FOO))).toString()).startsWith("VariablesGuard[MyVariablesGuard](")
    assertThat(VariablesGuard(listOf(exists(FOO))).toString()).startsWith("VariablesGuard(")
    assertThat(VariablesGuard("MyVariablesGuard", exists(FOO)).toString()).startsWith("VariablesGuard[MyVariablesGuard](")
    assertThat(VariablesGuard(exists(FOO)).toString()).startsWith("VariablesGuard(")
  }

  @Test
  fun shouldEvaluateOneOf() {
    val executionWithBoth = DelegateExecutionFake()
      .withVariable(FOO.name, "foo")
      .withVariable(BAR.name, "bar")


    val executionWithBAR = DelegateExecutionFake()
      .withVariable(BAR.name, "bar")

    val executionWithFOO = DelegateExecutionFake()
      .withVariable(FOO.name, "foo")

    val emptyExecution = DelegateExecutionFake()

    val g1 = VariablesGuard(name = "One Of", variableConditions = listOf(c1, c4), reduceOperator = ONE_OF)

    assertThat(g1.evaluate(executionWithBoth)).isEmpty()
    assertThat(g1.evaluate(executionWithBAR)).isEmpty()
    assertThat(g1.evaluate(executionWithFOO)).isEmpty()

    assertThat(g1.evaluate(emptyExecution)).containsExactlyInAnyOrder(
      GuardViolation(c1, Optional.empty(), "Expecting variable '${c1.variableFactory.name}' to be set, but it was not found."),
      GuardViolation(c4, Optional.empty(), "Expecting variable '${c4.variableFactory.name}' to be set, but it was not found."),
    )
  }

  @Test
  fun shouldEvaluateAll() {
    val executionWithBoth = DelegateExecutionFake()
      .withVariable(FOO.name, "foo")
      .withVariable(BAR.name, "bar")

    val executionWithFOO = DelegateExecutionFake()
      .withVariable(FOO.name, "foo")

    val emptyExecution = DelegateExecutionFake()

    val g1 = VariablesGuard(name = "All", variableConditions = listOf(c1, c4), reduceOperator = ALL)


    assertThat(g1.evaluate(executionWithBoth)).isEmpty()

    assertThat(g1.evaluate(executionWithFOO)).containsExactlyInAnyOrder(
      GuardViolation(c4, Optional.empty(), "Expecting variable '${c4.variableFactory.name}' to be set, but it was not found."),
    )

    assertThat(g1.evaluate(emptyExecution)).containsExactlyInAnyOrder(
      GuardViolation(c1, Optional.empty(), "Expecting variable '${c1.variableFactory.name}' to be set, but it was not found."),
      GuardViolation(c4, Optional.empty(), "Expecting variable '${c4.variableFactory.name}' to be set, but it was not found."),
    )
  }

}
