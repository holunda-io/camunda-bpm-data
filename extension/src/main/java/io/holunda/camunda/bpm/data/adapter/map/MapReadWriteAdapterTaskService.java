package io.holunda.camunda.bpm.data.adapter.map;

import org.camunda.bpm.engine.TaskService;

import java.util.Map;
import java.util.Optional;

/**
 * Read write adapter for task service access.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class MapReadWriteAdapterTaskService<K, V> extends AbstractMapReadWriteAdapter<K, V> {

  private final TaskService taskService;
  private final String taskId;

  /**
   * Constructs the adapter.
   *
   * @param taskService  task service to use.
   * @param taskId       id of the task to read from and write to.
   * @param variableName name of the variable.
   * @param keyClazz     class of the key of variable.
   * @param valueClazz   class of variable.
   */
  public MapReadWriteAdapterTaskService(
    TaskService taskService, String taskId, String variableName, Class<K> keyClazz, Class<V> valueClazz) {
    super(variableName, keyClazz, valueClazz);
    this.taskService = taskService;
    this.taskId = taskId;
  }

  @Override
  public Optional<Map<K, V>> getOptional() {
    return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)));
  }

  @Override
  public void set(Map<K, V> value, boolean isTransient) {
    taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public void setLocal(Map<K, V> value, boolean isTransient) {
    taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient));
  }

}
