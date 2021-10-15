package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import io.holunda.camunda.bpm.data.guard.condition.VariableMatchesRegexGuardCondition.Companion.EMAIL_REGEX
import io.holunda.camunda.bpm.data.guard.condition.VariableMatchesRegexGuardCondition.Companion.EMAIL_REGEX_DISPLAY_NAME
import io.holunda.camunda.bpm.data.guard.condition.VariableMatchesRegexGuardCondition.Companion.UUID_REGEX
import io.holunda.camunda.bpm.data.guard.condition.VariableMatchesRegexGuardCondition.Companion.UUID_REGEX_DISPLAY_NAME
import java.util.*

/**
 * Condition to check if the variable matches a regex.
 * @constructor Creates a condition.
 * @param T variable type.
 * @param variableFactory Factory to work with.
 * @param local flag indicating the variable scope (global/local). Defaults to global.
 * @property regex regex specifying the condition.
 * @property regexDisplayName regexName display name for the regex.
 */
class VariableMatchesRegexGuardCondition<T>(
  /**
   * Variable factory to work with.
   */
  variableFactory: VariableFactory<T>,
  local: Boolean = false,
  private val regex: Regex,
  private val regexDisplayName: String = regex.toString()
) : VariableGuardCondition<T>(variableFactory, local) {

  companion object {
    const val EMAIL_REGEX_DISPLAY_NAME = "E-Mail"
    const val EMAIL_REGEX = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    const val UUID_REGEX_DISPLAY_NAME = "UUID"
    const val UUID_REGEX = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$"
  }

  private val existsCondition = VariableExistsGuardCondition(variableFactory, local)

  override fun evaluate(option: Optional<T>): List<GuardViolation<T>> {
    val violations = existsCondition.evaluate(option).toMutableList()
    if (option.isPresent && !regex.matches(option.get().toString())) {
      violations.add(
        GuardViolation(
          condition = this,
          option = option,
          message = "Expecting$localLabel variable '${variableFactory.name}' to match the regex '${regexDisplayName}', but its value '${option.get()}' has not."
        )
      )
    }

    return violations
  }

  override fun toString(): String {
    return "MatchesRegex condition for$localLabel variable '${super.variableFactory.name}'"
  }
}

/**
 * Creation extension for the condition.
 * @param regex regex that must be matched.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matchesRegex(regex: Regex) = VariableMatchesRegexGuardCondition(this, false, regex)

/**
 * Creation extension for the condition.
 * @param regex regex that must be matched.
 * @param regexDisplayName regexName display name for the regex.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matchesRegex(regex: Regex, regexDisplayName: String) =
  VariableMatchesRegexGuardCondition(this, false, regex, regexDisplayName)

/**
 * Creation extension for the local condition.
 * @param regex regex that must be matched.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matchesRegexLocal(regex: Regex) = VariableMatchesRegexGuardCondition(this, true, regex)

/**
 * Creation extension for the local condition.
 * @param regex regex that must be matched.
 * @param regexDisplayName regexName display name for the regex.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.matchesRegexLocal(regex: Regex, regexDisplayName: String) =
  VariableMatchesRegexGuardCondition(this, true, regex, regexDisplayName)

/**
 * Creation extension for an Email matching regex condition.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isEmail() = VariableMatchesRegexGuardCondition(this, false, Regex(EMAIL_REGEX), EMAIL_REGEX_DISPLAY_NAME)

/**
 * Creation extension for an Email matching regex local condition.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isEmailLocal() = VariableMatchesRegexGuardCondition(this, true, Regex(EMAIL_REGEX), EMAIL_REGEX_DISPLAY_NAME)

/**
 * Creation extension for an UUID matching regex condition.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isUuid() = VariableMatchesRegexGuardCondition(this, false, Regex(UUID_REGEX), UUID_REGEX_DISPLAY_NAME)

/**
 * Creation extension for an UUID matching regex local condition.
 * @return instance of [VariableMatchesRegexGuardCondition] on current factory.
 */
fun <T> VariableFactory<T>.isUuidLocal() = VariableMatchesRegexGuardCondition(this, true, Regex(UUID_REGEX), UUID_REGEX_DISPLAY_NAME)
