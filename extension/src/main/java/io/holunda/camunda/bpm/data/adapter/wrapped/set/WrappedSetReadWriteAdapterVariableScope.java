package io.holunda.camunda.bpm.data.adapter.wrapped.set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;
import java.util.Set;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class WrappedSetReadWriteAdapterVariableScope<T> extends AbstractReadWriteAdapter<Set<T>> {

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
    public WrappedSetReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> memberClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.memberClazz = memberClazz;
        this.variableScope = variableScope;
        this.objectMapper = objectMapper;
        this.collectionType = objectMapper.getTypeFactory().constructCollectionType(Set.class, memberClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<Set<T>> getOptional() {
        return Optional.ofNullable(
            getOrNullFromTypedValue(
                variableScope.getVariableTyped(variableName, false)
            )
        );
    }

    @Override
    public void set(Set<T> value, boolean isTransient) {
        variableScope.setVariable(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Set<T>> getLocalOptional() {
        return Optional.ofNullable(
            getOrNullFromTypedValue(
                variableScope.getVariableLocalTyped(variableName, false)
            )
        );
    }

    @Override
    public void setLocal(Set<T> value, boolean isTransient) {
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
     * @param typedValue serialized version represented as json.
     *
     * @return set or null.
     */
    protected Set<T> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, memberClazz, objectMapper, collectionType);
    }

}
