package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import org.assertj.core.api.Assertions
import org.camunda.bpm.engine.CaseService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

class CaseServiceVariableReaderKTest {
  private val caseService = Mockito.mock(CaseService::class.java)
  private val taskId = UUID.randomUUID().toString()
  private val value = "value"
  private val localValue = "localValue"

  @BeforeEach
  fun mockExecution() {
    Mockito.`when`(caseService.getVariable(taskId, STRING.name)).thenReturn(value)
    Mockito.`when`(caseService.getVariableLocal(taskId, STRING.name)).thenReturn(localValue)
  }

  @AfterEach
  fun after() {
    Mockito.reset(caseService)
  }

  @Test
  fun shouldDelegateGet() {
    Assertions.assertThat(reader(caseService, taskId)[STRING]).isEqualTo(value)
  }

  @Test
  fun shouldDelegateGetOptional() {
    Assertions.assertThat(reader(caseService, taskId).getOptional(STRING)).hasValue(value)
    Assertions.assertThat(reader(caseService, taskId).getOptional(stringVariable("xxx"))).isEmpty()
  }

  @Test
  fun shouldDelegateGetLocalOptional() {
    Assertions.assertThat(reader(caseService, taskId).getLocalOptional(STRING)).hasValue(localValue)
    Assertions.assertThat(reader(caseService, taskId).getLocalOptional(stringVariable("xxx"))).isEmpty()
  }

  @Test
  fun shouldDelegateGetLocal() {
    Assertions.assertThat(reader(caseService, taskId).getLocal(STRING)).isEqualTo(localValue)
  }

  companion object {
    private val STRING = stringVariable("myString")
  }
}
