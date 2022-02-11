package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.OneOfVariablesGuard
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.hasOneOfValues
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.hasValue
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake
import org.junit.Test

class OneOfVariablesGuardTest {

  private val FOO = stringVariable("foo")
  private val BAR = stringVariable("bar")
  private val c1 = exists(FOO)
  private val c2 = hasValue(FOO, "bar")
  private val c3 = hasOneOfValues(FOO, setOf("bar", "baz"))
  private val c4 = exists(BAR)

  @Test
  fun equals() {
    val g1 = OneOfVariablesGuard(c1)
    val g2 = OneOfVariablesGuard(listOf(c2, c3))
    val g3 = OneOfVariablesGuard(listOf(c2, c3))

    assertThat(g1).isNotEqualTo(g2)

    assertThat(g2).isNotSameAs(g3)
    assertThat(g2).isEqualTo(g3)
  }

  @Test
  fun fromExisting() {
    val g1 = OneOfVariablesGuard.EMPTY
    val g2 = g1.fromExisting(c1).fromExisting(c2).fromExisting(c3)
    assertThat(g2).isEqualTo(OneOfVariablesGuard(listOf(c1, c2, c3)))
  }

  @Test
  fun shouldUseNameInToString() {
    assertThat(OneOfVariablesGuard("MyVariablesGuard", listOf(exists(FOO))).toString()).startsWith("VariablesGuard[MyVariablesGuard](")
    assertThat(OneOfVariablesGuard(listOf(exists(FOO))).toString()).startsWith("VariablesGuard(")
    assertThat(OneOfVariablesGuard("MyVariablesGuard", exists(FOO)).toString()).startsWith("VariablesGuard[MyVariablesGuard](")
    assertThat(OneOfVariablesGuard(exists(FOO)).toString()).startsWith("VariablesGuard(")
  }

  @Test
  fun shouldEvaluate() {
    val executionWithBoth = DelegateExecutionFake()
      .withVariable(FOO.name, "foo")
      .withVariable(BAR.name, "bar")


    val executionWithBAR = DelegateExecutionFake()
      .withVariable(BAR.name, "bar")

    val executionWithFOO = DelegateExecutionFake()
      .withVariable(FOO.name, "foo")

    val emptyExecution = DelegateExecutionFake()

    val g1 = OneOfVariablesGuard(listOf(c1, c4))

    assertThat(g1.evaluate(executionWithBoth)).isEmpty()
    assertThat(g1.evaluate(executionWithBAR)).isEmpty()
    assertThat(g1.evaluate(executionWithFOO)).isEmpty()
    assertThat(g1.evaluate(emptyExecution)).isEmpty()
  }


}
