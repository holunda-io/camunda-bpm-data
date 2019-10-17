package io.holunda.camunda.bpm.data;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import java.util.Date;

public class CamundaBpmData {
  private CamundaBpmData() { }

  public static VariableFactory<String> stringVariable(String variableName) {
    return new VariableFactory<>(variableName, String.class);
  }

  public static VariableFactory<Date> dateVariable(String variableName) {
    return new VariableFactory<>(variableName, Date.class);
  }

  public static VariableFactory<Integer> intVariable(String variableName) {
    return new VariableFactory<>(variableName, Integer.class);
  }

  public static VariableFactory<Long> longVariable(String variableName) {
    return new VariableFactory<>(variableName, Long.class);
  }

  public static VariableFactory<Short> shortVariable(String variableName) {
    return new VariableFactory<>(variableName, Short.class);
  }

  public static VariableFactory<Double> doubleVariable(String variableName) {
    return new VariableFactory<>(variableName, Double.class);
  }

  public static VariableFactory<Boolean> booleanVariable(String variableName) {
    return new VariableFactory<>(variableName, Boolean.class);
  }

  public static <T> VariableFactory<T> customVariable(String variableName, Class<T> clazz) {
    return new VariableFactory<>(variableName, clazz);
  }
}
