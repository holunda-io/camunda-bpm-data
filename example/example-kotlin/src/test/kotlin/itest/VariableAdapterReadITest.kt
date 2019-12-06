package io.holunda.camunda.bpm.data.itest

import com.fasterxml.jackson.annotation.JsonIgnore
import io.holunda.camunda.bpm.data.CamundaBpmData.*
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.BOOLEAN_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.COMPLEX_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.DATE_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.DOUBLE_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.INT_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.LONG_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.SHORT_VAR
import io.holunda.camunda.bpm.data.itest.VariableAdapterReadITest.Companion.STRING_VAR
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

class VariableAdapterReadITest : CamundaBpmDataITestBase() {

  companion object {
    val STRING_VAR = stringVariable("String Variable")
    val DATE_VAR = dateVariable("Date Variable")
    val SHORT_VAR = shortVariable("Short Variable")
    val INT_VAR = intVariable("Int Variable")
    val LONG_VAR = longVariable("Long Variable")
    val DOUBLE_VAR = doubleVariable("Double Variable")
    val BOOLEAN_VAR = booleanVariable("Boolean Variable")
    val COMPLEX_VAR = customVariable("Complex Variable", AnotherComplexDataStructure::class.java)
  }

  @Autowired
  lateinit var valueStoringUsingAdapterServiceDelegate: ValueStoringUsingAdapterServiceDelegate

  @Test
  fun `should write to variables-map and read using adapter`() {

    val date = Date.from(Instant.now())
    val complexValue = AnotherComplexDataStructure("string", 17, date)
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
    STRING_VAR.from(delegateExecution)
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


data class AnotherComplexDataStructure(
  val stringValue: String,
  val intValue: Int,
  val dateValue: Date
) {
  @JsonIgnore
  private val valueToIgnore: String = "some hidden value"
}
