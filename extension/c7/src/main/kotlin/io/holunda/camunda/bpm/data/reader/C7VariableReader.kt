package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.VariableFactory
import java.util.*

abstract class C7VariableReader : VariableReader {

  abstract fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T>

  override fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return getReader(variableFactory).getOptional()
  }

  override fun <T> get(variableFactory: VariableFactory<T>): T {
    return getReader(variableFactory).get()
  }

  override fun <T> getLocal(variableFactory: VariableFactory<T>): T {
    return getReader(variableFactory).getLocal()
  }

  override fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return getReader(variableFactory).getLocalOptional()
  }


}
