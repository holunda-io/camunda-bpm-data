package io.holunda.camunda.bpm.data.adapter.set;

import org.camunda.bpm.engine.RuntimeService;

import java.util.Optional;
import java.util.Set;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class SetReadWriteAdapterRuntimeService<T> extends AbstractSetReadWriteAdapter<T> {

    private final RuntimeService runtimeService;
    private final String executionId;

    /**
     * Constructs the adapter.
     *
     * @param runtimeService runtime service to use.
     * @param executionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param memberClazz    class of the variable.
     */
    public SetReadWriteAdapterRuntimeService(RuntimeService runtimeService, String executionId, String variableName, Class<T> memberClazz) {
        super(variableName, memberClazz);
        this.runtimeService = runtimeService;
        this.executionId = executionId;
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

}
