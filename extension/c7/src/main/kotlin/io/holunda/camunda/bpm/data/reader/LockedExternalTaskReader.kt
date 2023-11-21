package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.externaltask.LockedExternalTask

/**
 * Allows reading multiple variable values from [LockedExternalTask]).
 * @param lockedExternalTask scope to operate on.
 */
class LockedExternalTaskReader(
  private val lockedExternalTask: LockedExternalTask
) : VariableReader, C7VariableReader() {

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, lockedExternalTask: LockedExternalTask): ReadAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.from(variableFactory, lockedExternalTask) as ReadAdapter<T>
        is ListVariableFactory<*> -> C7Adapters.from(variableFactory, lockedExternalTask) as ReadAdapter<T>
        is SetVariableFactory<*> -> C7Adapters.from(variableFactory, lockedExternalTask) as ReadAdapter<T>
        is MapVariableFactory<*, *> -> C7Adapters.from(variableFactory, lockedExternalTask) as ReadAdapter<T>
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      }
  }

  override fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T> = of(variableFactory, lockedExternalTask)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as LockedExternalTaskReader
    return lockedExternalTask == that.lockedExternalTask
  }

  override fun hashCode(): Int {
    return lockedExternalTask.hashCode()
  }
}
