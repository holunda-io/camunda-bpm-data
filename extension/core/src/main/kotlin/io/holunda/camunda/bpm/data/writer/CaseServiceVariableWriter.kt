package io.holunda.camunda.bpm.data.writer

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.variable.VariableMap
import java.util.function.Function

/**
 * Process execution builder allowing for fluent variable setting.
 */
class CaseServiceVariableWriter(private val caseService: CaseService, private val caseExecutionId: String) :
  VariableWriter<CaseServiceVariableWriter> {

  override fun variables(): VariableMap {
    return caseService.getVariablesTyped(caseExecutionId)
  }

  override fun variablesLocal(): VariableMap {
    return caseService.getVariablesLocalTyped(caseExecutionId)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T): CaseServiceVariableWriter {
    return this.set(variableFactory, value, false)
  }

  override fun <T> set(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId)[value] = isTransient
    return this
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T): CaseServiceVariableWriter {
    return this.setLocal(variableFactory, value, false)
  }

  override fun <T> setLocal(variableFactory: VariableFactory<T>, value: T, isTransient: Boolean): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId).setLocal(value, isTransient)
    return this
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>
  ): CaseServiceVariableWriter {
    return updateLocal(variableFactory, valueProcessor, false)
  }

  override fun <T> updateLocal(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId).updateLocal(valueProcessor, isTransient)
    return this
  }

  override fun <T> remove(variableFactory: VariableFactory<T>): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId).remove()
    return this
  }

  override fun <T> removeLocal(variableFactory: VariableFactory<T>): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId).removeLocal()
    return this
  }

  override fun <T> update(variableFactory: VariableFactory<T>, valueProcessor: Function<T, T>): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId).update(valueProcessor)
    return this
  }

  override fun <T> update(
    variableFactory: VariableFactory<T>,
    valueProcessor: Function<T, T>,
    isTransient: Boolean
  ): CaseServiceVariableWriter {
    variableFactory.on(caseService, caseExecutionId).updateLocal(valueProcessor, isTransient)
    return this
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val that = other as CaseServiceVariableWriter
    return if (caseService != that.caseService) false else caseExecutionId == that.caseExecutionId
  }

  override fun hashCode(): Int {
    var result = caseService.hashCode()
    result = 31 * result + caseExecutionId.hashCode()
    return result
  }
}
