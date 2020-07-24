package io.holunda.camunda.bpm.data.adapter.basic;

import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.Objects;
import java.util.Optional;

/**
 * Read-write adapter for variable scope.
 *
 * @param <T> type of value.
 */
public class ReadWriteAdapterVariableScope<T> extends AbstractBasicReadWriteAdapter<T> {

    private final VariableScope variableScope;

    /**
     * Constructs the adapter.
     *
     * @param variableScope variable scope to access.
     * @param variableName  variable to access.
     * @param clazz         class of variable value.
     */
    public ReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> clazz) {
        super(variableName, clazz);
        this.variableScope = variableScope;
    }

    @Override
    public void set(T value, boolean isTransient) {
        variableScope.setVariable(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<T> getLocalOptional() {
        return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)));
    }

    @Override
    public void setLocal(T value, boolean isTransient) {
        variableScope.setVariableLocal(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<T> getOptional() {
        return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)));
    }

    @Override
    public void remove() {
        variableScope.removeVariable(variableName);
    }

    @Override
    public void removeLocal() {
        variableScope.removeVariableLocal(variableName);
    }

}
