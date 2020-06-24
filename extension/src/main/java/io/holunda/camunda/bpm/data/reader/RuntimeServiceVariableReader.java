package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;
import org.camunda.bpm.engine.RuntimeService;
import org.jetbrains.annotations.NotNull;

/**
 * Allows reading multiple variable values from {@link RuntimeService#getVariable(String, String)}.
 */
public class RuntimeServiceVariableReader implements VariableReader {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Constructs a reader operating on execution.
   * @param runtimeService runtime service to use.
   * @param executionId execution id.
   */
  public RuntimeServiceVariableReader(RuntimeService runtimeService, String executionId) {
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @NotNull
  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).getOptional();
  }

  @NotNull
  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).get();
  }

  @NotNull
  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).getLocal();
  }

  @NotNull
  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).getLocalOptional();
  }
}
