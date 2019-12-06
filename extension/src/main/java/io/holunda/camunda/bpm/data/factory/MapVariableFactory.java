package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableScope;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.List;
import java.util.Map;

/**
 * Variable factory of a base parametrized map type.
 *
 * @param <K> member key type of the factory.
 * @param <V> member value type of the factory.
 */
public class MapVariableFactory<K, V> implements VariableFactory<Map<K, V>> {

  private final String name;
  private final Class<K> keyClazz;
  private final Class<V> valueClazz;

  public MapVariableFactory(String name, Class<K> keyClazz, Class<V> valueClazz) {
    this.name = name;
    this.keyClazz = keyClazz;
    this.valueClazz = valueClazz;
  }

  @Override
  public WriteAdapter<Map<K,V>> on(VariableScope variableScope) {
    return new MapReadWriteAdapterVariableScope<K, V>(variableScope, name, keyClazz, valueClazz);
  }

  @Override
  public ReadAdapter<Map<K, V>> from(VariableScope variableScope) {
    return new MapReadWriteAdapterVariableScope<>(variableScope, name, keyClazz, valueClazz);
  }

  @Override
  public WriteAdapter<Map<K, V>> on(VariableMap variableMap) {
    return new MapReadWriteAdapterVariableMap<>(variableMap, name, keyClazz, valueClazz);
  }

  @Override
  public ReadAdapter<Map<K, V>> from(VariableMap variableMap) {
    return new MapReadWriteAdapterVariableMap<>(variableMap, name, keyClazz, valueClazz);
  }

  @Override
  public WriteAdapter<Map<K, V>> on(RuntimeService runtimeService, String executionId) {
    return new MapReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, keyClazz, valueClazz);
  }

  @Override
  public ReadAdapter<Map<K, V>> from(RuntimeService runtimeService, String executionId) {
    return new MapReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, keyClazz, valueClazz);
  }

  @Override
  public WriteAdapter<Map<K, V>> on(TaskService taskService, String taskId) {
    return new MapReadWriteAdapterTaskService<>(taskService, taskId, name, keyClazz, valueClazz);
  }

  @Override
  public ReadAdapter<Map<K, V>> from(TaskService taskService, String taskId) {
    return new MapReadWriteAdapterTaskService<>(taskService, taskId, name, keyClazz, valueClazz);
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Retrieves key type.
   * @return key type.
   */
  public Class<K> getKeyClass() {
    return keyClazz;
  }

  /**
   * Retrieves value type.
   * @return value type.
   */
  public Class<V> getValueClass() {
    return valueClazz;
  }
}
