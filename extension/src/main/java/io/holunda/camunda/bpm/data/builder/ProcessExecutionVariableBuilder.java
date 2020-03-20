package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.function.Function;

/**
 * Process execution builder allowing for fluent variable setting.
 */
public class ProcessExecutionVariableBuilder {

    private final RuntimeService runtimeService;
    private final String executionId;

    /**
     * Creates a builder working on task.
     */
    public ProcessExecutionVariableBuilder(RuntimeService runtimeService, String executionId) {
        this.runtimeService = runtimeService;
        this.executionId = executionId;
    }

    /**
     * Returns the resulting variables.
     * @return variables.
     */
    public VariableMap variables() {
        return this.runtimeService.getVariablesTyped(this.executionId);
    }

    /**
     * Returns the resulting local variables.
     * @return local variables.
     */
    public VariableMap variablesLocal() {
        return this.runtimeService.getVariablesLocalTyped(this.executionId);
    }

    /**
     * Sets the value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param value value to set.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> ProcessExecutionVariableBuilder set(VariableFactory<T> factory, T value) {
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
    public <T> ProcessExecutionVariableBuilder set(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.runtimeService, this.executionId).set(value, isTransient);
        return this;
    }

    /**
     * Sets the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param value value to set.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> ProcessExecutionVariableBuilder setLocal(VariableFactory<T> factory, T value) {
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
    public <T> ProcessExecutionVariableBuilder setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.runtimeService, this.executionId).setLocal(value, isTransient);
        return this;
    }

    /**
     * Removes the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> ProcessExecutionVariableBuilder remove(VariableFactory<T> factory) {
        factory.on(this.runtimeService, this.executionId).remove();
        return this;
    }

    /**
     * Removes the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> ProcessExecutionVariableBuilder removeLocal(VariableFactory<T> factory) {
        factory.on(this.runtimeService, this.executionId).removeLocal();
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
    public <T> ProcessExecutionVariableBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.runtimeService, this.executionId).update(valueProcessor, isTransient);
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
    public <T> ProcessExecutionVariableBuilder updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.runtimeService, this.executionId).updateLocal(valueProcessor, isTransient);
        return this;
    }

    /**
     * Updates the value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param valueProcessor processor for the value.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> ProcessExecutionVariableBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.update(factory, valueProcessor, false);
    }

    /**
     * Update the local value for the provided variable and returns the builder (fluently).
     * @param factory variable factory.
     * @param valueProcessor processor for the value.
     * @param <T> value type.
     * @return fluent builder.
     */
    public <T> ProcessExecutionVariableBuilder updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.updateLocal(factory, valueProcessor, false);
    }

}
