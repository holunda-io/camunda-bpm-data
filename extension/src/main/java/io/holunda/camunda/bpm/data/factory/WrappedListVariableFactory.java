package io.holunda.camunda.bpm.data.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.list.ListReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.wrapped.list.WrappedListReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.wrapped.list.WrappedListReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.wrapped.list.WrappedListReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Variable factory of a base parametrized list type.
 *
 * @param <T> member type of the factory.
 */
public class WrappedListVariableFactory<T> implements VariableFactory<List<T>> {

    @NotNull
    private final String name;
    @NotNull
    private final Class<T> memberClazz;
    @NotNull
    private final ObjectMapper objectMapper;

    public WrappedListVariableFactory(@NotNull String name, @NotNull Class<T> memberClazz, @NotNull ObjectMapper objectMapper) {
        this.name = name;
        this.memberClazz = memberClazz;
        this.objectMapper = objectMapper;
    }

    @Override
    public WriteAdapter<List<T>> on(VariableScope variableScope) {
        return new WrappedListReadWriteAdapterVariableScope<>(variableScope, name, memberClazz, objectMapper);
    }

    @Override
    public ReadAdapter<List<T>> from(VariableScope variableScope) {
        return new WrappedListReadWriteAdapterVariableScope<>(variableScope, name, memberClazz, objectMapper);
    }

    @Override
    public WriteAdapter<List<T>> on(VariableMap variableMap) {
        return new ListReadWriteAdapterVariableMap<>(variableMap, name, memberClazz);
    }

    @Override
    public ReadAdapter<List<T>> from(VariableMap variableMap) {
        return new ListReadWriteAdapterVariableMap<>(variableMap, name, memberClazz);
    }

    @Override
    public WriteAdapter<List<T>> on(RuntimeService runtimeService, String executionId) {
        return new WrappedListReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, memberClazz, objectMapper);
    }

    @Override
    public ReadAdapter<List<T>> from(RuntimeService runtimeService, String executionId) {
        return new WrappedListReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, memberClazz, objectMapper);
    }

    @Override
    public WriteAdapter<List<T>> on(TaskService taskService, String taskId) {
        return new WrappedListReadWriteAdapterTaskService<>(taskService, taskId, name, memberClazz, objectMapper);
    }

    @Override
    public ReadAdapter<List<T>> from(TaskService taskService, String taskId) {
        return new WrappedListReadWriteAdapterTaskService<>(taskService, taskId, name, memberClazz, objectMapper);
    }

    @Override
    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Class<T> getMemberClass() {
        return memberClazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WrappedListVariableFactory<?> that = (WrappedListVariableFactory<?>) o;
        return name.equals(that.name) &&
            memberClazz.equals(that.memberClazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, memberClazz);
    }
}
