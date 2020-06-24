package io.holunda.camunda.bpm.data;

import io.holunda.camunda.bpm.data.builder.VariableMapBuilder;
import io.holunda.camunda.bpm.data.factory.BasicVariableFactory;
import io.holunda.camunda.bpm.data.factory.ListVariableFactory;
import io.holunda.camunda.bpm.data.factory.MapVariableFactory;
import io.holunda.camunda.bpm.data.factory.SetVariableFactory;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.bpm.data.reader.RuntimeServiceVariableReader;
import io.holunda.camunda.bpm.data.reader.TaskServiceVariableReader;
import io.holunda.camunda.bpm.data.reader.VariableMapReader;
import io.holunda.camunda.bpm.data.reader.VariableReader;
import io.holunda.camunda.bpm.data.reader.VariableScopeReader;
import io.holunda.camunda.bpm.data.writer.RuntimeServiceVariableWriter;
import io.holunda.camunda.bpm.data.writer.TaskServiceVariableWriter;
import io.holunda.camunda.bpm.data.writer.VariableMapWriter;
import io.holunda.camunda.bpm.data.writer.VariableScopeWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a collection of factory methods for creating variable factories.
 */
public class CamundaBpmData {

  /**
   * Hide the instantiations.
   */
  private CamundaBpmData() {
  }

  /**
   * Creates a string variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for string.
   */
  @NotNull
  public static VariableFactory<String> stringVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, String.class);
  }

  /**
   * Creates a date variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for date.
   */
  @NotNull
  public static VariableFactory<Date> dateVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, Date.class);
  }

  /**
   * Creates an integer variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for integer.
   */
  @NotNull
  public static VariableFactory<Integer> intVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, Integer.class);
  }

  /**
   * Creates a long variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for long.
   */
  @NotNull
  public static VariableFactory<Long> longVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, Long.class);
  }

  /**
   * Creates a short variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for short.
   */
  @NotNull
  public static VariableFactory<Short> shortVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, Short.class);
  }

  /**
   * Creates a double variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for double.
   */
  @NotNull
  public static VariableFactory<Double> doubleVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, Double.class);
  }

  /**
   * Creates a boolean variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for boolean.
   */
  @NotNull
  public static VariableFactory<Boolean> booleanVariable(@NotNull String variableName) {
    return new BasicVariableFactory<>(variableName, Boolean.class);
  }

  /**
   * Creates a variable factory for custom type.
   *
   * @param variableName name of the variable.
   * @param clazz class of specifying the type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  @NotNull
  public static <T> VariableFactory<T> customVariable(@NotNull String variableName, @NotNull Class<T> clazz) {
    return new BasicVariableFactory<>(variableName, clazz);
  }

  /**
   * Creates a variable factory for list of custom type.
   *
   * @param variableName name of the variable.
   * @param clazz class of specifying the member type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  @NotNull
  public static <T> VariableFactory<List<T>> listVariable(@NotNull String variableName, @NotNull Class<T> clazz) {
    return new ListVariableFactory<>(variableName, clazz);
  }

  /**
   * Creates a variable factory for set of custom type.
   *
   * @param variableName name of the variable.
   * @param clazz class of specifying the member type.
   * @param <T> factory type.
   * @return variable factory for given type.
   */
  @NotNull
  public static <T> VariableFactory<Set<T>> setVariable(@NotNull String variableName, @NotNull Class<T> clazz) {
    return new SetVariableFactory<>(variableName, clazz);
  }

  /**
   * Creates a variable factory for map of custom key and custom value type.
   *
   * @param variableName name of the variable.
   * @param keyClazz class of specifying the key member type.
   * @param valueClazz class of specifying the value member type.
   * @param <K> factory key type.
   * @param <V> factory value type.
   * @return variable factory for given type.
   */
  @NotNull
  public static <K, V> VariableFactory<Map<K, V>> mapVariable(@NotNull String variableName, @NotNull Class<K> keyClazz,
    @NotNull Class<V> valueClazz) {
    return new MapVariableFactory<>(variableName, keyClazz, valueClazz);
  }

  /**
   * Creates a new variable map builder.
   *
   * @return new writer with empty variable map.
   */
  public static VariableMapBuilder builder() {
    return new VariableMapBuilder();
  }

  /**
   * Creates a new variable map builder.
   *
   * @param variables pre-created, potentially non-empty variables.
   * @return new writer
   */
  @NotNull
  public static VariableMapWriter writer(VariableMap variables) {
    return new VariableMapWriter(variables);
  }

  /**
   * Creates a new variable scope writer.
   *
   * @param variableScope scope to work on (delegate execution or delegate task).
   * @return new writer working on provided variable scope.
   */
  @NotNull
  public static VariableScopeWriter writer(VariableScope variableScope) {
    return new VariableScopeWriter(variableScope);
  }

  /**
   * Creates a new execution variable writer.
   *
   * @param runtimeService runtime service to use.
   * @param executionId id of the execution.
   * @return new writer working on provided process execution.
   */
  @NotNull
  public static RuntimeServiceVariableWriter writer(RuntimeService runtimeService, String executionId) {
    return new RuntimeServiceVariableWriter(runtimeService, executionId);
  }

  /**
   * Creates a new task variable writer.
   *
   * @param taskService task service to use.
   * @param taskId task id.
   * @return new writer working on provided user task.
   */
  @NotNull
  public static TaskServiceVariableWriter writer(TaskService taskService, String taskId) {
    return new TaskServiceVariableWriter(taskService, taskId);
  }

  /**
   * Creates a new task variable reader.
   *
   * @param taskService the Camunda task service
   * @param taskId the id of the task to use
   * @return variable reader working on task
   */
  @NotNull
  public static VariableReader reader(TaskService taskService, String taskId) {
    return new TaskServiceVariableReader(taskService, taskId);
  }

  /**
   * Creates a new execution variable reader.
   *
   * @param runtimeService the Camunda runtime service
   * @param executionId the executionId to use
   * @return variable reader working on execution
   */
  public static VariableReader reader(RuntimeService runtimeService, String executionId) {
    return new RuntimeServiceVariableReader(runtimeService, executionId);
  }

  /**
   * Creates a new variableScope variable reader.
   *
   * @param variableScope the variable scope to use (DelegateExecution, DelegateTask)
   * @return variable reader working on variableScope
   */
  public static VariableReader reader(VariableScope variableScope) {
    return new VariableScopeReader(variableScope);
  }

  /**
   * Creates a new variableMap variable reader.
   * @param variableMap the variableMap to use
   * @return variable reader working on variableMap
   */
  public static VariableReader reader(VariableMap variableMap) {
    return new VariableMapReader(variableMap);
  }
}
