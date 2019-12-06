package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.BOOLEAN_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.COMPLEX_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.DATE_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.DOUBLE_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.INT_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.LIST_STRING_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.LONG_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.MAP_STRING_DATE_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.SET_STRING_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.SHORT_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.STRING_VAR
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
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

@Component("ValueStoringUsingAdapterVariableMapServiceDelegate")
class ValueStoringUsingAdapterVariableMapServiceDelegate : JavaDelegate {

  val vars =  HashMap<String, Any>()

  override fun execute(delegateExecution: DelegateExecution) {
    val variableMap = delegateExecution.variablesTyped
    vars[STRING_VAR.name] = STRING_VAR.from(variableMap).get()
    vars[DATE_VAR.name] = DATE_VAR.from(variableMap).get()
    vars[SHORT_VAR.name] = SHORT_VAR.from(variableMap).get()
    vars[INT_VAR.name] = INT_VAR.from(variableMap).get()
    vars[LONG_VAR.name] = LONG_VAR.from(variableMap).get()
    vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(variableMap).get()
    vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(variableMap).get()
    vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(variableMap).get()
    vars[LIST_STRING_VAR.name] = LIST_STRING_VAR.from(variableMap).get()
    vars[SET_STRING_VAR.name] = SET_STRING_VAR.from(variableMap).get()
    vars[MAP_STRING_DATE_VAR.name] = MAP_STRING_DATE_VAR.from(variableMap).get()
  }
}

