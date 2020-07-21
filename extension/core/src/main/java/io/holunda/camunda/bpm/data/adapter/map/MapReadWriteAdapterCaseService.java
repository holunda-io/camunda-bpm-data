package io.holunda.camunda.bpm.data.adapter.map;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;

import java.util.Map;
import java.util.Optional;

/**
 * Read write adapter for case service access.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class MapReadWriteAdapterCaseService<K, V> extends AbstractMapReadWriteAdapter<K, V> {

    private final CaseService caseService;
    private final String caseExecutionId;

    /**
     * Constructs the adapter.
     *
     * @param caseService case service to use.
     * @param caseExecutionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param keyClazz       class of the key variable.
     * @param valueClazz     class of the value variable.
     */
    public MapReadWriteAdapterCaseService(
        CaseService caseService, String caseExecutionId, String variableName, Class<K> keyClazz, Class<V> valueClazz) {
        super(variableName, keyClazz, valueClazz);
        this.caseService = caseService;
        this.caseExecutionId = caseExecutionId;
    }

    @Override
    public Optional<Map<K, V>> getOptional() {
        return Optional.ofNullable(getOrNull(caseService.getVariable(caseExecutionId, variableName)));
    }

    @Override
    public void set(Map<K, V> value, boolean isTransient) {
      caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Map<K, V>> getLocalOptional() {
        return Optional.ofNullable(getOrNull(caseService.getVariableLocal(caseExecutionId, variableName)));
    }

    @Override
    public void setLocal(Map<K, V> value, boolean isTransient) {
      caseService.setVariableLocal(caseExecutionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public void remove() {
      caseService.removeVariable(caseExecutionId, variableName);
    }

    @Override
    public void removeLocal() {
      caseService.removeVariableLocal(caseExecutionId, variableName);
    }
}
