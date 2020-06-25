package io.holunda.camunda.bpm.data.adapter.set;

import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.Optional;
import java.util.Set;

/**
 * Read-write adapter for variable scope.
 *
 * @param <T> type of value.
 */
public class SetReadWriteAdapterVariableScope<T> extends AbstractSetReadWriteAdapter<T> {

    private final VariableScope variableScope;

    /**
     * Constructs the adapter.
     *
     * @param variableScope variable scope to access.
     * @param variableName  variable to access.
     * @param memberClazz   class of member variable value.
     */
    public SetReadWriteAdapterVariableScope(VariableScope variableScope, String variableName, Class<T> memberClazz) {
        super(variableName, memberClazz);
        this.variableScope = variableScope;
    }

    @Override
    public Optional<Set<T>> getOptional() {
        return Optional.ofNullable(getOrNull(variableScope.getVariable(variableName)));
    }

    @Override
    public void set(Set<T> value, boolean isTransient) {
        variableScope.setVariable(variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Set<T>> getLocalOptional() {
        return Optional.ofNullable(getOrNull(variableScope.getVariableLocal(variableName)));
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

}
