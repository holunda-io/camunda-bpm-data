package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Variable map builder allowing for fluent variable setting.
 * @param variables variables to work on.
 */
class VariableMapWriter(private val variables: VariableMap) : GlobalVariableWriter<VariableMapWriter> {
  override fun <T> set(variableFactory: VariableFactory<T>, value: T): VariableMapWriter {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): VariableMapWriter {
    variableFactory.on(variables)[value] = isTransient
    return this
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): VariableMapWriter {
    variableFactory.on(variables).remove()
    return this
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): VariableMapWriter {
    variableFactory.on(variables).update(valueProcessor)
    return this
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): VariableMapWriter {
    variableFactory.on(variables).update(valueProcessor, isTransient)
    return this
  }

  override fun variables(): VariableMap {
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
