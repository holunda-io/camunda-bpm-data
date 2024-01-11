package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.factory.*
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Process execution builder allowing for fluent variable setting.
 */
class CaseServiceVariableWriter(
  private val caseService: CaseService,
  private val caseExecutionId: String
) : VariableWriter<CaseServiceVariableWriter>, C7ServiceVariableWriter<CaseServiceVariableWriter>() {

  companion object {
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T> of(variableFactory: VariableFactory<T>, caseService: CaseService, caseExecutionId: String): WriteAdapter<T> =
      when (variableFactory) {
        is BasicVariableFactory<*> -> C7Adapters.on(variableFactory, caseService, caseExecutionId)
        is ListVariableFactory<*> -> C7Adapters.on(variableFactory, caseService, caseExecutionId)
        is SetVariableFactory<*> -> C7Adapters.on(variableFactory, caseService, caseExecutionId)
        is MapVariableFactory<*, *> -> C7Adapters.on(variableFactory, caseService, caseExecutionId)
        else -> throw IllegalArgumentException("Unsupported factory type of type ${variableFactory.javaClass}")
      } as WriteAdapter<T>
  }

  override fun <T> getWriter(variableFactory: VariableFactory<T>): WriteAdapter<T> =
    of(variableFactory, caseService, caseExecutionId)


  override fun variables(): VariableMap {
    return caseService.getVariablesTyped(caseExecutionId)
  }

  override fun variablesLocal(): VariableMap {
    return caseService.getVariablesLocalTyped(caseExecutionId)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as CaseServiceVariableWriter
    return if (caseService != that.caseService) false else caseExecutionId == that.caseExecutionId
  }

  override fun hashCode(): Int {
    return Objects.hash(caseService, caseExecutionId)
  }
}
