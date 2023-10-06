package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.mapVariable
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.mapVariableNotNullable
import io.holunda.camunda.bpm.data.NullableMapTest.TestVariables.NO_NULL_MAP
import io.holunda.camunda.bpm.data.NullableMapTest.TestVariables.NULL_MAP
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.jupiter.api.Test

internal class NullableMapTest {

  object TestVariables {
    val NO_NULL_MAP: VariableFactory<Map<String, Any>> = mapVariableNotNullable("noNullValues")
    val NULL_MAP: VariableFactory<Map<String, Any?>> = mapVariable("mayHaveNullValues")
  }

  @Test
  fun `test null values map as no null value map`() {
    val data = mapOf("first" to 1, "second" to null, "last" to "string")
    val mockExecution = DelegateExecutionFake().withVariables(createVariables().apply {
      putValue(NO_NULL_MAP.name, data)
    })
    // NO_NULL_MAP.on(mockExecution).set(data) // won't compile, wrong type
    val map: Map<String, Any> = NO_NULL_MAP.from(mockExecution).get()
    val second = map.getValue("second")
    assertThat(second).isNull()
  }


  @Test
  fun `test null values map`() {
    val data = mapOf("first" to 1, "second" to null, "last" to "string")
    val mockExecution = DelegateExecutionFake()
    NULL_MAP.on(mockExecution).set(data)
    // val result: Map<String, Any> = NULL_MAP.from(mockExecution).get() // no assignable
    val result: Map<String, Any?> = NULL_MAP.from(mockExecution).get()
    assertThat(result).isEqualTo(data)
    val second = result.getValue("second")
    assertThat(second).isNull()
  }

}
