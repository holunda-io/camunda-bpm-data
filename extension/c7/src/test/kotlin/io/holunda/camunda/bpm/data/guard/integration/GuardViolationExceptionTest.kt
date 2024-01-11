package io.holunda.camunda.bpm.data.guard.integration

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards
import io.holunda.camunda.bpm.data.guard.GuardViolation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class GuardViolationExceptionTest {

  private val FOO = CamundaBpmData.stringVariable("foo")
  private val c1 = CamundaBpmDataGuards.exists(FOO)
  private val c2 = CamundaBpmDataGuards.hasValue(FOO, "bar")

  @Test
  fun buildMessage() {

    val expected = "reason\n\tnot exists,\n\twrong VAL"
    val ex = GuardViolationException(reason = "reason", violations = listOf(
      GuardViolation(c1, Optional.empty(), "not exists"),
      GuardViolation(c2, Optional.of("VAL"), "wrong VAL")
    ))

    assertThat(ex.message).isEqualTo(expected)
  }
}
