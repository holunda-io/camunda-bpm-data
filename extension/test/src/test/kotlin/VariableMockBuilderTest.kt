package io.holunda.camunda.bpm.data.mockito

import io.holunda.camunda.bpm.data.CamundaBpmData
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class VariableMockBuilderTest {

  private val variableMockBuilder = VariableMockBuilder()
  private val execution = mock(DelegateExecution::class.java)

  @Test
  fun `should set a variable globally`() {
    // given
    val variable = CamundaBpmData.stringVariable("stringVariable")

    // when
    variableMockBuilder.set(execution, variable, "global value")

    // then
    assertThat(variable.from(execution).get()).isEqualTo("global value")
  }

  @Test
  fun `should set a variable locally`() {
    // given
    val variable = CamundaBpmData.stringVariable("stringVariable")

    // when
    variableMockBuilder.setLocal(execution, variable, "local Value")

    // then
    val value = variable.from(execution).local
    assertThat(value).isEqualTo("local Value")
  }
}
