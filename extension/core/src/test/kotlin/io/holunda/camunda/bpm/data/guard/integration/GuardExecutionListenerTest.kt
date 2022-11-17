package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.exists
import io.holunda.camunda.bpm.data.guard.condition.hasValue
import org.assertj.core.api.Assertions.assertThat
import org.camunda.community.mockito.delegate.DelegateExecutionFake
import org.junit.Assert.assertThrows
import org.junit.Test

val ORDER_REFERENCE = stringVariable("orderReference")

class GuardExecutionListenerTest {

  @Test
  fun `should do nothing`() {
    val execution = DelegateExecutionFake().withId("4711").withCurrentActivityName("some")
    ORDER_REFERENCE.on(execution).set("1")

    val listener = createListener(true)
    listener.notify(execution)

    // nothing to do here
    assertThat(true).isTrue
  }

  @Test
  fun `should not throw exception if disabled `() {
    val execution = DelegateExecutionFake().withId("4711").withCurrentActivityName("some")
    ORDER_REFERENCE.on(execution).set("2")

    val listener = createListener(false)
    listener.notify(execution)

    // nothing to do here
    assertThat(true).isTrue
  }

  @Test
  fun `should throw exception if enabled `() {
    val execution = DelegateExecutionFake().withId("4711").withCurrentActivityName("some")
    ORDER_REFERENCE.on(execution).set("2")

    val listener = createListener(true)
    assertThrows(
      "Guard violated by execution '${execution.id}' in activity '${execution.currentActivityName}'",
      GuardViolationException::class.java
    ) {
      listener.notify(execution)
    }
  }

  @Test
  fun `should print name of named guard`() {
    val execution = DelegateExecutionFake()

    val listener = DefaultGuardExecutionListener(VariablesGuard("NamedGuard", listOf(ORDER_REFERENCE.exists())))
    val exception = assertThrows(GuardViolationException::class.java) {
      listener.notify(execution)
    }
    assertThat(exception.message).startsWith("NamedGuard")
  }

  private fun createListener(throwE: Boolean = true) =
    DefaultGuardExecutionListener(VariablesGuard(listOf(ORDER_REFERENCE.hasValue("1"))), throwE)
}
