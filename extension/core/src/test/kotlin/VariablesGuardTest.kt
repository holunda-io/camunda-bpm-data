package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.hasOneOfValues
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.hasValue
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class VariablesGuardTest {

    val FOO = stringVariable("foo")
    val c1 = exists(FOO)
    val c2 = hasValue(FOO, "bar")
    val c3 = hasOneOfValues(FOO, setOf("bar", "baz"))

    @Test
    fun equals() {
        val g1 = VariablesGuard(c1)
        val g2 = VariablesGuard(listOf(c2, c3))
        val g3 = VariablesGuard(listOf(c2, c3))

        assertThat(g1).isNotEqualTo(g2)

        assertThat(g2).isNotSameAs(g3)
        assertThat(g2).isEqualTo(g3)
    }

    @Test
    fun fromExisting() {
        val g1 = VariablesGuard.EMPTY
        val g2 = g1.fromExisting(c1).fromExisting(c2).fromExisting(c3)
        assertThat(g2).isEqualTo(VariablesGuard(listOf(c1, c2, c3)))

    }
}