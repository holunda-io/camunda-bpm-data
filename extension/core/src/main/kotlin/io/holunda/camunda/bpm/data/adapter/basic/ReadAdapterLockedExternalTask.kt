package io.holunda.camunda.bpm.data.adapter.basic

import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Read-write adapter for external task.
 *
 * @param [T] type of value.
 */
class ReadAdapterLockedExternalTask<T : Any?>(
  private val lockedExternalTask: LockedExternalTask,
  variableName: String,
  clazz: Class<T>
) : AbstractBasicReadWriteAdapter<T>(variableName, clazz) {
  private val value: Any?
    get() = Optional.ofNullable(lockedExternalTask.variables)
      .orElse(Variables.createVariables())[variableName]

  override fun getOptional(): Optional<T> {
    return Optional.ofNullable(
      getOrNull(
        value
      )
    ) as Optional<T>
  }

  override fun set(value: T, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a variable on an external task")
  }

  override fun setLocal(value: T, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on an external task")
  }

  override fun getLocal(): T {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrDefault(defaultValue: T): T {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrNull(): T {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun remove() {
    throw UnsupportedOperationException("Can't remove a variable on an external task")
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't remove a local variable on an external task")
  }
}
