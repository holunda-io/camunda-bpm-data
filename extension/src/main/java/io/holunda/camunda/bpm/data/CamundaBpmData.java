package io.holunda.camunda.bpm.data;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import java.util.Date;

/**
 * Factory methods for creating variable factories.
 */
public class CamundaBpmData {
  /**
   * Hide the instantiations.
   */
  private CamundaBpmData() { }

  /**
   * Create string variable factory.
   * @param variableName name of the variable.
   * @return variable factory for string.
   */
  public static VariableFactory<String> stringVariable(String variableName) {
    return new VariableFactory<>(variableName, String.class);
  }

  /**
   * Create date variable factory.
   * @param variableName name of the variable.
   * @return variable factory for date.
   */
  public static VariableFactory<Date> dateVariable(String variableName) {
    return new VariableFactory<>(variableName, Date.class);
  }

  /**
   * Create integer variable factory.
   * @param variableName name of the variable.
   * @return variable factory for integer.
   */
  public static VariableFactory<Integer> intVariable(String variableName) {
    return new VariableFactory<>(variableName, Integer.class);
  }

  /**
   * Create long variable factory.
   * @param variableName name of the variable.
   * @return variable factory for long.
   */
  public static VariableFactory<Long> longVariable(String variableName) {
    return new VariableFactory<>(variableName, Long.class);
  }

  /**
   * Create short variable factory.
   * @param variableName name of the variable.
   * @return variable factory for short.
   */
  public static VariableFactory<Short> shortVariable(String variableName) {
    return new VariableFactory<>(variableName, Short.class);
  }

  /**
   * Create double variable factory.
   * @param variableName name of the variable.
   * @return variable factory for double.
   */
  public static VariableFactory<Double> doubleVariable(String variableName) {
    return new VariableFactory<>(variableName, Double.class);
  }

  /**
   * Create boolean variable factory.
   * @param variableName name of the variable.
   * @return variable factory for boolean.
   */
  public static VariableFactory<Boolean> booleanVariable(String variableName) {
    return new VariableFactory<>(variableName, Boolean.class);
  }

  /**
   * Create variable factory for custom type.
   * @param variableName name of the variable.
   * @param clazz class of specifying the type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  public static <T> VariableFactory<T> customVariable(String variableName, Class<T> clazz) {
    return new VariableFactory<>(variableName, clazz);
  }
}
