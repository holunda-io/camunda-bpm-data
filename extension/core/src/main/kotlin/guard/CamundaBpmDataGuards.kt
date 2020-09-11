package io.holunda.camunda.bpm.data.guard

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.condition.*
import java.util.function.Function

/**
 * Guard creation methods.
 */
@Suppress("unused")
object CamundaBpmDataGuards {

  /**
   * Creates exists condition.
   * @param variableFactory factory to work on.
   * @param local flag indicating scope (global/local).
   */
  @JvmStatic
  fun <T> exists(variableFactory: VariableFactory<T>) = variableFactory.exists()

  /**
   * Creates exists local condition.
   * @param variableFactory factory to work on.
   * @param local flag indicating scope (global/local).
   */
  @JvmStatic
  fun <T> existsLocal(variableFactory: VariableFactory<T>) = variableFactory.existsLocal()

  /**
   * Creates not exists local condition.
   * @param variableFactory factory to work on.
   */
  @JvmStatic
  fun <T> notExistsLocal(variableFactory: VariableFactory<T>) = variableFactory.notExistsLocal()

  /**
   * Creates not exists condition.
   * @param variableFactory factory to work on.
   */
  @JvmStatic
  fun <T> notExists(variableFactory: VariableFactory<T>) = variableFactory.notExists()

  /**
   * Creates has value local condition.
   * @param variableFactory factory to work on.
   * @param value value that the variable must have.
   */
  @JvmStatic
  fun <T> hasValueLocal(variableFactory: VariableFactory<T>, value: T) = variableFactory.hasValueLocal(value)

  /**
   * Creates has value condition.
   * @param variableFactory factory to work on.
   * @param value value that the variable must have.
   */
  @JvmStatic
  fun <T> hasValue(variableFactory: VariableFactory<T>, value: T) = variableFactory.hasValue(value)

  /**
   * Creates has value local condition.
   * @param variableFactory factory to work on.
   * @param values set of values to compare with the variable value.
   */
  @JvmStatic
  fun <T> hasOneOfValuesLocal(variableFactory: VariableFactory<T>, values: Set<T>) = variableFactory.hasOneOfValuesLocal(values)

  /**
   * Creates has value condition.
   * @param variableFactory factory to work on.
   * @param values set of values to compare with the variable value.
   */
  @JvmStatic
  fun <T> hasOneOfValues(variableFactory: VariableFactory<T>, values: Set<T>) = variableFactory.hasOneOfValues(values)

  /**
   * Creates matches local condition.
   * @param variableFactory factory to work on.
   * @param matcher a matcher function to check the variable value.
   */
  @JvmStatic
  fun <T> matchesLocal(variableFactory: VariableFactory<T>,
                  matcher: Function<T, Boolean>) = variableFactory.matchesLocal { matcher.apply(it) }

  /**
   * Creates matches condition.
   * @param variableFactory factory to work on.
   * @param matcher a matcher function to check the variable value.
   */
  @JvmStatic
  fun <T> matches(variableFactory: VariableFactory<T>,
                  matcher: Function<T, Boolean>) = variableFactory.matches { matcher.apply(it) }

}
