package io.holunda.camunda.bpm.data.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.set.SetReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.wrapped.set.WrappedSetReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.wrapped.set.WrappedSetReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.wrapped.set.WrappedSetReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

/**
 * Variable factory of a base parametrized set type.
 *
 * @param <T> member type of the factory.
 */
public class WrappedSetVariableFactory<T> implements VariableFactory<Set<T>> {

    @NotNull
    private final String name;
    @NotNull
    private final Class<T> memberClazz;
    @NotNull
    private final ObjectMapper objectMapper;

    public WrappedSetVariableFactory(@NotNull String name, @NotNull Class<T> memberClazz, @NotNull ObjectMapper objectMapper) {
        this.name = name;
        this.memberClazz = memberClazz;
        this.objectMapper = objectMapper;
    }

    @Override
    public WriteAdapter<Set<T>> on(VariableScope variableScope) {
      return new WrappedSetReadWriteAdapterVariableScope<>(variableScope, name, memberClazz, objectMapper);
    }

    @Override
    public ReadAdapter<Set<T>> from(VariableScope variableScope) {
      return new WrappedSetReadWriteAdapterVariableScope<>(variableScope, name, memberClazz, objectMapper);
    }

    @Override
    public WriteAdapter<Set<T>> on(VariableMap variableMap) {
      return new SetReadWriteAdapterVariableMap<>(variableMap, name, memberClazz);
    }

    @Override
    public ReadAdapter<Set<T>> from(VariableMap variableMap) {
      return new SetReadWriteAdapterVariableMap<>(variableMap, name, memberClazz);
    }

    @Override
    public WriteAdapter<Set<T>> on(RuntimeService runtimeService, String executionId) {
        return new WrappedSetReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, memberClazz, objectMapper);
    }

    @Override
    public ReadAdapter<Set<T>> from(RuntimeService runtimeService, String executionId) {
        return new WrappedSetReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, memberClazz, objectMapper);
    }

    @Override
    public WriteAdapter<Set<T>> on(TaskService taskService, String taskId) {
        return new WrappedSetReadWriteAdapterTaskService<>(taskService, taskId, name, memberClazz, objectMapper);
    }

    @Override
    public ReadAdapter<Set<T>> from(TaskService taskService, String taskId) {
        return new WrappedSetReadWriteAdapterTaskService<>(taskService, taskId, name, memberClazz, objectMapper);
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
        WrappedSetVariableFactory<?> that = (WrappedSetVariableFactory<?>) o;
        return name.equals(that.name) &&
            memberClazz.equals(that.memberClazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, memberClazz);
    }
}
