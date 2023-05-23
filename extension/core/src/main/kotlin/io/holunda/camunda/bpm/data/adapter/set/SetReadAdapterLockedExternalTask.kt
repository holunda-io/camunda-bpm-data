package io.holunda.camunda.bpm.data.adapter.set

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
  private val lockedExternalTask: LockedExternalTask, variableName: String, memberClazz: Class<T>
) : AbstractSetReadWriteAdapter<T>(variableName, memberClazz) {

  private val value: Any?
    get() = Optional.ofNullable(lockedExternalTask.variables).orElse(Variables.createVariables())[variableName]

  override fun getOptional(): Optional<Set<T>> {
    return Optional.ofNullable(
      getOrNull(
        value
      )
    )
  }

  override fun set(value: Set<T>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a variable on an external task")
  }

  override fun setLocal(value: Set<T>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on an external task")
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

  override fun getLocalOrNull(): Set<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun remove() {
    throw UnsupportedOperationException("Can't remove a variable on an external task")
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't remove a local variable on an external task")
  }


}
