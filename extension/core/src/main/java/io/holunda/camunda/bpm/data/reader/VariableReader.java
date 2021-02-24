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


  /**
   * Uses {@link ReadAdapter#getOptional()} to access variable value. If the optional is empty returns <code>null</code>
   *
   * @param variableFactory the variable to read
   * @param <T>             type of value
   * @return value of variable or <code>null</code> if the variable is not present or has value <code>null</code>.
   */
  default <T> T getOrNull(VariableFactory<T> variableFactory) {
    return getOptional(variableFactory).orElse(null);
  }

  /**
   * Reads a variable and returns a value if exists or returns the provided default.
   *
   * @param variableFactory   the variable to read
   * @param defaultValue      the default value if the variable is not set
   * @return value or default
   */
  default <T> T getOrDefault(VariableFactory<T> variableFactory, T defaultValue) {
    return getOptional(variableFactory).orElse(defaultValue);
  }

  /**
   * Uses {@link ReadAdapter#getLocalOptional()} to access variable value. If the optional is empty returns <code>null</code>
   *
   * @param variableFactory the variable to read
   * @param <T>             type of value
   * @return value of variable or <code>null</code> if the variable is not present or has value <code>null</code>.
   */
  default <T> T getLocalOrNull(VariableFactory<T> variableFactory) {
    return getLocalOptional(variableFactory).orElse(null);
  }

  /**
   * Reads a local variable and returns a value if exists or returns the provided default.
   *
   * @param variableFactory   the variable to read
   * @param defaultValue      the default value if the variable is not set
   * @return value or default
   */
  default <T> T getLocalOrDefault(VariableFactory<T> variableFactory, T defaultValue) {
    return getLocalOptional(variableFactory).orElse(defaultValue);
  }

}
