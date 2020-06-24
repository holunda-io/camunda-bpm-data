package io.holunda.camunda.bpm.data.acl.apply

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class GlobalScopeReplaceStrategyTest {

    val FOO = stringVariable("foo")

    @Test
    fun `should apply global`() {
        val variables = CamundaBpmData.builder().set(FOO, "bar").build()
        val executionMock = Mockito.mock(DelegateExecution::class.java)

        val result = GlobalScopeReplaceStrategy.apply(variables, executionMock)

        verify(executionMock, Mockito.never()).setVariablesLocal(Mockito.any())
        verify(executionMock).setVariables(variables)
        verifyNoMoreInteractions(executionMock)
    }
}