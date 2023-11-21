package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.RuntimeService
import java.util.*

/**
 * Allows reading multiple variable values from [RuntimeService.getVariable].
 * @param runtimeService runtime service to use.
 * @param executionId    execution id.
 */
class RuntimeServiceVariableReader(
  private val runtimeService: RuntimeService,
  private val executionId: String
) : VariableReader, C7VariableReader() {

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, runtimeService: RuntimeService, executionId: String): ReadAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.from(variableFactory, runtimeService, executionId)
        is ListVariableFactory<*> -> C7Adapters.from(variableFactory, runtimeService, executionId)
        is SetVariableFactory<*> -> C7Adapters.from(variableFactory, runtimeService, executionId)
        is MapVariableFactory<*, *> -> C7Adapters.from(variableFactory, runtimeService, executionId)
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      } as ReadAdapter<T>
  }

  override fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T> =
    of(variableFactory, runtimeService, executionId)


  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as RuntimeServiceVariableReader
    return if (runtimeService != that.runtimeService) false else executionId == that.executionId
  }

  override fun hashCode(): Int {
    return Objects.hash(runtimeService, executionId)
  }
}
