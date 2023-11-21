package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.VariableFactory
import java.util.function.Function

abstract class C7ServiceVariableWriter<SELF : C7ServiceVariableWriter<SELF>> : VariableWriter<SELF> {

  abstract fun <T> getWriter(variableFactory: VariableFactory<T>): WriteAdapter<T>

  @Suppress("UNCHECKED_CAST") // TODO: does it work? a subclass passing itself as self should have correct signature, right?
  open fun self(): SELF {
    return this as SELF
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T): SELF {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): SELF {
    getWriter(variableFactory)[value] = isTransient
    return self()
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T): SELF {
    return this.setLocal(variableFactory, value, false)
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): SELF {
    getWriter(variableFactory).setLocal(value, isTransient)
    return self()
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>
  ): SELF {
    return updateLocal(variableFactory, valueProcessor, false)
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): SELF {
    getWriter(variableFactory).updateLocal(valueProcessor, isTransient)
    return self()
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): SELF {
    getWriter(variableFactory).remove()
    return self()
  }

  override fun <T> removeLocal(variableFactory: VariableFactory<T>): SELF {
    getWriter(variableFactory).removeLocal()
    return self()
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): SELF {
    getWriter(variableFactory).update(valueProcessor)
    return self()
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): SELF {
    getWriter(variableFactory).updateLocal(valueProcessor, isTransient)
    return self()
  }
}
