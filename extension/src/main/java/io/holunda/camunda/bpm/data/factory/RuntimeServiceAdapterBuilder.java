package io.holunda.camunda.bpm.data.factory;


import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.RuntimeService;

/**
 * Creates a builder to encapsulate the runtime service access.
 *
 * @param <T> type of builder.
 */
public class RuntimeServiceAdapterBuilder<T> {

  private final RuntimeService runtimeService;
  private final VariableFactory<T> variableFactory;

  /**
   * Constructs the builder.
   *
   * @param variableFactory variable factory to use.
   * @param runtimeService  task service to build adapter with.
   */
  public RuntimeServiceAdapterBuilder(VariableFactory<T> variableFactory, RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
    this.variableFactory = variableFactory;
  }

  /**
   * Creates a write adapter on execution.
   *
   * @param executionId id identifying execution.
   *
   * @return write adapter
   */
  public WriteAdapter<T> on(String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, variableFactory.getName(), variableFactory.getVariableClass());
  }

  /**
   * Creates a read adapter on execution.
   *
   * @param executionId id identifying execution.
   *
   * @return read adapter.
   */
  public ReadAdapter<T> from(String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, variableFactory.getName(), variableFactory.getVariableClass());
  }
}
