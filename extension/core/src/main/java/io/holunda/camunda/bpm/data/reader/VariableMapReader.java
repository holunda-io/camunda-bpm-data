package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * Allows reading multiple variable values from {@link VariableMap#getValue(String, Class)}.
 */
public class VariableMapReader implements VariableReader {

  private final VariableMap variableMap;

  /**
   * Constructs the reader using the specified variable map.
   * @param variableMap map to operate on.
   */
  public VariableMapReader(VariableMap variableMap) {
    this.variableMap = variableMap;
  }

  @Override
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableMap).getOptional();
  }

  @Override
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableMap).get();
  }

  @Override
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableMap).getLocal();
  }

  @Override
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(variableMap).getLocalOptional();
  }
}
