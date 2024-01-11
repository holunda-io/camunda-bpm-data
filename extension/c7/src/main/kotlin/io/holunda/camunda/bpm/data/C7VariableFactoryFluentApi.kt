package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.reader.*
import io.holunda.camunda.bpm.data.writer.*
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

/**
 * Getter from local scope.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.getOptional(factory: VariableFactory<T>): Optional<T> = VariableMapReader.of(factory, this).getOptional()

/**
 * Fluent setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableMap.set(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
  VariableMapWriter.of(factory, this)[value] = isTransient
}

/**
 * Fluent remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableMap.remove(factory: VariableFactory<T>) = this.apply {
  VariableMapWriter.of(factory, this).remove()
}

/**
 * Fluent updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableMap.update(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
  VariableMapWriter.of(factory, this).update(valueProcessor, isTransient)
}

/**
 * Getter from local scope.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.getOptional(factory: VariableFactory<T>): Optional<T> = VariableScopeReader.of(factory, this).getOptional()

/**
 * Getter from local scope.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.getLocal(factory: VariableFactory<T>): T = VariableScopeReader.of(factory, this).getLocal()

/**
 * Getter from local scope.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.getLocalOptional(factory: VariableFactory<T>): Optional<T> =
  VariableScopeReader.of(factory, this).getLocalOptional()

/**
 * Fluent setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.set(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
  VariableScopeWriter.of(factory, this)[value] = isTransient
}

/**
 * Fluent remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.remove(factory: VariableFactory<T>) = this.apply {
  VariableScopeWriter.of(factory, this).remove()
}

/**
 * Fluent updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.update(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
  VariableScopeWriter.of(factory, this).update(valueProcessor, isTransient)
}

/**
 * Fluent local setter.
 * @param factory factory defining the variable.
 * @param value new value.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.setLocal(factory: VariableFactory<T>, value: T, isTransient: Boolean = false) = this.apply {
  VariableScopeWriter.of(factory, this).setLocal(value, isTransient)
}

/**
 * Fluent local remover.
 * @param factory factory defining the variable.
 */
fun <T> VariableScope.removeLocal(factory: VariableFactory<T>) = this.apply {
  VariableScopeWriter.of(factory, this).removeLocal()
}

/**
 * Fluent local updater.
 * @param factory factory defining the variable.
 * @param valueProcessor update function.
 * @param isTransient flag for transient access, <code>false</code> by default.
 */
fun <T> VariableScope.updateLocal(factory: VariableFactory<T>, valueProcessor: (T) -> T, isTransient: Boolean = false) = this.apply {
  VariableScopeWriter.of(factory, this).updateLocal(valueProcessor, isTransient)
}

/**
 * Helper to access case service writer.
 * @param caseExecutionId id of the execution.
 */
fun CaseService.writer(caseExecutionId: String) =
  CaseServiceVariableWriter(this, caseExecutionId)

/**
 * Helper to access runtime service writer.
 * @param executionId id of the execution.
 */
fun RuntimeService.writer(executionId: String) =
  RuntimeServiceVariableWriter(this, executionId)

/**
 * Helper to access task service writer.
 * @param taskId id of the task.
 */
fun TaskService.writer(taskId: String) =
  TaskServiceVariableWriter(this, taskId)

/**
 * Helper to access case service reader.
 * @param caseExecutionId id of the execution.
 */
fun CaseService.reader(caseExecutionId: String) =
  CaseServiceVariableReader(this, caseExecutionId)

/**
 * Helper to access runtime service reader.
 * @param executionId id of the execution.
 */
fun RuntimeService.reader(executionId: String) =
  RuntimeServiceVariableReader(this, executionId)

/**
 * Helper to access task service reader.
 * @param taskId id of the task.
 */
fun TaskService.reader(taskId: String) =
  TaskServiceVariableReader(this, taskId)
