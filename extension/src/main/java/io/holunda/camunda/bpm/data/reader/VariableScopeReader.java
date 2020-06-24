package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;
import org.camunda.bpm.engine.delegate.VariableScope;

/**
 * Allows reading multiple variable values from {@link VariableScope} (such as {@link org.camunda.bpm.engine.delegate.DelegateExecution} and {@link org.camunda.bpm.engine.delegate.DelegateTask}).
 */
public class VariableScopeReader implements VariableReader{

  private final VariableScope variableScope;

  public VariableScopeReader(VariableScope variableScope) {
    this.variableScope = variableScope;
  }

  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).getOptional();
  }

  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).get();
  }

  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).getLocal();
  }

  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).getLocalOptional();
  }
}
