package io.holunda.camunda.bpm.data.guard.contract

import io.holunda.camunda.bpm.data.guard.VariablesGuard

interface PostCondition {
  val postCondition: VariablesGuard
}
