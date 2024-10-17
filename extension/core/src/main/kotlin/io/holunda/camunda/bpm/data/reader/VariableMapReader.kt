package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Allows reading multiple variable values from [VariableMap.getValue].
 * @param variableMap map to operate on.
 */
class VariableMapReader(private val variableMap: VariableMap) : VariableReader {

  override fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return variableFactory.from(variableMap).getOptional()
  }

  override operator fun <T> get(variableFactory: VariableFactory<T>): T {
    return variableFactory.from(variableMap).get()
  }

  override fun <T> getLocal(variableFactory: VariableFactory<T>): T {
    return variableFactory.from(variableMap).getLocal()
  }

  override fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return variableFactory.from(variableMap).getLocalOptional()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as VariableMapReader
    return variableMap == that.variableMap
  }

  override fun hashCode(): Int {
    return variableMap.hashCode()
  }
}
