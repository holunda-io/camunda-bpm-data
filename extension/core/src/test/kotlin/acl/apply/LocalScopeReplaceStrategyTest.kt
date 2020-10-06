package io.holunda.camunda.bpm.data.acl.apply

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.assertj.core.api.Assertions
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.junit.Test
import org.mockito.Mockito.*

class LocalScopeReplaceStrategyTest {

  private val FOO = stringVariable("foo")

  @Test
  fun `should apply local`() {
    val variables = CamundaBpmData.builder().set(FOO, "bar").build()
    val executionMock = mock(DelegateExecution::class.java)

    LocalScopeReplaceStrategy.apply(variables, executionMock)

    verify(executionMock, never()).variables = any()
    verify(executionMock).variablesLocal = variables
    verifyNoMoreInteractions(executionMock)

    Assertions.assertThat(LocalScopeReplaceStrategy.toString()).isEqualTo(LocalScopeReplaceStrategy::class.java.canonicalName)
  }
}
