package io.holunda.camunda.bpm.data.factory;


import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.RuntimeService;

/**
 * Creates a builder to encapsulate the runtime service access.
 *
 * @param <T> type of builder.
 */
public class RuntimeServiceAdapterBuilder<T> {

  private final RuntimeService runtimeService;
  private final BasicVariableFactory<T> basicVariableFactory;

  /**
   * Constructs the builder.
   *
   * @param basicVariableFactory variable factory to use.
   * @param runtimeService  task service to build adapter with.
   */
  public RuntimeServiceAdapterBuilder(BasicVariableFactory<T> basicVariableFactory, RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
    this.basicVariableFactory = basicVariableFactory;
  }

  /**
   * Creates a write adapter on execution.
   *
   * @param executionId id identifying execution.
   *
   * @return write adapter
   */
  public WriteAdapter<T> on(String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, basicVariableFactory.getName(), basicVariableFactory.getVariableClass());
  }

  /**
   * Creates a read adapter on execution.
   *
   * @param executionId id identifying execution.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, basicVariableFactory.getName(), basicVariableFactory.getVariableClass());
  }
}
