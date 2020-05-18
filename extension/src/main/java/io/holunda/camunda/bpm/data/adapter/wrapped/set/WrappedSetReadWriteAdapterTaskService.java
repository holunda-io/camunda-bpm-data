package io.holunda.camunda.bpm.data.adapter.wrapped.set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Optional;
import java.util.Set;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for task service access.
 *
 * @param <T> type of value.
 */
public class WrappedSetReadWriteAdapterTaskService<T> extends AbstractReadWriteAdapter<Set<T>> {

    private final TaskService taskService;
    private final String executionId;
    private final Class<T> memberClazz;
    private final ObjectMapper objectMapper;
    private final CollectionType collectionType;

    /**
     * Constructs the adapter.
     *
     * @param taskService    task service to use.
     * @param executionId    id of the execution to read from and write to.
     * @param variableName   name of the variable.
     * @param memberClazz    class of the variable.
     */
    public WrappedSetReadWriteAdapterTaskService(TaskService taskService, String executionId, String variableName, Class<T> memberClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.taskService = taskService;
        this.executionId = executionId;
        this.memberClazz = memberClazz;
        this.objectMapper = objectMapper;
        this.collectionType = objectMapper.getTypeFactory().constructCollectionType(Set.class, memberClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<Set<T>> getOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(taskService.getVariableTyped(executionId, variableName, false)));
    }

    @Override
    public void set(Set<T> value, boolean isTransient) {
        taskService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Set<T>> getLocalOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(taskService.getVariableLocalTyped(executionId, variableName, false)));
    }

    @Override
    public void setLocal(Set<T> value, boolean isTransient) {
        taskService.setVariableLocal(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public void remove() {
        taskService.removeVariable(executionId, variableName);
    }

    @Override
    public void removeLocal() {
        taskService.removeVariableLocal(executionId, variableName);
    }


    /**
     * Retrieves the value or null.
     *
     * @param typedValue raw value.
     *
     * @return set or null.
     */
    protected Set<T> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, memberClazz, objectMapper, collectionType);
    }

}
