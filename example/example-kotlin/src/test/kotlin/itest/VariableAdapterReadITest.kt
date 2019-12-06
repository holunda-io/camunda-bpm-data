package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.BOOLEAN_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.COMPLEX_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.DATE_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.DOUBLE_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.INT_VAR
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.LONG_VAR
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

class VariableAdapterReadITest : CamundaBpmDataITestBase() {

  @Autowired
  lateinit var valueStoringUsingAdapterServiceDelegate: ValueStoringUsingAdapterServiceDelegate

  @Test
  fun `should write to variables-map and read using adapter`() {

    val date = Date.from(Instant.now())
    val complexValue = ComplexDataStructure("string", 17, date)
    val variables = createVariables()
      .putValue("String Variable", "value")
      .putValue("Date Variable", date)
      .putValue("Short Variable", 11.toShort())
      .putValue("Int Variable", 123)
      .putValue("Long Variable", 812L)
      .putValue("Double Variable", 12.0)
      .putValue("Boolean Variable", true)
      .putValue("Complex Variable", complexValue)

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${testDelegate}")
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
        COMPLEX_VAR to complexValue
      )
  }
}

@Component("testDelegate")
class ValueStoringUsingAdapterServiceDelegate : JavaDelegate {

  val vars =  HashMap<String, Any>()

  override fun execute(delegateExecution: DelegateExecution) {
    vars[STRING_VAR.name] = STRING_VAR.from(delegateExecution).get()
    vars[DATE_VAR.name] = DATE_VAR.from(delegateExecution).get()
    vars[SHORT_VAR.name] = SHORT_VAR.from(delegateExecution).get()
    vars[INT_VAR.name] = INT_VAR.from(delegateExecution).get()
    vars[LONG_VAR.name] = LONG_VAR.from(delegateExecution).get()
    vars[DOUBLE_VAR.name] = DOUBLE_VAR.from(delegateExecution).get()
    vars[BOOLEAN_VAR.name] = BOOLEAN_VAR.from(delegateExecution).get()
    vars[COMPLEX_VAR.name] = COMPLEX_VAR.from(delegateExecution).get()
  }
}

