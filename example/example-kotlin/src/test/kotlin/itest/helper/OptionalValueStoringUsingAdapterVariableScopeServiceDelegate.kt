package io.holunda.camunda.bpm.data.itest.helper

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import java.util.HashMap

@Component("OptionalValueStoringUsingAdapterVariableScopeServiceDelegate")
class OptionalValueStoringUsingAdapterVariableScopeServiceDelegate : JavaDelegate {

  val vars = HashMap<String, Any>()

  override fun execute(delegateExecution: DelegateExecution) {
    vars[CamundaBpmDataITestBase.STRING_VAR.name] = CamundaBpmDataITestBase.STRING_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.DATE_VAR.name] = CamundaBpmDataITestBase.DATE_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.SHORT_VAR.name] = CamundaBpmDataITestBase.SHORT_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.INT_VAR.name] = CamundaBpmDataITestBase.INT_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.LONG_VAR.name] = CamundaBpmDataITestBase.LONG_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.DOUBLE_VAR.name] = CamundaBpmDataITestBase.DOUBLE_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.BOOLEAN_VAR.name] = CamundaBpmDataITestBase.BOOLEAN_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.COMPLEX_VAR.name] = CamundaBpmDataITestBase.COMPLEX_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.LIST_STRING_VAR.name] = CamundaBpmDataITestBase.LIST_STRING_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.SET_STRING_VAR.name] = CamundaBpmDataITestBase.SET_STRING_VAR.from(delegateExecution).optional
    vars[CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.name] = CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.from(delegateExecution).optional
  }
}
