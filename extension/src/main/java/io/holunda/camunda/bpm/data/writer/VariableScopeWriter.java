package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.function.Supplier;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Variable scope builder allowing for fluent variable setting.
 */
public class VariableScopeWriter implements LocalVariableWriter<VariableScopeWriter> {

  private final VariableScope scope;

  /**
   * Creates a builder with provided variable map.
   * <p>The provided variables are modified by reference.</p>
   *
   * @param variables variables to work on.
   */
  public VariableScopeWriter(VariableScope variables) {
    this.scope = variables;
  }

  @Override
  public <T> VariableScopeWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  public <T> VariableScopeWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.scope).set(value, isTransient);
    return this;
  }

  @Override
  public <T> VariableScopeWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  public <T> VariableScopeWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.scope).setLocal(value, isTransient);
    return this;
  }

  @Override
  public <T> VariableScopeWriter remove(VariableFactory<T> factory) {
    factory.on(this.scope).remove();
    return this;
  }


  @Override
  public <T> VariableScopeWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.scope).removeLocal();
    return this;
  }


  @Override
  public VariableMap variables() {
    return scope.getVariablesTyped();
  }

  @Override
  public VariableMap variablesLocal() {
    return scope.getVariablesLocalTyped();
  }

}