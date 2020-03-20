package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.function.Function;

/**
 * User task builder allowing for fluent variable setting.
 */
public class UserTaskVariableBuilder {

    private final TaskService taskService;
    private final String taskId;

    /**
     * Creates a builder working on a user task.
     */
    public UserTaskVariableBuilder(TaskService taskService, String taskId) {
        this.taskService = taskService;
        this.taskId = taskId;
    }

    /**
     * Returns the resulting variables.
     * @return variables.
     */
    public VariableMap variables() {
        return this.taskService.getVariablesTyped(this.taskId);
    }

    /**
     * Returns the resulting local variables.
     * @return local variables.
     */
    public VariableMap variablesLocal() {
        return this.taskService.getVariablesLocalTyped(this.taskId);
    }

    /**
     * Sets the value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param value value to set.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder set(VariableFactory<T> factory, T value) {
        return this.set(factory, value, false);
    }

    /**
     * Sets the value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param value value to set.
     * @param isTransient transient flag.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder set(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.taskService, this.taskId).set(value, isTransient);
        return this;
    }

    /**
     * Sets the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param value value to set.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder setLocal(VariableFactory<T> factory, T value) {
        return this.setLocal(factory, value, false);
    }

    /**
     * Sets the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param value value to set.
     * @param isTransient transient flag.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.taskService, this.taskId).setLocal(value, isTransient);
        return this;
    }

    /**
     * Removes the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder remove(VariableFactory<T> factory) {
        factory.on(this.taskService, this.taskId).remove();
        return this;
    }

    /**
     * Removes the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder removeLocal(VariableFactory<T> factory) {
        factory.on(this.taskService, this.taskId).removeLocal();
        return this;
    }

    /**
     * Updates the value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param valueProcessor processor for the value.
     * @param isTransient transient flag.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.taskService, this.taskId).update(valueProcessor, isTransient);
        return this;
    }

    /**
     * Update the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param valueProcessor processor for the value.
     * @param isTransient transient flag.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.taskService, this.taskId).updateLocal(valueProcessor, isTransient);
        return this;
    }

    /**
     * Updates the value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param valueProcessor processor for the value.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.update(factory, valueProcessor, false);
    }

    /**
     * Update the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param valueProcessor processor for the value.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> UserTaskVariableBuilder updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.updateLocal(factory, valueProcessor, false);
    }

}
