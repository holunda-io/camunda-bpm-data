package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.bpm.data.writer.VariableMapWriter;
import java.util.Collections;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.jetbrains.annotations.NotNull;

/**
 * Builder to create {@link VariableMap} using {@link VariableFactory}.
 */
public class VariableMapBuilder {

  private final VariableMapWriter writer;

  /**
   * Constructs a builder.
   */
  public VariableMapBuilder() {
    this.writer = new VariableMapWriter(Variables.createVariables());
  }

  /**
   * Sets the value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value the value
   * @param <T> type of value
   * @return current builder instance
   */
  @NotNull
  public <T> VariableMapBuilder set(VariableFactory<T> variableFactory, T value) {
    writer.set(variableFactory, value);
    return this;
  }

  /**
   * Sets the (transient) value for the provided variable and returns the builder (fluently).
   *
   * @param variableFactory the variable
   * @param value the value
   * @param isTransient if true, the variable is transient, default false.
   * @param <T> type of value
   * @return current builder instance
   */
  @NotNull
  public <T> VariableMapBuilder set(VariableFactory<T> variableFactory, T value, boolean isTransient) {
    writer.set(variableFactory, value, isTransient);
    return this;
  }

  /**
   * @return instance of {@link VariableMap} containing set values
   */
  @NotNull
  public VariableMap build() {
    return Variables.fromMap(Collections.unmodifiableMap(writer.variables()));
  }
}
