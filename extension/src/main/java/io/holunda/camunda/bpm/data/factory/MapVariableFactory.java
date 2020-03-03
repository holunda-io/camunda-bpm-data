package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.map.MapReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * Variable factory of a base parametrized map type.
 *
 * @param <K> member key type of the factory.
 * @param <V> member value type of the factory.
 */
public class MapVariableFactory<K, V> implements VariableFactory<Map<K, V>> {

  @NotNull
  private final String name;

  @NotNull
  private final Class<K> keyClazz;

  @NotNull
  private final Class<V> valueClazz;

  public MapVariableFactory(@NotNull String name, @NotNull Class<K> keyClazz, @NotNull Class<V> valueClazz) {
    this.name = name;
    this.keyClazz = keyClazz;
    this.valueClazz = valueClazz;
  }

  @Override
  public WriteAdapter<Map<K, V>> on(VariableScope variableScope) {
    return new MapReadWriteAdapterVariableScope<>(variableScope, name, keyClazz, valueClazz);
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
  @NotNull
  public String getName() {
    return name;
  }

  /**
   * Retrieves key type.
   *
   * @return key type.
   */
  @NotNull
  public Class<K> getKeyClass() {
    return keyClazz;
  }

  /**
   * Retrieves value type.
   *
   * @return value type.
   */
  @NotNull
  public Class<V> getValueClass() {
    return valueClazz;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MapVariableFactory<?, ?> that = (MapVariableFactory<?, ?>) o;
    return name.equals(that.name) &&
      keyClazz.equals(that.keyClazz) &&
      valueClazz.equals(that.valueClazz);
  }

  @Override public int hashCode() {
    return Objects.hash(name, keyClazz, valueClazz);
  }
}
