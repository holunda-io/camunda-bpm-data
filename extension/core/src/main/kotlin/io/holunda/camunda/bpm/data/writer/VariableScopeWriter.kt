package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Variable scope builder allowing for fluent variable setting.
 * @param scope variables to work on.
 */
class VariableScopeWriter(private val scope: VariableScope) : VariableWriter<VariableScopeWriter> {
  override fun <T> set(variableFactory: VariableFactory<T>, value: T): VariableScopeWriter {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): VariableScopeWriter {
    variableFactory.on(scope)[value] = isTransient
    return this
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T): VariableScopeWriter {
    return this.setLocal(variableFactory, value, false)
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): VariableScopeWriter {
    variableFactory.on(scope).setLocal(value, isTransient)
    return this
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): VariableScopeWriter {
    variableFactory.on(scope).update(valueProcessor)
    return this
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): VariableScopeWriter {
    variableFactory.on(scope).update(valueProcessor, isTransient)
    return this
  }

  override fun <T> updateLocal(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): VariableScopeWriter {
    variableFactory.on(scope).updateLocal(valueProcessor)
    return this
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): VariableScopeWriter {
    variableFactory.on(scope).updateLocal(valueProcessor, isTransient)
    return this
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): VariableScopeWriter {
    variableFactory.on(scope).remove()
    return this
  }

  override fun <T> removeLocal(variableFactory: VariableFactory<T>): VariableScopeWriter {
    variableFactory.on(scope).removeLocal()
    return this
  }

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
