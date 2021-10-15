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
   */
  @JvmStatic
  fun <T> exists(variableFactory: VariableFactory<T>) = variableFactory.exists()

  /**
   * Creates exists local condition.
   * @param variableFactory factory to work on.
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

  /**
   * Creates matches regex local condition.
   * @param variableFactory factory to work on.
   * @param regex a regex to check the variable value.
   */
  @JvmStatic
  fun <T> matchesRegexLocal(variableFactory: VariableFactory<T>, regex: String) = variableFactory.matchesRegexLocal(Regex(regex))

  /**
   * Creates matches regex Local condition.
   * @param variableFactory factory to work on.
   * @param regex a regex to check the variable value.
   * @param regexDisplayName a display name representing the regex.
   */
  @JvmStatic
  fun <T> matchesRegexLocal(variableFactory: VariableFactory<T>, regex: String, regexDisplayName: String) =
    variableFactory.matchesRegexLocal(Regex(regex), regexDisplayName)

  /**
   * Creates matches regex condition.
   * @param variableFactory factory to work on.
   * @param regex a regex to check the variable value.
   */
  @JvmStatic
  fun <T> matchesRegex(variableFactory: VariableFactory<T>, regex: String) = variableFactory.matchesRegex(Regex(regex))

  /**
   * Creates matches regex condition.
   * @param variableFactory factory to work on.
   * @param regex a regex to check the variable value.
   * @param regexDisplayName a display name representing the regex.
   */
  @JvmStatic
  fun <T> matchesRegex(variableFactory: VariableFactory<T>, regex: String, regexDisplayName: String) =
    variableFactory.matchesRegex(Regex(regex), regexDisplayName)

  /**
   * Creates matches E-Mail regex local condition.
   * @param variableFactory factory to work on.
   */
  @JvmStatic
  fun <T> isEmailLocal(variableFactory: VariableFactory<T>) = variableFactory.isEmailLocal()

  /**
   * Creates matches E-Mail regex condition.
   * @param variableFactory factory to work on.
   */
  @JvmStatic
  fun <T> isEmail(variableFactory: VariableFactory<T>) = variableFactory.isEmail()

  /**
   * Creates matches UUID regex local condition.
   * @param variableFactory factory to work on.
   */
  @JvmStatic
  fun <T> isUuidLocal(variableFactory: VariableFactory<T>) = variableFactory.isUuidLocal()

  /**
   * Creates matches UUID regex condition.
   * @param variableFactory factory to work on.
   */
  @JvmStatic
  fun <T> isUuid(variableFactory: VariableFactory<T>) = variableFactory.isUuid()

}
