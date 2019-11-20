package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.CamundaBpmData.*
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

class VariableAdapterITest: CamundaBpmDataITestBase() {

  companion object {
    val STRING_VAR = stringVariable("String Variable")
    val DATE_VAR = dateVariable("Date Variable")
    val SHORT_VAR = shortVariable("Short Variable")
    val INT_VAR = intVariable("Int Variable")
    val LONG_VAR = longVariable("Long Variable")
    val DOUBLE_VAR = doubleVariable("Double Variable")
    val BOOLEAN_VAR = booleanVariable("Boolean Variable")
  }

  @Autowired
  lateinit var valueStoringServiceDelegate: ValueStoringServiceDelegate

  @Test
  fun `should write to map and read from variable scope`() {

    val date = Date.from(Instant.now())

    val variables = createVariables()
    STRING_VAR.on(variables).set("value")
    DATE_VAR.on(variables).set(date)
    SHORT_VAR.on(variables).set(11)
    INT_VAR.on(variables).set(123)
    LONG_VAR.on(variables).set(812L)
    DOUBLE_VAR.on(variables).set(12.0)
    BOOLEAN_VAR.on(variables).set(true)

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${myDelegate}")
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
        BOOLEAN_VAR to true
      )
  }
}

@Component("myDelegate")
class ValueStoringServiceDelegate : JavaDelegate {

  lateinit var vars: VariableMap

  override fun execute(delegateExecution: DelegateExecution) {
    vars = delegateExecution.variablesTyped
  }
}

