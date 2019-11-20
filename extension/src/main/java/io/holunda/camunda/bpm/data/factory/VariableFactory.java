package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.ReadWriteAdapterVariableScope;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Typed variable factory.
 * @param <T> type of the factory.
 */
public class VariableFactory<T> {

  private String name;
  private Class<T> clazz;

  /**
   * Creates variable factory for a given type and name.
   * @param name name of the variable.
   * @param clazz class of the type.
   */
  public VariableFactory(String name, Class<T> clazz) {
    this.name = name;
    this.clazz = clazz;
  }

  /**
   * Creates a write adapter for variable scope.
   * @param variableScope underlying scope to work on.
   * @return write adapter.
   */
  public WriteAdapter<T> on(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  /**
   * Creates a read adapter on variable scope.
   * @param variableScope underlying scope to work on.
   * @return read adapter.
   */
  public ReadAdapter<T> from(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  /**
   * Creates a write adapter for variable map.
   * @param variableMap underlying scope to work on.
   * @return write adapter.
   */
  public WriteAdapter<T> on(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  /**
   * Creates a read adapter on variable scope.
   * @param variableMap underlying map to work on.
   * @return read adapter.
   */
  public ReadAdapter<T> from(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  /**
   * Retrieves the variable name.
   * @return name of the variable.
   */
  public String getName() {
    return this.name;
  }
}
