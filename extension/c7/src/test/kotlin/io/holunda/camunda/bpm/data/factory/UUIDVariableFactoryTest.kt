package io.holunda.camunda.bpm.data.factory

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.reader.VariableMapReader
import io.holunda.camunda.bpm.data.writer.VariableMapWriter
import org.assertj.core.api.Assertions
import org.camunda.bpm.engine.variable.Variables
import org.junit.jupiter.api.Test
import java.util.*

class UUIDVariableFactoryTest {

  private val variables = Variables.createVariables()

  @Test
  fun readWriteUuid() {
    val anUuid = UUID.randomUUID()
    val uuidVariable = CamundaBpmData.uuidVariable("myUuid")

    VariableMapWriter.of(uuidVariable, variables).set(anUuid)
    val result: UUID = VariableMapReader.of(uuidVariable, variables).get()
    Assertions.assertThat(result).isEqualTo(anUuid)
  }
}
