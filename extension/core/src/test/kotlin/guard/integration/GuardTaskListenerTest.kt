package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.guard.condition.hasValue
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.extension.mockito.delegate.DelegateTaskFake
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

val ORDER_ID = stringVariable("orderID")

class GuardTaskListenerTest {

    @Suppress("RedundantVisibilityModifier")
    @get: Rule
    public val thrown = ExpectedException.none()

    @Test
    fun `should do nothing`() {
        val delegateTask = DelegateTaskFake().withId("4711")
        ORDER_ID.on(delegateTask).set("1")

        val listener = createListener(true)
        listener.notify(delegateTask)

        // nothing to do here
        assertThat(true).isTrue()
    }

    @Test
    fun `should not throw exception if disabled `() {
        val delegateTask = DelegateTaskFake().withId("4711").withName("task name")
        ORDER_ID.on(delegateTask).set("2")

        val listener = createListener(false)
        listener.notify(delegateTask)

        // nothing to do here
        assertThat(true).isTrue()
    }

    @Test
    fun `should throw exception if enabled `() {
        val delegateTask = DelegateTaskFake().withId("4711").withName("task name")
        ORDER_ID.on(delegateTask).set("2")

        thrown.expectMessage("Guard violated in task '${delegateTask.name}' (taskId: '${delegateTask.id}')")

        val listener = createListener(true)
        listener.notify(delegateTask)

        // nothing to do here
        assertThat(true).isTrue()
    }

    private fun createListener(throwE: Boolean = true) = DefaultGuardTaskListener(listOf(ORDER_ID.hasValue("1")), throwE)
}