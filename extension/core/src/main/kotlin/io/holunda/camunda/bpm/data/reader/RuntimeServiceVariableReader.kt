package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService
import java.util.*

/**
 * Allows reading multiple variable values from [RuntimeService.getVariable].
 * @param runtimeService runtime service to use.
 * @param executionId    execution id.
 */
class RuntimeServiceVariableReader(private val runtimeService: RuntimeService, private val executionId: String) : VariableReader {
  override fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return variableFactory.from(runtimeService, executionId).optional
  }

  override fun <T> get(variableFactory: VariableFactory<T>): T {
    return variableFactory.from(runtimeService, executionId).get()
  }

  override fun <T> getLocal(variableFactory: VariableFactory<T>): T {
    return variableFactory.from(runtimeService, executionId).local
  }

  override fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return variableFactory.from(runtimeService, executionId).localOptional
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as RuntimeServiceVariableReader
    return if (runtimeService != that.runtimeService) false else executionId == that.executionId
  }

  override fun hashCode(): Int {
    var result = runtimeService.hashCode()
    result = 31 * result + executionId.hashCode()
    return result
  }
}
