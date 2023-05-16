package io.holunda.camunda.bpm.data.adapter.set

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Read adapter for external task.
 *
 * @param [T] type of value.
 * @param lockedExternalTask locked external client
 * @param variableName name of the variable.
 * @param memberClazz class of the variable.
 */
class SetReadAdapterLockedExternalTask<T>(
  private val lockedExternalTask: LockedExternalTask,
  private val variableName: String,
  private val memberClazz: Class<T>
) : ReadAdapter<Set<T>> {
  override fun get(): Set<T> {
    return getOptional().get()
  }

  override fun getOptional(): Optional<Set<T>> {
    return Optional.ofNullable(getOrNull())
  }

  override fun getLocal(): Set<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<Set<T>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getOrDefault(defaultValue: Set<T>): Set<T> {
    return getOptional().orElse(defaultValue)
  }

  override fun getLocalOrDefault(defaultValue: Set<T>): Set<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getOrNull(): Set<T>? {
    return getOrNull(value)
  }

  override fun getLocalOrNull(): Set<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  private fun getOrNull(value: T?): Set<T>? {
    if (value == null) {
      return null
    }
    if (MutableSet::class.java.isAssignableFrom(value.javaClass)) {
      val valueAsSet = value as Set<*>
      return if (valueAsSet.isEmpty()) {
        emptySet()
      } else {
        if (memberClazz.isAssignableFrom(valueAsSet.iterator().next()!!.javaClass)) {
          valueAsSet as Set<T>
        } else {
          throw WrongVariableTypeException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.name + ", but was not found in " + valueAsSet)
        }
      }
    }
    throw WrongVariableTypeException("Error reading $variableName: Couldn't read value of type List from $value")
  }

  private val value: T?
    get() = Optional.ofNullable(lockedExternalTask.variables)
      .orElse(Variables.createVariables())[variableName] as T?
}
