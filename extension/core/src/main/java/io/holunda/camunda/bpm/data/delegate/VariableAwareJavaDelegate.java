package io.holunda.camunda.bpm.data.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public interface VariableAwareJavaDelegate extends JavaDelegate {

  @Override
  default void execute(DelegateExecution execution) throws Exception {
    execute(new VariableAwareDelegateExecution(execution));
  }

  void execute(VariableAwareDelegateExecution execution) throws Exception;
}
