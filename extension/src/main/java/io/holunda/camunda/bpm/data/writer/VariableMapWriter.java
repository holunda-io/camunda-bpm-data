package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Variable map builder allowing for fluent variable setting.
 */
public class VariableMapWriter implements VariableWriter<VariableMapWriter> {

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
  public <T> VariableMapWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  public <T> VariableMapWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.variables).set(value, isTransient);
    return this;
  }

  @Override
  public <T> VariableMapWriter remove(VariableFactory<T> factory) {
    factory.on(this.variables).remove();
    return this;
  }

  @Override
  public VariableMap variables() {
    return variables;
  }
}
