package io.holunda.camunda.bpm.data.adapter.wrapped.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Map;
import java.util.Optional;

import static org.camunda.bpm.engine.variable.Variables.untypedValue;

/**
 * Read write adapter for task service access.
 *
 * @param <K> type of key.
 * @param <V> type of value.
 */
public class WrappedMapReadWriteAdapterTaskService<K, V> extends AbstractReadWriteAdapter<Map<K, V>> {

    private final TaskService taskService;
    private final String executionId;
    private final Class<K> keyClazz;
    private final Class<V> valueClazz;
    private final ObjectMapper objectMapper;
    private final MapType mapType;

    /**
     * Constructs the adapter.
     *
     * @param taskService  task service to use.
     * @param executionId  id of the execution to read from and write to.
     * @param variableName name of the variable.
     * @param keyClazz     class of the variable key.
     * @param valueClazz   class of the variable value.
     */
    public WrappedMapReadWriteAdapterTaskService(
        TaskService taskService, String executionId, String variableName, Class<K> keyClazz, Class<V> valueClazz, ObjectMapper objectMapper) {
        super(variableName);
        this.taskService = taskService;
        this.executionId = executionId;
        this.keyClazz = keyClazz;
        this.valueClazz = valueClazz;
        this.objectMapper = objectMapper;
        this.mapType = objectMapper.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return untypedValue(value, isTransient);
    }

    @Override
    public Optional<Map<K, V>> getOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(taskService.getVariableTyped(executionId, variableName, false)));
    }

    @Override
    public void set(Map<K, V> value, boolean isTransient) {
        taskService.setVariable(executionId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<Map<K, V>> getLocalOptional() {
        return Optional.ofNullable(getOrNullFromTypedValue(taskService.getVariableLocalTyped(executionId, variableName, false)));
    }

    @Override
    public void setLocal(Map<K, V> value, boolean isTransient) {
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
    protected Map<K, V> getOrNullFromTypedValue(TypedValue typedValue) {
        return ValueWrapperUtil.readFromTypedValue(typedValue, variableName, keyClazz, valueClazz, objectMapper, mapType);
    }

}
