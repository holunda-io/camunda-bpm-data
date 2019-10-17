package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterVariableScope;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.delegate.VariableScope;

public class VariableFactory<T> {

  private String name;
  private Class<T> clazz;

  public VariableFactory(String name, Class<T> clazz) {
    this.name = name;
    this.clazz = clazz;
  }

  public WriteAdapter<T> on(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  public ReadAdapter<T> from(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }
}
