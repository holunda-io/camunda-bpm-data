package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Process execution builder allowing for fluent variable setting.
 */
class RuntimeServiceVariableWriter(private val runtimeService: RuntimeService, private val executionId: String) :
  VariableWriter<RuntimeServiceVariableWriter> {
  override fun variables(): VariableMap {
    return runtimeService.getVariablesTyped(executionId)
  }

  override fun variablesLocal(): VariableMap {
    return runtimeService.getVariablesLocalTyped(executionId)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T): RuntimeServiceVariableWriter {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId)[value] = isTransient
    return this
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T): RuntimeServiceVariableWriter {
    return this.setLocal(variableFactory, value, false)
  }

  override fun <T> setLocal(
    variableFactory: VariableFactory<T>,
    value: T,
    isTransient: Boolean
  ): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).setLocal(value, isTransient)
    return this
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).remove()
    return this
  }

  override fun <T> removeLocal(variableFactory: VariableFactory<T>): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).removeLocal()
    return this
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).update(valueProcessor)
    return this
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).update(valueProcessor, isTransient)
    return this
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>
  ): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).updateLocal(valueProcessor)
    return this
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): RuntimeServiceVariableWriter {
    variableFactory.on(runtimeService, executionId).updateLocal(valueProcessor, isTransient)
    return this
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as RuntimeServiceVariableWriter
    return if (runtimeService != that.runtimeService) false else executionId == that.executionId
  }

  override fun hashCode(): Int {
    var result = runtimeService.hashCode()
    result = 31 * result + executionId.hashCode()
    return result
  }
}
