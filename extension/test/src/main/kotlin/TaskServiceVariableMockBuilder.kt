package io.holunda.camunda.bpm.data.mockito

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString

/**
 * Builder to mock the task service behavior regarding variables.
 */
class TaskServiceVariableMockBuilder(
  private val taskService: TaskService,
  private val variables: VariableMap = createVariables(),
  private val localVariables: VariableMap = createVariables(),
  private val factories: MutableList<VariableFactory<*>> = mutableListOf()
) {

  /**
   * Defines a global variable.
   * @param variableFactory variable to define.
   * @return fluent builder.
   */
  fun <T> define(variableFactory: VariableFactory<T>): TaskServiceVariableMockBuilder {
    factories.add(variableFactory)
    return this
  }

  /**
   * Defines a variable and sets an initial global value.
   * @param variableFactory factory to use.
   * @param value initial value.
   * @return fluent builder.
   */
  fun <T> initial(variableFactory: VariableFactory<T>, value: T): TaskServiceVariableMockBuilder {
    define(variableFactory)
    variableFactory.on(variables).set(value)
    return this
  }

  /**
   * Defines a variable and sets an initial local value.
   * @param variableFactory factory to use.
   * @param value initial value.
   * @return fluent builder.
   */
  fun <T> initialLocal(variableFactory: VariableFactory<T>, value: T): TaskServiceVariableMockBuilder {
    factories.add(variableFactory)
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
      }.whenever(taskService).getVariable(anyString(), eq(factory.name))

      doAnswer { invocation ->
        // Arguments: 0: taskId, 1: variable name, 2: value
        val value = invocation.getArgument<Any>(2)
        variables[factory.name] = value
      }.whenever(taskService).setVariable(anyString(), eq(factory.name), any())

      // local
      doAnswer {
        factory.from(localVariables).get()
      }.whenever(taskService).getVariableLocal(anyString(), eq(factory.name))

      doAnswer { invocation ->
        // Arguments: 0: taskId, 1: variable name, 2: value
        val value = invocation.getArgument<Any>(2)
        localVariables[factory.name] = value
      }.whenever(taskService).setVariableLocal(anyString(), eq(factory.name), any())
    }

    doAnswer { variables }.whenever(taskService).getVariables(anyString())
    doAnswer { invocation ->
      // Arguments: 0: taskId, 1: licat of variables
      val variablesList = invocation.getArgument<List<String>>(1)
      variables.filter { variablesList.contains(it.key) }
    }.whenever(taskService).getVariables(anyString(), anyList())
    doAnswer { localVariables }.whenever(taskService).getVariablesLocal(anyString())
    doAnswer { invocation ->
      // Arguments: 0: taskId, 1: licat of variables
      val variablesList = invocation.getArgument<List<String>>(1)
      localVariables.filter { variablesList.contains(it.key) }
    }.whenever(taskService).getVariablesLocal(anyString(), anyList())
  }
}
