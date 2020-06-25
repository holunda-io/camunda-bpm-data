package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Variable map builder allowing for fluent variable setting.
 */
public class VariableMapWriter implements GlobalVariableWriter<VariableMapWriter> {

  private final VariableMap variables;

  /**
   * Creates a builder with provided variable map.
   *
   * @param variables variables to work on.
   */
  public VariableMapWriter(VariableMap variables) {
    this.variables = variables;
  }

  @Override
  @NotNull
  public <T> VariableMapWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  @NotNull
  public <T> VariableMapWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.variables).set(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> VariableMapWriter remove(VariableFactory<T> factory) {
    factory.on(this.variables).remove();
    return this;
  }

  @Override
  @NotNull
  public VariableMap variables() {
    return variables;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VariableMapWriter that = (VariableMapWriter) o;
    return Objects.equals(variables, that.variables);
  }

  @Override
  public int hashCode() {
    return variables != null ? variables.hashCode() : 0;
  }
}
