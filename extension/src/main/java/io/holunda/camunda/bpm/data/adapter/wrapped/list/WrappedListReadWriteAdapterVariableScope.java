package io.holunda.camunda.bpm.data.adapter.wrapped.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;
import java.util.List;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class WrappedListReadWriteAdapterVariableScope<T> extends AbstractReadWriteAdapter<List<T>> {

    private final Class<T> memberClazz;
    private final VariableScope variableScope;
    private final ObjectMapper objectMapper;
    private final CollectionType collectionType;

    /**
     * Constructs the adapter.
     *
     * @param variableScope scope to save into.
     * @param variableName  name of the variable.
     * @param memberClazz   class of the variable.
     */
    public WrappedListReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> memberClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.memberClazz = memberClazz;
        this.variableScope = variableScope;
        this.objectMapper = objectMapper;
        this.collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, memberClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<List<T>> getOptional() {
        return Optional.ofNullable(
            getOrNullFromTypedValue(
                variableScope.getVariableTyped(variableName, false)
            )
        );
    }

    @Override
    public void set(List<T> value, boolean isTransient) {
        variableScope.setVariable(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<List<T>> getLocalOptional() {
        return Optional.ofNullable(
            getOrNullFromTypedValue(
                variableScope.getVariableLocalTyped(variableName, false)
            )
        );
    }

    @Override
    public void setLocal(List<T> value, boolean isTransient) {
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
    protected List<T> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, memberClazz, objectMapper, collectionType);
    }

}
