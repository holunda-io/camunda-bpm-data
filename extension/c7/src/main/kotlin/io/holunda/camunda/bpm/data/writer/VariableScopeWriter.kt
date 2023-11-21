package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Variable scope builder allowing for fluent variable setting.
 * @param scope variables to work on.
 */
class VariableScopeWriter(
  private val scope: VariableScope
) : VariableWriter<VariableScopeWriter>, C7ServiceVariableWriter<VariableScopeWriter>() {

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, scope: VariableScope): WriteAdapter<T> = when (variableFactory) {
      is BasicVariableFactory<*> -> C7Adapters.on(variableFactory, scope)
      is ListVariableFactory<*> -> C7Adapters.on(variableFactory, scope)
      is SetVariableFactory<*> -> C7Adapters.on(variableFactory, scope)
      is MapVariableFactory<*, *> -> C7Adapters.on(variableFactory, scope)
      else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
    } as WriteAdapter<T>
  }

  override fun <T> getWriter(variableFactory: VariableFactory<T>): WriteAdapter<T> = of(variableFactory, scope)

  override fun variables(): VariableMap {
    return scope.variablesTyped
  }

  override fun variablesLocal(): VariableMap {
    return scope.variablesLocalTyped
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as VariableScopeWriter
    return scope == that.scope
  }

  override fun hashCode(): Int {
    return scope.hashCode()
  }
}
