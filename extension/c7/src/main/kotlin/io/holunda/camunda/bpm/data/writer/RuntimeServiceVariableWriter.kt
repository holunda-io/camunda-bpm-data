package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Process execution builder allowing for fluent variable setting.
 */
class RuntimeServiceVariableWriter(
  private val runtimeService: RuntimeService,
  private val executionId: String
) : VariableWriter<RuntimeServiceVariableWriter>, C7ServiceVariableWriter<RuntimeServiceVariableWriter>() {

  companion object {


    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T> of(variableFactory: VariableFactory<T>, runtimeService: RuntimeService, executionId: String): WriteAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.on(variableFactory, runtimeService, executionId)
        is ListVariableFactory<*> -> C7Adapters.on(variableFactory, runtimeService, executionId)
        is SetVariableFactory<*> -> C7Adapters.on(variableFactory, runtimeService, executionId)
        is MapVariableFactory<*, *> -> C7Adapters.on(variableFactory, runtimeService, executionId)
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      } as WriteAdapter<T>
  }

  override fun <T> getWriter(variableFactory: VariableFactory<T>): WriteAdapter<T> =
    of(variableFactory, runtimeService, executionId)

  override fun variables(): VariableMap {
    return runtimeService.getVariablesTyped(executionId)
  }

  override fun variablesLocal(): VariableMap {
    return runtimeService.getVariablesLocalTyped(executionId)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as RuntimeServiceVariableWriter
    return if (runtimeService != that.runtimeService) false else executionId == that.executionId
  }

  override fun hashCode(): Int {
    return Objects.hash(runtimeService, executionId)
  }
}
