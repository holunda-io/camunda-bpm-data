package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.TaskService
import java.util.*

/**
 * Allows reading multiple variable values from [TaskService.getVariable].
 * @param taskService task service to operate on.
 * @param taskId      task id.
 */
class TaskServiceVariableReader(private val taskService: TaskService, private val taskId: String) : VariableReader {
    override fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
        return variableFactory.from(taskService, taskId).optional
    }

    override fun <T> get(variableFactory: VariableFactory<T>): T {
        return variableFactory.from(taskService, taskId).get()
    }

    override fun <T> getLocal(variableFactory: VariableFactory<T>): T {
        return variableFactory.from(taskService, taskId).local
    }

    override fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
        return variableFactory.from(taskService, taskId).localOptional
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as TaskServiceVariableReader
        return if (taskService != that.taskService) false else taskId == that.taskId
    }

    override fun hashCode(): Int {
        var result = taskService.hashCode()
        result = 31 * result + taskId.hashCode()
        return result
    }
}
