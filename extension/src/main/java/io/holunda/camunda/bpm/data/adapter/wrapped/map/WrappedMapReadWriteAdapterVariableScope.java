package io.holunda.camunda.bpm.data.adapter.wrapped.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Map;
import java.util.Optional;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for runtime service access.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class WrappedMapReadWriteAdapterVariableScope<K, V> extends AbstractReadWriteAdapter<Map<K, V>> {

    private final VariableScope variableScope;
    private final ObjectMapper objectMapper;
    private final Class<K> keyClazz;
    private final Class<V> valueClazz;
    private final MapType mapType;

    /**
     * Constructs the adapter.
     *
     * @param variableScope scope to save into.
     * @param variableName  name of the variable.
     */
    public WrappedMapReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<K> keyClazz, Class<V> valueClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.variableScope = variableScope;
        this.objectMapper = objectMapper;
        this.keyClazz = keyClazz;
        this.valueClazz = valueClazz;
        this.mapType = objectMapper.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<Map<K, V>> getOptional() {
        return Optional.ofNullable(
            getOrNullFromTypedValue(
                variableScope.getVariableTyped(variableName, false)
            )
        );
    }

    @Override
    public void set(Map<K, V> value, boolean isTransient) {
        variableScope.setVariable(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Map<K, V>> getLocalOptional() {
        return Optional.ofNullable(
            getOrNullFromTypedValue(
                variableScope.getVariableLocalTyped(variableName, false)
            )
        );
    }

    @Override
    public void setLocal(Map<K, V> value, boolean isTransient) {
        variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public void remove() {
        variableScope.removeVariable(variableName);
    }

    @Override
    public void removeLocal() {
        variableScope.removeVariableLocal(variableName);
    }


    /**
     * Retrieves the value or null.
     *
     * @param typedValue value with type information and serialized json.
     *
     * @return list or null.
     */
    protected Map<K, V> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, keyClazz, valueClazz, objectMapper, mapType);
    }

}
