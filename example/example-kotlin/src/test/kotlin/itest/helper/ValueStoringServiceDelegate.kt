package io.holunda.camunda.bpm.data.itest.helper

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.VariableMap
import org.springframework.stereotype.Component

@Component("ValueStoringServiceDelegate")
class ValueStoringServiceDelegate : JavaDelegate {

  lateinit var vars: VariableMap

  override fun execute(delegateExecution: DelegateExecution) {
    vars = delegateExecution.variablesTyped
  }
}
