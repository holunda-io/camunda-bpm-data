package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.function.Function;

/**
 * Variable scope builder allowing for fluent variable setting.
 */
public class VariableScopeBuilder {

    private final VariableScope scope;

    /**
     * Creates a builder with provided variable map.
     * <p>The provided variables are modified by reference.</p>
     *
     * @param variables variables to work on.
     */
    public VariableScopeBuilder(VariableScope variables) {
        this.scope = variables;
    }

    /**
     * Returns the resulting variables.
     *
     * @return variables.
     */
    public VariableScope build() {
        return this.scope;
    }

    /**
     * Sets the value for the provided variable and returns the builder (fluently).
     *
     * @param factory variable factory.
     * @param value   value to set.
     * @param <T>     value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder set(VariableFactory<T> factory, T value) {
        return this.set(factory, value, false);
    }

    /**
     * Sets the value for the provided variable and returns the builder (fluently).
     *
     * @param factory     variable factory.
     * @param value       value to set.
     * @param isTransient transient flag.
     * @param <T>         value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder set(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.scope).set(value, isTransient);
        return this;
    }

    /**
     * Sets the local value for the provided variable and returns the builder (fluently).
     *
     * @param factory variable factory.
     * @param value   value to set.
     * @param <T>     value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder setLocal(VariableFactory<T> factory, T value) {
        return this.setLocal(factory, value, false);
    }

    /**
     * Sets the local value for the provided variable and returns the builder (fluently).
     *
     * @param factory     variable factory.
     * @param value       value to set.
     * @param isTransient transient flag.
     * @param <T>         value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.scope).setLocal(value, isTransient);
        return this;
    }

    /**
     * Removes the local value for the provided variable and returns the builder (fluently).
     *
     * @param factory variable factory.
     * @param <T>     value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder remove(VariableFactory<T> factory) {
        factory.on(this.scope).remove();
        return this;
    }

    /**
     * Removes the local value for the provided variable and returns the builder (fluently).
     *
     * @param factory variable factory.
     * @param <T>     value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder removeLocal(VariableFactory<T> factory) {
        factory.on(this.scope).removeLocal();
        return this;
    }

    /**
     * Updates the value for the provided variable and returns the builder (fluently).
     *
     * @param factory        variable factory.
     * @param valueProcessor processor for the value.
     * @param isTransient    transient flag.
     * @param <T>            value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.scope).update(valueProcessor, isTransient);
        return this;
    }

    /**
     * Update the local value for the provided variable and returns the builder (fluently).
     *
     * @param factory        variable factory.
     * @param valueProcessor processor for the value.
     * @param isTransient    transient flag.
     * @param <T>            value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.scope).update(valueProcessor, isTransient);
        return this;
    }

    /**
     * Updates the value for the provided variable and returns the builder (fluently).
     *
     * @param factory        variable factory.
     * @param valueProcessor processor for the value.
     * @param <T>            value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.update(factory, valueProcessor, false);
    }

    /**
     * Update the local value for the provided variable and returns the builder (fluently).
     *
     * @param factory        variable factory.
     * @param valueProcessor processor for the value.
     * @param <T>            value type.
     *
     * @return fluent builder.
     */
    public <T> VariableScopeBuilder updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.updateLocal(factory, valueProcessor, false);
    }

}
