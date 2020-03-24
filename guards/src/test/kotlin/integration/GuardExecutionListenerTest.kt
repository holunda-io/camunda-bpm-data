package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.condition.hasValue
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

val ORDER_REFERENCE = stringVariable("orderReference")

class GuardExecutionListenerTest {

    @get: Rule
    public val thrown = ExpectedException.none()

    @Test
    fun `should do nothing`() {
        val delegate = DelegateExecutionFake().withId("4711").withCurrentActivityName("some")
        ORDER_REFERENCE.on(delegate).set("1")

        val listener = MyListener2(true)
        listener.notify(delegate)

        // nothing to do here
        assertThat(true).isTrue()
    }

    @Test
    fun `should not throw exception if disabled `() {
        val delegate = DelegateExecutionFake().withId("4711").withCurrentActivityName("some")
        ORDER_REFERENCE.on(delegate).set("2")

        val listener = MyListener2(false)
        listener.notify(delegate)

        // nothing to do here
        assertThat(true).isTrue()
    }

    @Test
    fun `should throw exception if enabled `() {
        val delegate = DelegateExecutionFake().withId("4711").withCurrentActivityName("some")
        ORDER_REFERENCE.on(delegate).set("2")

        thrown.expectMessage("Guard violated by execution '${delegate.id}' in activity '${delegate.currentActivityName}'")

        val listener = MyListener2(true)
        listener.notify(delegate)

        // nothing to do here
        assertThat(true).isTrue()
    }

    class MyListener2(throwE: Boolean = true) : AbstractGuardExecutionListener(listOf(ORDER_REFERENCE.hasValue("1")), throwE)
}