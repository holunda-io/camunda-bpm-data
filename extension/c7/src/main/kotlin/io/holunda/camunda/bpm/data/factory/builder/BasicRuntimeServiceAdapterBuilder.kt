package io.holunda.camunda.bpm.data.factory.builder

import io.holunda.camunda.bpm.data.adapter.ReadAdapter
import io.holunda.camunda.bpm.data.adapter.WriteAdapter
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterRuntimeService
import io.holunda.camunda.bpm.data.factory.BasicVariableFactory
import org.camunda.bpm.engine.RuntimeService

/**
 * Creates a builder to encapsulate the runtime service access.
 *
 * @param [T] type of builder.
 * @param basicVariableFactory variable factory to use.
 * @param runtimeService       task service to build adapter with.
 */
class BasicRuntimeServiceAdapterBuilder<T: Any?>(
  private val basicVariableFactory: BasicVariableFactory<T>,
  private val runtimeService: RuntimeService
) {
  private fun readWriteAdapter(executionId: String): ReadWriteAdapterRuntimeService<T> {
    return ReadWriteAdapterRuntimeService(
        runtimeService,
        executionId,
        basicVariableFactory.name,
        basicVariableFactory.variableClass
    )
  }

  /**
   * Creates a write adapter on execution.
   *
   * @param executionId id identifying execution.
   * @return write adapter
   */
  fun on(executionId: String): WriteAdapter<T> {
    return readWriteAdapter(executionId)
  }

  /**
   * Creates a read adapter on execution.
   *
   * @param executionId id identifying execution.
   * @return read adapter.
   */
  fun from(executionId: String): ReadAdapter<T> {
    return readWriteAdapter(executionId)
  }
}
