

package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.builder.VariableMapBuilder
import io.holunda.camunda.bpm.data.factory.*
import io.holunda.camunda.bpm.data.reader.*
import io.holunda.camunda.bpm.data.writer.*
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*


/**
 * Provides a collection of factory methods for creating variable factories.
 */
object CamundaBpmData {
    /**
     * Creates a string variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for string.
     */
    @JvmStatic
    fun stringVariable(variableName: String): VariableFactory<String> {
        return BasicVariableFactory(variableName, String::class.java)
    }

    /**
     * Creates a date variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for date.
     */
    @JvmStatic
    fun dateVariable(variableName: String): VariableFactory<Date> {
        return BasicVariableFactory(variableName, Date::class.java)
    }

    /**
     * Creates an integer variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for integer.
     */
    @JvmStatic
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun intVariable(variableName: String): VariableFactory<Integer> {
        return BasicVariableFactory(variableName, java.lang.Integer::class.java)
    }

    /**
     * Creates a long variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for long.
     */
    @JvmStatic
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun longVariable(variableName: String): VariableFactory<java.lang.Long> {
        return BasicVariableFactory(variableName, java.lang.Long::class.java)
    }

    /**
     * Creates a short variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for short.
     */
    @JvmStatic
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun shortVariable(variableName: String): VariableFactory<java.lang.Short> {
        return BasicVariableFactory(variableName, java.lang.Short::class.java)
    }

    /**
     * Creates a double variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for double.
     */
    @JvmStatic
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun doubleVariable(variableName: String): VariableFactory<java.lang.Double> {
        return BasicVariableFactory(variableName, java.lang.Double::class.java)
    }

    /**
     * Creates a boolean variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for boolean.
     */
    @JvmStatic
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun booleanVariable(variableName: String): VariableFactory<java.lang.Boolean> {
        return BasicVariableFactory(variableName, java.lang.Boolean::class.java)
    }

    /**
     * Creates an uuid variable factory.
     *
     * @param variableName name of the variable.
     * @return variable factory for uuid.
     */
    @JvmStatic
    fun uuidVariable(variableName: String): VariableFactory<UUID> {
        return BasicVariableFactory(variableName, UUID::class.java)
    }

    /**
     * Creates a variable factory for custom type.
     *
     * @param variableName name of the variable.
     * @param clazz        class of specifying the type.
     * @param <T>          factory type.
     * @return variable factory for given type.
    </T> */
    @JvmStatic
    fun <T> customVariable(variableName: String, clazz: Class<T>): VariableFactory<T> {
        return BasicVariableFactory(variableName, clazz)
    }

    /**
     * Creates a variable factory for list of custom type.
     *
     * @param variableName name of the variable.
     * @param clazz        class of specifying the member type.
     * @param <T>          factory type.
     * @return variable factory for given type.
    </T> */
    @JvmStatic
    fun <T> listVariable(variableName: String, clazz: Class<T>): VariableFactory<List<T>> {
        return ListVariableFactory(variableName, clazz)
    }

    /**
     * Creates a variable factory for set of custom type.
     *
     * @param variableName name of the variable.
     * @param clazz        class of specifying the member type.
     * @param <T>          factory type.
     * @return variable factory for given type.
    </T> */
    @JvmStatic
    fun <T> setVariable(variableName: String, clazz: Class<T>): VariableFactory<Set<T>> {
        return SetVariableFactory(variableName, clazz)
    }

    /**
     * Creates a variable factory for map of custom key and custom value type.
     *
     * @param variableName name of the variable.
     * @param keyClazz     class of specifying the key member type.
     * @param valueClazz   class of specifying the value member type.
     * @param <K>          factory key type.
     * @param <V>          factory value type.
     * @return variable factory for given type.
    </V></K> */
    @JvmStatic
    fun <K, V> mapVariable(
        variableName: String, keyClazz: Class<K>,
        valueClazz: Class<V>
    ): VariableFactory<Map<K, V>> {
        return MapVariableFactory(variableName, keyClazz, valueClazz)
    }

    /**
     * Creates a new variable map builder.
     *
     * @return new writer with empty variable map.
     */
    @JvmStatic
    fun builder(): VariableMapBuilder {
        return VariableMapBuilder()
    }

    /**
     * Creates a new variable map builder.
     *
     * @param variables pre-created, potentially non-empty variables.
     * @return new writer
     */
    @JvmStatic
    fun writer(variables: VariableMap): GlobalVariableWriter<*> {
        return VariableMapWriter(variables)
    }

    /**
     * Creates a new variable scope writer.
     *
     * @param variableScope scope to work on (delegate execution or delegate task).
     * @return new writer working on provided variable scope.
     */
    @JvmStatic
    fun writer(variableScope: VariableScope): VariableWriter<*> {
        return VariableScopeWriter(variableScope)
    }

    /**
     * Creates a new execution variable writer.
     *
     * @param runtimeService runtime service to use.
     * @param executionId    id of the execution.
     * @return new writer working on provided process execution.
     */
    @JvmStatic
    fun writer(runtimeService: RuntimeService, executionId: String): VariableWriter<*> {
        return RuntimeServiceVariableWriter(runtimeService, executionId)
    }

    /**
     * Creates a new task variable writer.
     *
     * @param taskService task service to use.
     * @param taskId      task id.
     * @return new writer working on provided user task.
     */
    @JvmStatic
    fun writer(taskService: TaskService, taskId: String): VariableWriter<*> {
        return TaskServiceVariableWriter(taskService, taskId)
    }

    /**
     * Creates a new caseExecution variable writer.
     *
     * @param caseService     task service to use.
     * @param caseExecutionId caseExecution id.
     * @return new writer working on provided user task.
     */
    @JvmStatic
    fun writer(caseService: CaseService, caseExecutionId: String): VariableWriter<*> {
        return CaseServiceVariableWriter(caseService, caseExecutionId)
    }

    /**
     * Creates a new task variable reader.
     *
     * @param taskService the Camunda task service
     * @param taskId      the id of the task to use
     * @return variable reader working on task
     */
    @JvmStatic
    fun reader(taskService: TaskService, taskId: String): VariableReader {
        return TaskServiceVariableReader(taskService, taskId)
    }

    /**
     * Creates a new execution variable reader.
     *
     * @param runtimeService the Camunda runtime service
     * @param executionId    the executionId to use
     * @return variable reader working on execution
     */
    @JvmStatic
    fun reader(runtimeService: RuntimeService, executionId: String): VariableReader {
        return RuntimeServiceVariableReader(runtimeService, executionId)
    }

    /**
     * Creates a new execution variable reader.
     *
     * @param caseService     the Camunda case service
     * @param caseExecutionId the caseExecutionId to use
     * @return variable reader working on execution
     */
    @JvmStatic
    fun reader(caseService: CaseService, caseExecutionId: String): VariableReader {
        return CaseServiceVariableReader(caseService, caseExecutionId)
    }

    /**
     * Creates a new variableScope variable reader.
     *
     * @param variableScope the variable scope to use (DelegateExecution, DelegateTask)
     * @return variable reader working on variableScope
     */
    @JvmStatic
    fun reader(variableScope: VariableScope): VariableReader {
        return VariableScopeReader(variableScope)
    }

    /**
     * Creates a new variableMap variable reader.
     *
     * @param variableMap the variableMap to use
     * @return variable reader working on variableMap
     */
    @JvmStatic
    fun reader(variableMap: VariableMap): VariableReader {
        return VariableMapReader(variableMap)
    }

    /**
     * Creates a new processInstance variable reader.
     *
     * @see .reader
     * @param processInstance the processInstance with variables to read from
     * @return variable reader working on the variableMap provided by instance
     */
    @JvmStatic
    fun reader(processInstance: ProcessInstanceWithVariables): VariableReader {
        return reader(processInstance.variables)
    }

    /**
     * Creates a new extern variable reader.
     *
     * @param lockedExternalTask the external tasks to use
     * @return variable reader working on external task
     */
    @JvmStatic
    fun reader(lockedExternalTask: LockedExternalTask): VariableReader {
        return LockedExternalTaskReader(lockedExternalTask)
    }
}
