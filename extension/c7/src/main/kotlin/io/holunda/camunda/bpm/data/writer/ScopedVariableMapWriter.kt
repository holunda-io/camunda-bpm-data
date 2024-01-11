package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.CamundaBpmData.writer
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Writer to write into two variable maps, for global and local scope accordingly.
 */
class ScopedVariableMapWriter(
  global: VariableMap,
  local: VariableMap
) : VariableWriter<ScopedVariableMapWriter> {

  private val globalWriter = writer(global)
  private val localWriter = writer(local)

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>, value: T): ScopedVariableMapWriter {
    localWriter[variableFactory] = value
    return this
  }

  override fun <T : Any?> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): ScopedVariableMapWriter {
    localWriter[variableFactory, value] = isTransient
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): ScopedVariableMapWriter {
    localWriter.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> updateLocal(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>, isTransient: Boolean): ScopedVariableMapWriter {
    localWriter.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> removeLocal(variableFactory: VariableFactory<T>): ScopedVariableMapWriter {
    localWriter.remove(variableFactory)
    return this
  }

  override fun variablesLocal(): Map<String, Any?> {
    return localWriter.variables()
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>, value: T): ScopedVariableMapWriter {
    globalWriter[variableFactory] = value
    return this
  }

  override fun <T : Any?> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): ScopedVariableMapWriter {
    globalWriter[variableFactory, value] = isTransient
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): ScopedVariableMapWriter {
    globalWriter.update(variableFactory, valueProcessor)
    return this
  }

  override fun <T : Any?> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>, isTransient: Boolean): ScopedVariableMapWriter {
    globalWriter.update(variableFactory, valueProcessor, isTransient)
    return this
  }

  override fun <T : Any?> remove(variableFactory: VariableFactory<T>): ScopedVariableMapWriter {
    globalWriter.remove(variableFactory)
    return this
  }

  override fun variables(): Map<String, Any?> {
    return globalWriter.variables()
  }
}
