package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.writer.RuntimeServiceVariableWriter
import io.holunda.camunda.bpm.data.writer.TaskServiceVariableWriter
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.reader.CaseServiceVariableReader
import io.holunda.camunda.bpm.data.reader.RuntimeServiceVariableReader
import io.holunda.camunda.bpm.data.reader.TaskServiceVariableReader
import io.holunda.camunda.bpm.data.writer.CaseServiceVariableWriter
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Getter from global scope.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.get(factory: VariableFactory<T>) = this.apply {
  factory.from(this).get()
}

/**
 * Getter from local scope.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.getLocal(factory: VariableFactory<T>) = this.apply {
  factory.from(this).local
}

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
 * Fluent local setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableMap.setLocal(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
  factory.on(this).setLocal(value, isTransient)
}

/**
 * Fluent remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.remove(factory: VariableFactory<T>) = this.apply {
    factory.on(this).remove()
}

/**
 * Fluent local remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.removeLocal(factory: VariableFactory<T>) = this.apply {
  factory.on(this).removeLocal()
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
 * Fluent updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableMap.updateLocal(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
  factory.on(this).updateLocal(valueProcessor, isTransient)
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
 * Helper to access case service writer.
 * @param caseExecutionId id of the execution.
 */
fun CaseService.writer(caseExecutionId: String) = CaseServiceVariableWriter(this, caseExecutionId)

/**
 * Helper to access runtime service writer.
 * @param executionId id of the execution.
 */
fun RuntimeService.writer(executionId: String) = RuntimeServiceVariableWriter(this, executionId)

/**
 * Helper to access task service writer.
 * @param taskId id of the task.
 */
fun TaskService.writer(taskId: String) = TaskServiceVariableWriter(this, taskId)

/**
 * Helper to access case service reader.
 * @param caseExecutionId id of the execution.
 */
fun CaseService.reader(caseExecutionId: String) = CaseServiceVariableReader(this, caseExecutionId)

/**
 * Helper to access runtime service reader.
 * @param executionId id of the execution.
 */
fun RuntimeService.reader(executionId: String) = RuntimeServiceVariableReader(this, executionId)

/**
 * Helper to access task service reader.
 * @param taskId id of the task.
 */
fun TaskService.reader(taskId: String) = TaskServiceVariableReader(this, taskId)
