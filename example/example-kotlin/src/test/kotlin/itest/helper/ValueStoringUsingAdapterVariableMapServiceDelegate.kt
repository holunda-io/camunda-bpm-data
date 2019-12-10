package io.holunda.camunda.bpm.data.itest.helper

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import java.util.HashMap

@Component("ValueStoringUsingAdapterVariableMapServiceDelegate")
class ValueStoringUsingAdapterVariableMapServiceDelegate : JavaDelegate {

  val vars = HashMap<String, Any>()

  override fun execute(delegateExecution: DelegateExecution) {
    val variableMap = delegateExecution.variablesTyped
    vars[CamundaBpmDataITestBase.STRING_VAR.name] = CamundaBpmDataITestBase.STRING_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.DATE_VAR.name] = CamundaBpmDataITestBase.DATE_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.SHORT_VAR.name] = CamundaBpmDataITestBase.SHORT_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.INT_VAR.name] = CamundaBpmDataITestBase.INT_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.LONG_VAR.name] = CamundaBpmDataITestBase.LONG_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.DOUBLE_VAR.name] = CamundaBpmDataITestBase.DOUBLE_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.BOOLEAN_VAR.name] = CamundaBpmDataITestBase.BOOLEAN_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.COMPLEX_VAR.name] = CamundaBpmDataITestBase.COMPLEX_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.LIST_STRING_VAR.name] = CamundaBpmDataITestBase.LIST_STRING_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.SET_STRING_VAR.name] = CamundaBpmDataITestBase.SET_STRING_VAR.from(variableMap).get()
    vars[CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.name] = CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.from(variableMap).get()
  }
}
