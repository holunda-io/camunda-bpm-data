package io.holunda.camunda.bpm.data.guard.contract

import io.holunda.camunda.bpm.data.guard.VariablesGuard

/**
 * Contract containing pre- and post-condition.
 * A condition is empty by default but can specify a list of
 */
open class Contract(
  override val pre: VariablesGuard = VariablesGuard(listOf()),
  override val post: VariablesGuard = VariablesGuard(listOf())
) : PreContract, PostContract {

  val preConditionVariables = pre.getVariables()
  val preConditionLocalVariables = pre.getLocalVariables()
  val postConditionVariables = post.getVariables()
  val postConditionLocalVariables = post.getLocalVariables()
}
