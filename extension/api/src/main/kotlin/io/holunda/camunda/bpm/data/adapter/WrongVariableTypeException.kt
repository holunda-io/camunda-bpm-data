package io.holunda.camunda.bpm.data.adapter

/**
 * Exception indicating that a variable has a different type than requested.
 * @param reason reason.
 */
class WrongVariableTypeException(reason: String?) : RuntimeException(reason)
