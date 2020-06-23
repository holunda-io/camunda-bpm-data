package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;

import java.util.function.Function;

import static org.camunda.bpm.engine.variable.Variables.createVariables;

/**
 * Variable map builder allowing for fluent variable setting.
 */
public class VariableMapBuilder {

    private final VariableMap variables;

    /**
     * Creates a builder with empty variables.
     */
    public VariableMapBuilder() {
        this(createVariables());
    }

    /**
     * Creates a builder with provided variable map.
     *
     * @param variables variables to work on.
     */
    public VariableMapBuilder(VariableMap variables) {
        this.variables = variables;
    }

    /**
     * Returns the resulting variables.
     *
     * @return variables.
     */
    public VariableMap build() {
        return this.variables;
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
    public <T> VariableMapBuilder set(VariableFactory<T> factory, T value) {
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
    public <T> VariableMapBuilder set(VariableFactory<T> factory, T value, boolean isTransient) {
        factory.on(this.variables).set(value, isTransient);
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
    public <T> VariableMapBuilder remove(VariableFactory<T> factory) {
        factory.on(this.variables).remove();
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
    public <T> VariableMapBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
        factory.on(this.variables).update(valueProcessor, isTransient);
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
    public <T> VariableMapBuilder update(VariableFactory<T> factory, Function<T, T> valueProcessor) {
        return this.update(factory, valueProcessor, false);
    }

}
