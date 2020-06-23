package io.holunda.camunda.bpm.data;

import io.holunda.camunda.bpm.data.builder.ProcessExecutionVariableBuilder;
import io.holunda.camunda.bpm.data.builder.UserTaskVariableBuilder;
import io.holunda.camunda.bpm.data.builder.VariableMapBuilder;
import io.holunda.camunda.bpm.data.builder.VariableScopeBuilder;
import io.holunda.camunda.bpm.data.factory.BasicVariableFactory;
import io.holunda.camunda.bpm.data.factory.ListVariableFactory;
import io.holunda.camunda.bpm.data.factory.MapVariableFactory;
import io.holunda.camunda.bpm.data.factory.SetVariableFactory;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides a collection of factory methods for creating variable factories.
 */
public class CamundaBpmData {
    /**
     * Hide the instantiations.
     */
    private CamundaBpmData() { }

    /**
     * Creates a string variable factory.
     *
     * @param variableName name of the variable.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     * @param clazz        class of specifying the type.
     * @param <T>          factory type.
     *
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
     * @param clazz        class of specifying the member type.
     * @param <T>          factory type.
     *
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
     * @param clazz        class of specifying the member type.
     * @param <T>          factory type.
     *
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
     * @param keyClazz     class of specifying the key member type.
     * @param valueClazz   class of specifying the value member type.
     * @param <K>          factory key type.
     * @param <V>          factory value type.
     *
     * @return variable factory for given type.
     */
    @NotNull
    public static <K, V> VariableFactory<Map<K, V>> mapVariable(@NotNull String variableName, @NotNull Class<K> keyClazz, @NotNull Class<V> valueClazz) {
        return new MapVariableFactory<>(variableName, keyClazz, valueClazz);
    }

    /**
     * Creates a new variable map builder.
     *
     * @return new builder with empty variable map.
     */
    @NotNull
    public static VariableMapBuilder builder() {
        return new VariableMapBuilder();
    }

    /**
     * Creates a new variable map builder.
     *
     * @param variables pre-filled variables.
     *
     * @return new builder with a variable map containing a copy of provided variables.
     */
    @NotNull
    public static VariableMapBuilder builder(VariableMap variables) {
        return new VariableMapBuilder(variables);
    }

    /**
     * Creates a new variable scope builder.
     *
     * @param variableScope scope to work on (delegate execution or delegate task).
     *
     * @return new builder working on provided variable scope.
     */
    @NotNull
    public static VariableScopeBuilder builder(VariableScope variableScope) {
        return new VariableScopeBuilder(variableScope);
    }

    /**
     * Creates a new execution variable builder.
     *
     * @param runtimeService runtime service to use.
     * @param executionId    id of the execution.
     *
     * @return new builder working on provided process execution.
     */
    @NotNull
    public static ProcessExecutionVariableBuilder builder(RuntimeService runtimeService, String executionId) {
        return new ProcessExecutionVariableBuilder(runtimeService, executionId);
    }

    /**
     * Creates a new task variable builder.
     *
     * @param taskService task service to use.
     * @param taskId      task id.
     *
     * @return new builder working on provided user task.
     */
    @NotNull
    public static UserTaskVariableBuilder builder(TaskService taskService, String taskId) {
        return new UserTaskVariableBuilder(taskService, taskId);
    }

}
