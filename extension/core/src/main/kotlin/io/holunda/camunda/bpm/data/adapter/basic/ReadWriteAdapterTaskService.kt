package io.holunda.camunda.bpm.data.adapter.basic

import org.camunda.bpm.engine.TaskService
import java.util.*

/**
 * Read write adapter for task service access.
 *
 * @param [T] type of value.
 * @param taskService  task service to use.
 * @param taskId       id of the task to read from and write to.
 * @param variableName name of the variable.
 * @param clazz        class of the variable.
</T> */
class ReadWriteAdapterTaskService<T: Any?>(
    private val taskService: TaskService,
    private val taskId: String,
    variableName: String,
    clazz: Class<T>
) : AbstractBasicReadWriteAdapter<T>(variableName, clazz) {
    override fun getOptional(): Optional<T> {
        @Suppress("UNCHECKED_CAST")
        return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName))) as Optional<T>
    }

    override fun set(value: T, isTransient: Boolean) {
        taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient))
    }

    override fun getLocalOptional(): Optional<T> {
        @Suppress("UNCHECKED_CAST")
        return Optional.ofNullable(getOrNull(taskService.getVariableLocal(taskId, variableName))) as Optional<T>
    }

    override fun setLocal(value: T, isTransient: Boolean) {
        taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient))
    }

    override fun remove() {
        taskService.removeVariable(taskId, variableName)
    }

    override fun removeLocal() {
        taskService.removeVariableLocal(taskId, variableName)
    }
}
