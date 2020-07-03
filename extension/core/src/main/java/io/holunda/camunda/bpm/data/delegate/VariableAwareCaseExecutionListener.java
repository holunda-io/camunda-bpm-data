package io.holunda.camunda.bpm.data.delegate;

import org.camunda.bpm.engine.delegate.CaseExecutionListener;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;

public interface VariableAwareCaseExecutionListener extends CaseExecutionListener {

  @Override
  default void notify(DelegateCaseExecution execution) throws Exception {
    notify(new VariableAwareDelegateCaseExecution(execution));
  }

  void notify(VariableAwareDelegateCaseExecution execution) throws Exception;
}
