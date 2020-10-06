package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Inverting calls to {@link io.holunda.camunda.bpm.data.adapter.ReadAdapter}.
 */
public interface VariableReader {

  /**
   * Uses {@link ReadAdapter#getOptional()} to access variable value.
   *
   * @param variableFactory the variable to read
   * @param <T>             type of value
   * @return value of variable or empty()
   */
  @NotNull <T> Optional<T> getOptional(VariableFactory<T> variableFactory);

  /**
   * Uses {@link ReadAdapter#get()}  to access variable value.
   *
   * @param variableFactory the variable to read
   * @param <T>             type of value
   * @return value of variable
   * @throws IllegalStateException if variable is not set
   */
  @NotNull <T> T get(VariableFactory<T> variableFactory);

  /**
   * Uses {@link ReadAdapter#getLocal()} to access variable value.
   *
   * @param variableFactory the variable to read
   * @param <T>             type of value
   * @return value of variable
   * @throws IllegalStateException if variable is not set
   */
  @NotNull <T> T getLocal(VariableFactory<T> variableFactory);

  /**
   * Uses {@link ReadAdapter#getLocalOptional()} ()}  to access variable value.
   *
   * @param variableFactory the variable to read
   * @param <T>             type of value
   * @return value of variable or empty()
   */
  @NotNull <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory);

}
