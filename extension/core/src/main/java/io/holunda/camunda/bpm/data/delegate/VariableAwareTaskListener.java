package io.holunda.camunda.bpm.data.delegate;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public interface VariableAwareTaskListener extends TaskListener {

  @Override
  default void notify(DelegateTask task) {
    notify(new VariableAwareDelegateTask(task));
  }

  void notify(VariableAwareDelegateTask task);
}
