package io.holunda.camunda.bpm.data.adapter.wrapper;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;
import java.util.Set;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class WrappedSetReadWriteAdapterRuntimeService<T> extends AbstractReadWriteAdapter<Set<T>> {

    private final RuntimeService runtimeService;
    private final String executionId;
    private final Class<T> memberClazz;

    /**
     * Constructs the adapter.
     *
     * @param runtimeService runtime service to use.
     * @param executionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param memberClazz    class of the variable.
     */
    public WrappedSetReadWriteAdapterRuntimeService(RuntimeService runtimeService, String executionId, String variableName, Class<T> memberClazz) {
        super(variableName);
        this.runtimeService = runtimeService;
        this.executionId = executionId;
        this.memberClazz = memberClazz;
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(new SetWrapper((Set) value), isTransient);
    }

    @Override
    public Optional<Set<T>> getOptional() {
        return Optional.ofNullable(getOrNull(runtimeService.getVariable(executionId, variableName)));
    }

    @Override
    public void set(Set<T> value, boolean isTransient) {
        runtimeService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Set<T>> getLocalOptional() {
        return Optional.ofNullable(getOrNull(runtimeService.getVariableLocal(executionId, variableName)));
    }

    @Override
    public void setLocal(Set<T> value, boolean isTransient) {
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
     * @param value raw value.
     *
     * @return set or null.
     */
    @SuppressWarnings("unchecked")
    protected Set<T> getOrNull(Object value) {
        if (value == null) {
            return null;
        }
        if (WrapperType.class.isAssignableFrom(value.getClass())) {
            WrapperType<T> wrapper = (WrapperType) value;
            Set<T> valueAsCollection = wrapper.getAsCollection();
            if (valueAsCollection.isEmpty()) {
                return valueAsCollection;
            } else {
                if (memberClazz.isAssignableFrom(valueAsCollection.iterator().next().getClass())) {
                    return valueAsCollection;
                } else {
                    throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong member type detected, expected " + memberClazz.getName() + ", but was not found in " + valueAsCollection);
                }
            }
        }
        throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of wrapper type from " + value);
    }

}
