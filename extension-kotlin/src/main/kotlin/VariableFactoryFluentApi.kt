package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.builder.ProcessExecutionVariableBuilder
import io.holunda.camunda.bpm.data.builder.UserTaskVariableBuilder
import io.holunda.camunda.bpm.data.factory.RuntimeServiceAdapterBuilder
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Fluent setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableMap.set(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
    factory.on(this).set(value, isTransient)
}

/**
 * Fluent remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.remove(factory: VariableFactory<T>) = this.apply {
    factory.on(this).remove()
}

/**
 * Fluent updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableMap.update(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
    factory.on(this).update(valueProcessor, isTransient)
}


/**
 * Fluent setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.set(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
    factory.on(this).set(value, isTransient)
}

/**
 * Fluent remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.remove(factory: VariableFactory<T>) = this.apply {
    factory.on(this).remove()
}

/**
 * Fluent updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.update(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
    factory.on(this).update(valueProcessor, isTransient)
}

/**
 * Fluent local setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.setLocal(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
    factory.on(this).setLocal(value, isTransient)
}

/**
 * Fluent local remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.removeLocal(factory: VariableFactory<T>) = this.apply {
    factory.on(this).removeLocal()
}

/**
 * Fluent local updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.updateLocal(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
    factory.on(this).updateLocal(valueProcessor, isTransient)
}

/**
 * Helper to access runtime service builder.
 * @param executionId id of the execution.
 */
fun RuntimeService.builder(executionId: String) = ProcessExecutionVariableBuilder(this, executionId)

/**
 * Helper to access task service builder.
 * @param taskId id of the task.
 */
fun TaskService.builder(taskId: String) = UserTaskVariableBuilder(this, taskId)
