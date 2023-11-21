package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.reader.VariableMapReader
import io.holunda.camunda.bpm.data.reader.VariableScopeReader
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Operator getter from global scope.
 * @param factory factory defining the variable.
 */
operator fun <T> VariableMap.get(factory: VariableFactory<T>): T = VariableMapReader.of(factory, this).get()

/**
 * Operator getter from global scope.
 * @param factory factory defining the variable.
 */
operator fun <T> VariableScope.get(factory: VariableFactory<T>): T = VariableScopeReader.of(factory, this).get()

