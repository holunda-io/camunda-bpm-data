package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString

/**
 * Builder to mock the runtime service behavior regarding variables.
 */
class RuntimeServiceVariableMockBuilder(
    private val runtimeService: RuntimeService,
    private val variables: VariableMap = createVariables(),
    private val localVariables: VariableMap = createVariables(),
    private val factories: MutableList<VariableFactory<*>> = mutableListOf()
) {

    /**
     * Defines a variable.
     * @param variableFactory variable to define.
     * @return fluent builder.
     */
    fun <T> define(variableFactory: VariableFactory<T>): RuntimeServiceVariableMockBuilder {
        factories.add(variableFactory)
        return this
    }

    /**
     * Defines a variable and sets a global value.
     * @param variableFactory factory to use.
     * @param value initial value.
     * @return fluent builder.
     */
    fun <T> intial(variableFactory: VariableFactory<T>, value: T): RuntimeServiceVariableMockBuilder {
        define(variableFactory)
        variableFactory.on(variables).set(value)
        return this
    }

    /**
     * Defines a variable and sets a local value.
     * @param variableFactory factory to use.
     * @param value initial value.
     * @return fluent builder.
     */
    fun <T> initalLocal(variableFactory: VariableFactory<T>, value: T): RuntimeServiceVariableMockBuilder {
        define(variableFactory)
        variableFactory.on(localVariables).set(value)
        return this
    }

    /**
     * Performs the modifications on the task service.
     */
    fun build() {

        factories.forEach { factory ->

            // global
            doAnswer {
                factory.from(variables).get()
            }.whenever(runtimeService).getVariable(anyString(), eq(factory.name))

            doAnswer { invocation ->
                // Arguments: 0: taskId, 1: variable name, 2: value
                val value = invocation.getArgument<Any>(2)
                variables.set(factory.name, value)
                // FIXME: does this work?
                // factory.on(variables).set(value)
                Unit
            }.whenever(runtimeService).setVariable(anyString(), eq(factory.name), any())

            // local
            doAnswer {
                factory.from(localVariables).get()
            }.whenever(runtimeService).getVariableLocal(anyString(), eq(factory.name))

            doAnswer { invocation ->
                // Arguments: 0: taskId, 1: variable name, 2: value
                val value = invocation.getArgument<Any>(2)
                localVariables.set(factory.name, value)
                // FIXME: does this work?
                // factory.on(variables).set(value)
                Unit
            }.whenever(runtimeService).setVariableLocal(anyString(), eq(factory.name), any())
        }

        doAnswer { variables }.whenever(runtimeService).getVariables(anyString())
        doAnswer { invocation ->
            // Arguments: 0: taskId, 1: licat of variables
            val variablesList = invocation.getArgument<List<String>>(1)
            variables.filter { variablesList.contains(it.key) }
        }.whenever(runtimeService).getVariables(anyString(), anyList())

        doAnswer { localVariables }.whenever(runtimeService).getVariablesLocal(anyString())
        doAnswer { invocation ->
            // Arguments: 0: taskId, 1: licat of variables
            val variablesList = invocation.getArgument<List<String>>(1)
            localVariables.filter { variablesList.contains(it.key) }
        }.whenever(runtimeService).getVariablesLocal(anyString(), anyList())
    }
}