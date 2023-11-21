package io.holunda.camunda.bpm.data.factory.builder

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterCaseService
import io.holunda.camunda.bpm.data.factory.BasicVariableFactory
import org.camunda.bpm.engine.CaseService

/**
 * Creates a builder to encapsulate the case service access.
 *
 * @param [T] type of builder.
 * @param basicVariableFactory variable factory to use.
 * @param caseService          task service to build adapter with.
 */
class BasicCaseServiceAdapterBuilder<T: Any?>(private val basicVariableFactory: BasicVariableFactory<T>, private val caseService: CaseService) {
  private fun readWriteAdapter(caseExecutionId: String): ReadWriteAdapterCaseService<T> {
    return ReadWriteAdapterCaseService(
      caseService,
      caseExecutionId,
      basicVariableFactory.name,
      basicVariableFactory.variableClass
    )
  }

  /**
   * Creates a write adapter on caseExecution.
   *
   * @param caseExecutionId id identifying caseExecution.
   * @return write adapter
   */
  fun on(caseExecutionId: String): WriteAdapter<T> {
    return readWriteAdapter(caseExecutionId)
  }

  /**
   * Creates a read adapter on caseExecution.
   *
   * @param caseExecutionId id identifying caseExecution.
   * @return read adapter.
   */
  fun from(caseExecutionId: String): ReadAdapter<T> {
    return readWriteAdapter(caseExecutionId)
  }
}
