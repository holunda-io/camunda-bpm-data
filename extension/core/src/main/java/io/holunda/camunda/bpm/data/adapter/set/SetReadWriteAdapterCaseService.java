package io.holunda.camunda.bpm.data.adapter.set;

import org.camunda.bpm.engine.CaseService;

import java.util.Optional;
import java.util.Set;

/**
 * Read write adapter for runtime service access.
 *
 * @param <T> type of value.
 */
public class SetReadWriteAdapterCaseService<T> extends AbstractSetReadWriteAdapter<T> {

    private final CaseService caseService;
    private final String caseExecutionId;

    /**
     * Constructs the adapter.
     *
     * @param caseService   case service to use.
     * @param caseExecutionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param memberClazz    class of the variable.
     */
    public SetReadWriteAdapterCaseService(CaseService caseService, String caseExecutionId, String variableName, Class<T> memberClazz) {
        super(variableName, memberClazz);
        this.caseService = caseService;
        this.caseExecutionId = caseExecutionId;
    }

    @Override
    public Optional<Set<T>> getOptional() {
        return Optional.ofNullable(getOrNull(caseService.getVariable(caseExecutionId, variableName)));
    }

    @Override
    public void set(Set<T> value, boolean isTransient) {
        caseService.setVariable(caseExecutionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Set<T>> getLocalOptional() {
        return Optional.ofNullable(getOrNull(caseService.getVariableLocal(caseExecutionId, variableName)));
    }

    @Override
    public void setLocal(Set<T> value, boolean isTransient) {
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
