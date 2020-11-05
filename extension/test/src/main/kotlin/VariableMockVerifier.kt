package io.holunda.camunda.bpm.data.mockito

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.Variables
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import java.util.Date

/**
 * Verifier for a mocked variable.
 * Provides methods for easy verification.
 */
class VariableMockVerifier {

  /**
   * Verifies if the variable has been set globally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param value value to set.
   * @param T type of variable.
   */
  fun <T> verifyVariableSet(variableScope: VariableScope, variable: VariableFactory<T>, value: T) {
    when (value) {
      is Boolean -> verify(variableScope).setVariable(variable.name, Variables.booleanValue(value as Boolean))
      is Long -> verify(variableScope).setVariable(variable.name, Variables.longValue(value as Long))
      is String -> verify(variableScope).setVariable(variable.name, Variables.stringValue(value as String))
      is Date -> verify(variableScope).setVariable(variable.name, Variables.dateValue(value as Date))
      else -> verify(variableScope).setVariable(variable.name, Variables.untypedValue(value))
    }
  }

  /**
   * Verifies if the variable has NOT been set globally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param value value to set.
   * @param T type of variable.
   */
  fun <T> verifyVariableNotSet(variableScope: VariableScope, variable: VariableFactory<T>, value: T) {
    when (value) {
      is Boolean -> verify(variableScope, never()).setVariable(variable.name, Variables.booleanValue(value as Boolean))
      is Long -> verify(variableScope, never()).setVariable(variable.name, Variables.longValue(value as Long))
      is String -> verify(variableScope, never()).setVariable(variable.name, Variables.stringValue(value as String))
      is Date -> verify(variableScope, never()).setVariable(variable.name, Variables.dateValue(value as Date))
      else -> verify(variableScope, never()).setVariable(variable.name, Variables.untypedValue(value))
    }
  }

  /**
   * Verifies if the variable has been set locally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param value value to set.
   * @param T type of variable.
   */
  fun <T> verifyVariableSetLocal(variableScope: VariableScope, variable: VariableFactory<T>, value: T) {
    when (value) {
      is Boolean -> verify(variableScope).setVariableLocal(variable.name, Variables.booleanValue(value as Boolean))
      is Long -> verify(variableScope).setVariableLocal(variable.name, Variables.longValue(value as Long))
      is String -> verify(variableScope).setVariableLocal(variable.name, Variables.stringValue(value as String))
      is Date -> verify(variableScope).setVariableLocal(variable.name, Variables.dateValue(value as Date))
      else -> verify(variableScope).setVariableLocal(variable.name, Variables.untypedValue(value))
    }
  }

  /**
   * Verifies if the variable has NOT been set locally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param value value to set.
   * @param T type of variable.
   */
  fun <T> verifyVariableNotSetLocal(variableScope: VariableScope, variable: VariableFactory<T>, value: T) {
    when (value) {
      is Boolean -> verify(variableScope, never()).setVariableLocal(variable.name, Variables.booleanValue(value as Boolean))
      is Long -> verify(variableScope, never()).setVariableLocal(variable.name, Variables.longValue(value as Long))
      is String -> verify(variableScope, never()).setVariableLocal(variable.name, Variables.stringValue(value as String))
      is Date -> verify(variableScope, never()).setVariableLocal(variable.name, Variables.dateValue(value as Date))
      else -> verify(variableScope, never()).setVariableLocal(variable.name, Variables.untypedValue(value))
    }
  }

  /**
   * Verifies if the variable has been read globally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableGet(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope).getVariable(variable.name)
  }

  /**
   * Verifies if the variable has NOT been read globally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableNotGet(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope, never()).getVariable(variable.name)
  }


  /**
   * Verifies if the variable has been read locally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableGetLocal(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope).getVariableLocal(variable.name)
  }

  /**
   * Verifies if the variable has NOT been read locally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableNotGetLocal(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope, never()).getVariableLocal(variable.name)
  }

  /**
   * Verifies if the variable has been removed globally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableRemoved(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope).removeVariable(variable.name)
  }

  /**
   * Verifies if the variable has NOT been removed globally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableNotRemoved(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope, never()).removeVariable(variable.name)
  }


  /**
   * Verifies if the variable has been removed locally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableRemovedLocal(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope).removeVariableLocal(variable.name)
  }

  /**
   * Verifies if the variable has NOT been removed locally.
   * @param variableScope scope the variable is defined in
   * @param variable factory defining the variable.
   * @param T type of variable.
   */
  fun <T> verifyVariableNotRemovedLocal(variableScope: VariableScope, variable: VariableFactory<T>) {
    verify(variableScope, never()).removeVariableLocal(variable.name)
  }
}
