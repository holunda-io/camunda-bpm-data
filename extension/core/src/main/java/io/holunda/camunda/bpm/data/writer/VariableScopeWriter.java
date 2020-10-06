package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * Variable scope builder allowing for fluent variable setting.
 */
public class VariableScopeWriter implements VariableWriter<VariableScopeWriter> {

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
  @NotNull
  public <T> VariableScopeWriter set(VariableFactory<T> factory, T value) {
    return this.set(factory, value, false);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.scope).set(value, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter setLocal(VariableFactory<T> factory, T value) {
    return this.setLocal(factory, value, false);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    factory.on(this.scope).setLocal(value, isTransient);
    return this;
  }

  @Override
  public @NotNull <T> VariableScopeWriter update(VariableFactory<T> factory, Function<T, T> valueProcessor) {
    factory.on(this.scope).update(valueProcessor);
    return this;
  }

  @Override
  public @NotNull <T> VariableScopeWriter update(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
    factory.on(this.scope).update(valueProcessor, isTransient);
    return this;
  }

  @Override
  public @NotNull <T> VariableScopeWriter updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor) {
    factory.on(this.scope).updateLocal(valueProcessor);
    return this;
  }

  @Override
  public @NotNull <T> VariableScopeWriter updateLocal(VariableFactory<T> factory, Function<T, T> valueProcessor, boolean isTransient) {
    factory.on(this.scope).updateLocal(valueProcessor, isTransient);
    return this;
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter remove(VariableFactory<T> factory) {
    factory.on(this.scope).remove();
    return this;
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter removeLocal(VariableFactory<T> factory) {
    factory.on(this.scope).removeLocal();
    return this;
  }

  @Override
  @NotNull
  public VariableMap variables() {
    return scope.getVariablesTyped();
  }

  @Override
  @NotNull
  public VariableMap variablesLocal() {
    return scope.getVariablesLocalTyped();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VariableScopeWriter that = (VariableScopeWriter) o;
    return Objects.equals(scope, that.scope);
  }

  @Override
  public int hashCode() {
    return scope != null ? scope.hashCode() : 0;
  }
}
