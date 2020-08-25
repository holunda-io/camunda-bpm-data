package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Allows reading multiple variable values from {@link RuntimeService#getVariable(String, String)}.
 */
public class RuntimeServiceVariableReader implements VariableReader {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Constructs a reader operating on execution.
   *
   * @param runtimeService runtime service to use.
   * @param executionId    execution id.
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RuntimeServiceVariableReader that = (RuntimeServiceVariableReader) o;

    if (!Objects.equals(runtimeService, that.runtimeService)) return false;
    return Objects.equals(executionId, that.executionId);
  }

  @Override
  public int hashCode() {
    int result = runtimeService != null ? runtimeService.hashCode() : 0;
    result = 31 * result + (executionId != null ? executionId.hashCode() : 0);
    return result;
  }
}
