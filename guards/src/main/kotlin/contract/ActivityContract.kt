package io.holunda.camunda.bpm.data.guard.contract

import io.holunda.camunda.bpm.data.guard.VariablesGuard

class ActivityContract(
  override val preCondition: VariablesGuard = VariablesGuard(listOf()),
  override val postCondition: VariablesGuard = VariablesGuard(listOf())
) : PreCondition, PostCondition {

  val preConditionVariables = preCondition.getVariables()
  val preConditionLocalVariables = preCondition.getLocalVariables()
  val postConditionVariables = postCondition.getVariables()
  val postConditionLocalVariables = postCondition.getLocalVariables()
}
