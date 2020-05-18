package io.holunda.camunda.bpm.data.adapter.wrapped.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class WrappedMapReadWriteAdapterRuntimeService<K, V> extends AbstractReadWriteAdapter<Map<K, V>> {

    private final RuntimeService runtimeService;
    private final String executionId;
    private final Class<K> keyClazz;
    private final Class<V> valueClazz;
    private final ObjectMapper objectMapper;
    private final MapType mapType;

    /**
     * Constructs the adapter.
     *
     * @param runtimeService runtime service to use.
     * @param executionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param keyClazz       class of the variable key.
     * @param valueClazz     class of the variable value.
     */
    public WrappedMapReadWriteAdapterRuntimeService(
        RuntimeService runtimeService, String executionId, String variableName, Class<K> keyClazz, Class<V> valueClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.runtimeService = runtimeService;
        this.executionId = executionId;
        this.keyClazz = keyClazz;
        this.valueClazz = valueClazz;
        this.objectMapper = objectMapper;
        this.mapType = objectMapper.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<Map<K, V>> getOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(runtimeService.getVariableTyped(executionId, variableName, false)));
    }

    @Override
    public void set(Map<K, V> value, boolean isTransient) {
        runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Map<K, V>> getLocalOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(runtimeService.getVariableLocalTyped(executionId, variableName, false)));
    }

    @Override
    public void setLocal(Map<K, V> value, boolean isTransient) {
        runtimeService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public void remove() {
        runtimeService.removeVariable(executionId, variableName);
    }

    @Override
    public void removeLocal() {
        runtimeService.removeVariableLocal(executionId, variableName);
    }


    /**
     * Retrieves the value or null.
     *
     * @param typedValue raw value.
     *
     * @return set or null.
     */
    protected Map<K, V> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, keyClazz, valueClazz, objectMapper, mapType);
    }

}
