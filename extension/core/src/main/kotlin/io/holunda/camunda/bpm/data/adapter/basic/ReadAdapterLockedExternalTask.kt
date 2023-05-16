package io.holunda.camunda.bpm.data.adapter.basic

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import java.util.*

/**
 * Read-write adapter for external task.
 *
 * @param [T] type of value.
 */
class ReadAdapterLockedExternalTask<T : Any>(lockedExternalTask: LockedExternalTask, variableName: String, clazz: Class<T>) :
  ReadAdapter<T>
{
  private val readAdapter: ReadAdapter<T> = ReadWriteAdapterVariableMap(lockedExternalTask.variables, variableName, clazz)

  override fun get(): T {
    return readAdapter.get()
  }

  override fun getOptional(): Optional<T> {
    return readAdapter.getOptional()
  }

  override fun getLocal(): T {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getLocalOptional(): Optional<T> {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getOrDefault(defaultValue: T): T {
    return readAdapter.getOrDefault(defaultValue)
  }

  override fun getLocalOrDefault(defaultValue: T): T {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }

  override fun getOrNull(): T? {
    return readAdapter.getOrNull()
  }

  override fun getLocalOrNull(): T {
    throw UnsupportedOperationException("Can't get a local variable on an external task")
  }
}
