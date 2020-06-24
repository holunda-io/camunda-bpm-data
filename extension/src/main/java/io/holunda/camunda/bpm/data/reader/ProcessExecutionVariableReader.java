package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;
import org.camunda.bpm.engine.RuntimeService;

public class ProcessExecutionVariableReader implements VariableReader {

  private final RuntimeService runtimeService;
  private final String executionId;

  public ProcessExecutionVariableReader(RuntimeService runtimeService, String executionId) {
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).getOptional();
  }

  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).get();
  }

  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).getLocal();
  }

  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(runtimeService, executionId).getLocalOptional();
  }
}
