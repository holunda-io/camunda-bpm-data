package io.holunda.camunda.bpm.data.itest

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

class VariableAdapterVariableMapWriteTest: CamundaBpmDataITestBase() {

  @Autowired
  lateinit var valueStoringServiceDelegate: ValueStoringServiceDelegate

  @Test
  fun `should write to map`() {

    val date = Date.from(Instant.now())
    val complexValue = ComplexDataStructure("string", 17, date)
    val listOfStrings = listOf("Hello", "World")
    val setOfStrings = setOf("Kermit", "Piggy")
    val variables = createVariables()
    STRING_VAR.on(variables).set("value")
    DATE_VAR.on(variables).set(date)
    SHORT_VAR.on(variables).set(11)
    INT_VAR.on(variables).set(123)
    LONG_VAR.on(variables).set(812L)
    DOUBLE_VAR.on(variables).set(12.0)
    BOOLEAN_VAR.on(variables).set(true)
    COMPLEX_VAR.on(variables).set(complexValue)
    LIST_STRING_VAR.on(variables).set(listOfStrings)
    SET_STRING_VAR.on(variables).set(setOfStrings)

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${ValueStoringServiceDelegate}")
    whenever()
      .process_is_started_with_variables(variables = variables)
    then()
      .variables_had_value(valueStoringServiceDelegate.vars,
        STRING_VAR to "value",
        DATE_VAR to date,
        SHORT_VAR to 11.toShort(),
        INT_VAR to 123.toInt(),
        LONG_VAR to 812.toLong(),
        DOUBLE_VAR to 12.0.toDouble(),
        BOOLEAN_VAR to true,
        COMPLEX_VAR to complexValue,
        LIST_STRING_VAR to listOfStrings,
        SET_STRING_VAR to setOfStrings
      )
  }
}

@Component("ValueStoringServiceDelegate")
class ValueStoringServiceDelegate : JavaDelegate {

  lateinit var vars: VariableMap

  override fun execute(delegateExecution: DelegateExecution) {
    vars = delegateExecution.variablesTyped
  }
}

