package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Variable map builder allowing for fluent variable setting.
 * @param variables variables to work on.
 * @param delegateLocalToGlobal allows to delegate access for "local" scope to the same variables. If set to false, all local scope access
 * will cause an exception.
 */
class VariableMapWriter(
  private val variables: VariableMap,
  private val delegateLocalToGlobal: Boolean = true
) : VariableWriter<VariableMapWriter> {

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, variables: VariableMap): WriteAdapter<T> = when (variableFactory) {
      is BasicVariableFactory<*> -> C7Adapters.on(variableFactory, variables)
      is ListVariableFactory<*> -> C7Adapters.on(variableFactory, variables)
      is SetVariableFactory<*> -> C7Adapters.on(variableFactory, variables)
      is MapVariableFactory<*, *> -> C7Adapters.on(variableFactory, variables)
      else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
    } as WriteAdapter<T>
  }

  fun <T> getWriter(variableFactory: VariableFactory<T>): WriteAdapter<T> = Companion.of(variableFactory, variables)

  override fun <T> set(variableFactory: VariableFactory<T>, value: T): VariableMapWriter {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): VariableMapWriter {
    getWriter(variableFactory)[value] = isTransient
    return this
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T): VariableMapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return this.set(variableFactory, value)
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): VariableMapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return this.set(variableFactory, value, isTransient)
  }

  override fun <T> updateLocal(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): VariableMapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return this.update(variableFactory, valueProcessor)
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): VariableMapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return this.update(variableFactory, valueProcessor, isTransient)
  }

  override fun <T> removeLocal(variableFactory: VariableFactory<T>): VariableMapWriter {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return this.remove(variableFactory)
  }

  override fun variablesLocal(): Map<String, Any?> {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return this.variables
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): VariableMapWriter {
    getWriter(variableFactory).remove()
    return this
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): VariableMapWriter {
    getWriter(variableFactory).update(valueProcessor)
    return this
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): VariableMapWriter {
    getWriter(variableFactory).update(valueProcessor, isTransient)
    return this
  }

  override fun variables(): Map<String, Any?> {
    return variables
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as VariableMapWriter
    return variables == that.variables
  }

  override fun hashCode(): Int {
    return variables.hashCode()
  }
}
