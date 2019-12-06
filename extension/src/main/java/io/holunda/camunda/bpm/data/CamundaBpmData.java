package io.holunda.camunda.bpm.data;

import io.holunda.camunda.bpm.data.factory.BasicVariableFactory;
import io.holunda.camunda.bpm.data.factory.ListVariableFactory;
import io.holunda.camunda.bpm.data.factory.SetVariableFactory;
import io.holunda.camunda.bpm.data.factory.VariableFactory;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
    return new BasicVariableFactory<>(variableName, String.class);
  }

  /**
   * Create date variable factory.
   * @param variableName name of the variable.
   * @return variable factory for date.
   */
  public static VariableFactory<Date> dateVariable(String variableName) {
    return new BasicVariableFactory<>(variableName, Date.class);
  }

  /**
   * Create integer variable factory.
   * @param variableName name of the variable.
   * @return variable factory for integer.
   */
  public static VariableFactory<Integer> intVariable(String variableName) {
    return new BasicVariableFactory<>(variableName, Integer.class);
  }

  /**
   * Create long variable factory.
   * @param variableName name of the variable.
   * @return variable factory for long.
   */
  public static VariableFactory<Long> longVariable(String variableName) {
    return new BasicVariableFactory<>(variableName, Long.class);
  }

  /**
   * Create short variable factory.
   * @param variableName name of the variable.
   * @return variable factory for short.
   */
  public static VariableFactory<Short> shortVariable(String variableName) {
    return new BasicVariableFactory<>(variableName, Short.class);
  }

  /**
   * Create double variable factory.
   * @param variableName name of the variable.
   * @return variable factory for double.
   */
  public static VariableFactory<Double> doubleVariable(String variableName) {
    return new BasicVariableFactory<>(variableName, Double.class);
  }

  /**
   * Create boolean variable factory.
   * @param variableName name of the variable.
   * @return variable factory for boolean.
   */
  public static VariableFactory<Boolean> booleanVariable(String variableName) {
    return new BasicVariableFactory<>(variableName, Boolean.class);
  }

  /**
   * Create variable factory for custom type.
   * @param variableName name of the variable.
   * @param clazz class of specifying the type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  public static <T> VariableFactory<T> customVariable(String variableName, Class<T> clazz) {
    return new BasicVariableFactory<>(variableName, clazz);
  }

  /**
   * Create variable factory for list of custom type.
   * @param variableName name of the variable.
   * @param clazz class of specifying the member type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  public static <T> VariableFactory<List<T>> listVariable(String variableName, Class<T> clazz) {
    return new ListVariableFactory<>(variableName, clazz);
  }

  /**
   * Create variable factory for set of custom type.
   * @param variableName name of the variable.
   * @param clazz class of specifying the member type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  public static <T> VariableFactory<Set<T>> setVariable(String variableName, Class<T> clazz) {
    return new SetVariableFactory<>(variableName, clazz);
  }

}
