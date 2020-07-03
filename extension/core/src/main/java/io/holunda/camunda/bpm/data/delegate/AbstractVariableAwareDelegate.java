package io.holunda.camunda.bpm.data.delegate;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.bpm.data.reader.VariableReader;
import io.holunda.camunda.bpm.data.reader.VariableScopeReader;
import io.holunda.camunda.bpm.data.writer.VariableScopeWriter;
import io.holunda.camunda.bpm.data.writer.VariableWriter;
import java.util.Optional;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

abstract class AbstractVariableAwareDelegate implements VariableReader, VariableWriter<VariableScopeWriter> {

  protected final VariableScopeReader reader;
  protected final VariableScopeWriter writer;

  protected AbstractVariableAwareDelegate(VariableScope variableScope) {
    this(new VariableScopeReader(variableScope), new VariableScopeWriter(variableScope));
  }

  protected AbstractVariableAwareDelegate(VariableScopeReader reader, VariableScopeWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  @Override
  @NotNull
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return reader.getOptional(variableFactory);
  }

  @Override
  @NotNull
  public <T> T get(VariableFactory<T> variableFactory) {
    return reader.get(variableFactory);
  }

  @Override
  @NotNull
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return reader.getLocal(variableFactory);
  }

  @Override
  @NotNull
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return reader.getLocalOptional(variableFactory);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter set(VariableFactory<T> factory, T value) {
    return writer.set(factory, value);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter set(VariableFactory<T> factory, T value, boolean isTransient) {
    return writer.set(factory, value, isTransient);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter setLocal(VariableFactory<T> factory, T value) {
    return writer.setLocal(factory, value);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter setLocal(VariableFactory<T> factory, T value, boolean isTransient) {
    return writer.setLocal(factory, value, isTransient);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter remove(VariableFactory<T> factory) {
    return writer.remove(factory);
  }

  @Override
  @NotNull
  public <T> VariableScopeWriter removeLocal(VariableFactory<T> factory) {
    return writer.removeLocal(factory);
  }

  @Override
  @NotNull
  public VariableMap variables() {
    return writer.variables();
  }

  @Override
  @NotNull
  public VariableMap variablesLocal() {
    return writer.variablesLocal();
  }
}
