package io.holunda.camunda.bpm.data.adapter.wrapped.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class WrappedListReadWriteAdapterRuntimeService<T> extends AbstractReadWriteAdapter<List<T>> {

    private final RuntimeService runtimeService;
    private final String executionId;
    private final Class<T> memberClazz;
    private final ObjectMapper objectMapper;
    private final CollectionType collectionType;

    /**
     * Constructs the adapter.
     *
     * @param runtimeService runtime service to use.
     * @param executionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param memberClazz    class of the variable.
     */
    public WrappedListReadWriteAdapterRuntimeService(RuntimeService runtimeService, String executionId, String variableName, Class<T> memberClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.runtimeService = runtimeService;
        this.executionId = executionId;
        this.memberClazz = memberClazz;
        this.objectMapper = objectMapper;
        this.collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, memberClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<List<T>> getOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(runtimeService.getVariableTyped(executionId, variableName, false)));
    }

    @Override
    public void set(List<T> value, boolean isTransient) {
        runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<List<T>> getLocalOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(runtimeService.getVariableLocalTyped(executionId, variableName, false)));
    }

    @Override
    public void setLocal(List<T> value, boolean isTransient) {
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
    protected List<T> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, memberClazz, objectMapper, collectionType);
    }

}
