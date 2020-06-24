package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.Optional;

/**
 * Inverting calls to {@link io.holunda.camunda.bpm.data.adapter.ReadAdapter}.
 */
public interface VariableReader {

  <T> Optional<T> getOptional(VariableFactory<T> variableFactory);

  <T> T get(VariableFactory<T> variableFactory);

  <T> T getLocal(VariableFactory<T> variableFactory);

  <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory);

}
