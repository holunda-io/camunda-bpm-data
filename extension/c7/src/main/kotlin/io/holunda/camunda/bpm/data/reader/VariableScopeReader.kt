package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.delegate.VariableScope

/**
 * Allows reading multiple variable values from [VariableScope] (such as [org.camunda.bpm.engine.delegate.DelegateExecution] and [org.camunda.bpm.engine.delegate.DelegateTask]).
 * @param variableScope scope to operate on.
 */
class VariableScopeReader(
  private val variableScope: VariableScope
) : VariableReader, C7VariableReader() {

  companion object {
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T> of(variableFactory: VariableFactory<T>, variableScope: VariableScope): ReadAdapter<T> = when (variableFactory) {
      is BasicVariableFactory<*> -> C7Adapters.from(variableFactory, variableScope)
      is ListVariableFactory<*> -> C7Adapters.from(variableFactory, variableScope)
      is SetVariableFactory<*> -> C7Adapters.from(variableFactory, variableScope)
      is MapVariableFactory<*, *> -> C7Adapters.from(variableFactory, variableScope)
      else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
    } as ReadAdapter<T>

  }

  override fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T> = Companion.of(variableFactory, variableScope)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as VariableScopeReader
    return variableScope == that.variableScope
  }

  override fun hashCode(): Int {
    return variableScope.hashCode()
  }
}
