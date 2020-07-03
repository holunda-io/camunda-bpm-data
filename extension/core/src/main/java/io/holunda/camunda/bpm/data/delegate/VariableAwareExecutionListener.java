package io.holunda.camunda.bpm.data.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public interface VariableAwareExecutionListener extends ExecutionListener {

  @Override
  default void notify(DelegateExecution execution) throws Exception {
    notify(new VariableAwareDelegateExecution(execution));
  }

  void notify(VariableAwareDelegateExecution execution) throws Exception;
}
