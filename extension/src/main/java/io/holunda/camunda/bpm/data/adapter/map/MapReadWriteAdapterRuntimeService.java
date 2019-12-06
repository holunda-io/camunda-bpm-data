package io.holunda.camunda.bpm.data.adapter.map;

import org.camunda.bpm.engine.RuntimeService;

import java.util.Map;
import java.util.Optional;

/**
 * Read write adapter for runtime service access.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class MapReadWriteAdapterRuntimeService<K, V> extends AbstractMapReadWriteAdapter<K, V> {

  private final RuntimeService runtimeService;
  private final String executionId;

  /**
   * Constructs the adapter.
   *
   * @param runtimeService runtime service to use.
   * @param executionId    id of the execution to read from and write to.
   * @param variableName   name of the variable.
   * @param keyClazz       class of the key variable.
   * @param valueClazz     class of the value variable.
   */
  public MapReadWriteAdapterRuntimeService(
    RuntimeService runtimeService, String executionId, String variableName, Class<K> keyClazz, Class<V> valueClazz) {
    super(variableName, keyClazz, valueClazz);
    this.runtimeService = runtimeService;
    this.executionId = executionId;
  }

  @Override
  public Optional<Map<K, V>> getOptional() {
    return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName)));
  }

  @Override
  public void set(Map<K, V> value, boolean isTransient) {
    runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
  }

  @Override
  public void setLocal(Map<K, V> value, boolean isTransient) {
    runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient));
  }
}
