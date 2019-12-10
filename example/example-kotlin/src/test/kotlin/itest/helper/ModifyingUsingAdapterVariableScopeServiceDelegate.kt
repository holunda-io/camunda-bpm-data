package io.holunda.camunda.bpm.data.itest.helper

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase
import io.holunda.camunda.bpm.data.itest.VariableAdapterWriteITest
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import java.util.HashMap

@Component("ModifyingUsingAdapterVariableScopeServiceDelegate")
class ModifyingUsingAdapterVariableScopeServiceDelegate : JavaDelegate {

  val vars = HashMap<String, Any>()

  override fun execute(delegateExecution: DelegateExecution) {

    CamundaBpmDataITestBase.STRING_VAR.on(delegateExecution).set("value")
    CamundaBpmDataITestBase.DATE_VAR.on(delegateExecution).set(VariableAdapterWriteITest.date)
    CamundaBpmDataITestBase.SHORT_VAR.on(delegateExecution).set(11.toShort())
    CamundaBpmDataITestBase.INT_VAR.on(delegateExecution).set(123.toInt())
    CamundaBpmDataITestBase.LONG_VAR.on(delegateExecution).set(812.toLong())
    CamundaBpmDataITestBase.DOUBLE_VAR.on(delegateExecution).set(12.0.toDouble())
    CamundaBpmDataITestBase.BOOLEAN_VAR.on(delegateExecution).set(true)
    CamundaBpmDataITestBase.COMPLEX_VAR.on(delegateExecution).set(VariableAdapterWriteITest.complexValue)
    CamundaBpmDataITestBase.LIST_STRING_VAR.on(delegateExecution).set(VariableAdapterWriteITest.listOfStrings)
    CamundaBpmDataITestBase.SET_STRING_VAR.on(delegateExecution).set(VariableAdapterWriteITest.setOfStrings)
    CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.on(delegateExecution).set(VariableAdapterWriteITest.map)
  }
}
