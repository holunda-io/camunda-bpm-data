package io.holunda.camunda.bpm.data.adapter.list

import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.Variables
import java.util.*

/**
 * Read adapter for external task.
 *
 * @param [T] type of value.
 */
class ListReadAdapterLockedExternalTask<T>(
  private val lockedExternalTask: LockedExternalTask,
  variableName: String,
  memberClazz: Class<T>
) : AbstractListReadWriteAdapter<T>(variableName, memberClazz) {

  private val value: Any?
    get() = Optional.ofNullable(lockedExternalTask.variables)
      .orElse(Variables.createVariables())[variableName]

  override fun getOptional(): Optional<List<T>> {
    return Optional.ofNullable(
      getOrNull(
        value
      )
    )
  }

  override fun set(value: List<T>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a variable on an external task")
  }

  override fun setLocal(value: List<T>, isTransient: Boolean) {
    throw UnsupportedOperationException("Can't set a local variable on an external task")
  }

  override fun getLocal(): List<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<List<T>> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrDefault(defaultValue: List<T>): List<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOrNull(): List<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun remove() {
    throw UnsupportedOperationException("Can't remove a variable on an external task")
  }

  override fun removeLocal() {
    throw UnsupportedOperationException("Can't remove a local variable on an external task")
  }
}
