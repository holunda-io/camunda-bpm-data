package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Allows reading multiple variable values from [VariableMap.getValue].
 * @param variableMap map to operate on.
 */
class VariableMapReader(
  private val variableMap: VariableMap
) : VariableReader, C7VariableReader() {

  companion object {

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, variableMap: VariableMap): ReadAdapter<T> = when (variableFactory) {
      is BasicVariableFactory<*> -> C7Adapters.from(variableFactory, variableMap)
      is ListVariableFactory<*> -> C7Adapters.from(variableFactory, variableMap)
      is SetVariableFactory<*> -> C7Adapters.from(variableFactory, variableMap)
      is MapVariableFactory<*, *> -> C7Adapters.from(variableFactory, variableMap)
      else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
    } as ReadAdapter<T>
  }

  override fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T> = Companion.of(variableFactory, variableMap)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as VariableMapReader
    return variableMap == that.variableMap
  }

  override fun hashCode(): Int {
    return variableMap.hashCode()
  }
}
