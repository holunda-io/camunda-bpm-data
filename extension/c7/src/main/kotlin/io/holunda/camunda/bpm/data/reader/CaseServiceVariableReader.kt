package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Allows reading multiple variable values from [CaseService.getVariable].
 * @param caseService     runtime service to use.
 * @param caseExecutionId execution id.
 *
 */
class CaseServiceVariableReader(
  private val caseService: CaseService,
  private val caseExecutionId: String
) : VariableReader, C7VariableReader() {

  companion object {
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> of(variableFactory: VariableFactory<T>, caseService: CaseService, caseExecutionId: String): ReadAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.from(variableFactory, caseService, caseExecutionId)
        is ListVariableFactory<*> -> C7Adapters.from(variableFactory, caseService, caseExecutionId)
        is SetVariableFactory<*> -> C7Adapters.from(variableFactory, caseService, caseExecutionId)
        is MapVariableFactory<*, *> -> C7Adapters.from(variableFactory, caseService, caseExecutionId)
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      } as ReadAdapter<T>
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T> getReader(variableFactory: VariableFactory<T>): ReadAdapter<T> =
    of(variableFactory, caseService, caseExecutionId)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as CaseServiceVariableReader
    return if (caseService != that.caseService) false else caseExecutionId == that.caseExecutionId
  }

  override fun hashCode(): Int {
    return Objects.hash(caseService, caseExecutionId)
  }
}
