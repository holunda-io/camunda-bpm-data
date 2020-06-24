package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;
import org.camunda.bpm.engine.TaskService;

/**
 * Allows reading multiple variable values from {@link TaskService#getVariable(String, String)}.
 */
public class UserTaskVariableReader implements VariableReader {

  private final TaskService taskService;
  private final String taskId;

  public UserTaskVariableReader(TaskService taskService, String taskId) {
    this.taskService = taskService;
    this.taskId = taskId;
  }


  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).getOptional();
  }

  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).get();
  }

  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).getLocal();
  }

  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(taskService, taskId).getLocalOptional();
  }
}
