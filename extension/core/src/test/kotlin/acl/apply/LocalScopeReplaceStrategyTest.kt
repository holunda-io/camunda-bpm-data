package io.holunda.camunda.bpm.data.acl.apply

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.junit.Test
import org.mockito.Mockito.*

class LocalScopeReplaceStrategyTest {

    val FOO = stringVariable("foo")

    @Test
    fun `should apply local`() {
        val variables = CamundaBpmData.builder().set(FOO, "bar").build()
        val executionMock = mock(DelegateExecution::class.java)

        LocalScopeReplaceStrategy.apply(variables, executionMock)

        verify(executionMock, never()).setVariables(any())
        verify(executionMock).setVariablesLocal(variables)
        verifyNoMoreInteractions(executionMock)
    }
}