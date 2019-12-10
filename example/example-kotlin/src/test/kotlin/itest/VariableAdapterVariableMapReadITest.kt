package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.itest.helper.ValueStoringUsingAdapterVariableMapServiceDelegate
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.*

class VariableAdapterVariableMapReadITest : CamundaBpmDataITestBase() {

  @Autowired
  lateinit var valueStoringUsingAdapterServiceDelegate: ValueStoringUsingAdapterVariableMapServiceDelegate

  @Test
  fun `should write to variables-map and read using adapter`() {

    val date = Date.from(Instant.now())
    val complexValue = ComplexDataStructure("string", 17, date)
    val listOfStrings = listOf("Hello", "World")
    val setOfStrings = setOf("Kermit", "Piggy")
    val map: Map<String, Date> = mapOf("Twelve" to date, "Eleven" to date)
    val variables = createVariables()
      .putValue(STRING_VAR.name, "value")
      .putValue(DATE_VAR.name, date)
      .putValue(SHORT_VAR.name, 11.toShort())
      .putValue(INT_VAR.name, 123)
      .putValue(LONG_VAR.name, 812L)
      .putValue(DOUBLE_VAR.name, 12.0)
      .putValue(BOOLEAN_VAR.name, true)
      .putValue(COMPLEX_VAR.name, complexValue)
      .putValue(LIST_STRING_VAR.name, listOfStrings)
      .putValue(SET_STRING_VAR.name, setOfStrings)
      .putValue(MAP_STRING_DATE_VAR.name, map)

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${ValueStoringUsingAdapterVariableMapServiceDelegate}")
    whenever()
      .process_is_started_with_variables(variables = variables)
    then()
      .variables_had_value(valueStoringUsingAdapterServiceDelegate.vars,
        STRING_VAR to "value",
        DATE_VAR to date,
        SHORT_VAR to 11.toShort(),
        INT_VAR to 123.toInt(),
        LONG_VAR to 812.toLong(),
        DOUBLE_VAR to 12.0.toDouble(),
        BOOLEAN_VAR to true,
        COMPLEX_VAR to complexValue,
        LIST_STRING_VAR to listOfStrings,
        SET_STRING_VAR to setOfStrings,
        MAP_STRING_DATE_VAR to map
      )
  }
}

