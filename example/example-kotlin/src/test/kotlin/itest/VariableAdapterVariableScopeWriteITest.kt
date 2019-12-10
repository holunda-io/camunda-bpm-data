package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.itest.helper.ModifyingUsingAdapterVariableScopeServiceDelegate
import io.holunda.camunda.bpm.data.itest.helper.ValueStoringUsingAdapterVariableScopeServiceDelegate
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.*

class VariableAdapterWriteITest : CamundaBpmDataITestBase() {

  companion object {
    val date = Date.from(Instant.now())
    val complexValue = ComplexDataStructure("string", 17, date)
    val listOfStrings = listOf("Hello", "World")
    val setOfStrings = setOf("Kermit", "Piggy")
    val map: Map<String, Date> = mapOf("Twelve" to date, "Eleven" to date)
  }

  @Autowired
  lateinit var valueStoringUsingAdapterVariableScopeServiceDelegate: ValueStoringUsingAdapterVariableScopeServiceDelegate

  @Autowired
  lateinit var modifyingUsingAdapterVariableScopeServiceDelegate: ModifyingUsingAdapterVariableScopeServiceDelegate

  @Test
  fun `should write to variable scope and read`() {


    val variables = createVariables()

    given()
      .process_with_modifying_delegate_is_deployed(
        modifyingDelegateExpression = "\${ModifyingUsingAdapterVariableScopeServiceDelegate}",
        delegateExpression = "\${ValueStoringUsingAdapterVariableScopeServiceDelegate}"
      )
    whenever()
      .process_is_started_with_variables(variables = variables)
    then()
      .variables_had_value(valueStoringUsingAdapterVariableScopeServiceDelegate.vars,
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


