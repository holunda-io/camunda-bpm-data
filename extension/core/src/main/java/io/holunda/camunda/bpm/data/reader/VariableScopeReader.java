package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.jetbrains.annotations.NotNull;

/**
 * Allows reading multiple variable values from {@link VariableScope} (such as {@link org.camunda.bpm.engine.delegate.DelegateExecution} and {@link org.camunda.bpm.engine.delegate.DelegateTask}).
 */
public class VariableScopeReader implements VariableReader{

  private final VariableScope variableScope;

  /**
   * Constructs a reader operating on variable scope (e.g. DelegateExecution or DelegateTask).
   * @param variableScope scope to operate on.
   */
  public VariableScopeReader(VariableScope variableScope) {
    this.variableScope = variableScope;
  }

  @Override
  @NotNull
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).getOptional();
  }

  @Override
  @NotNull
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).get();
  }

  @Override
  @NotNull
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).getLocal();
  }

  @Override
  @NotNull
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableScope).getLocalOptional();
  }
}
