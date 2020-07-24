package io.holunda.camunda.bpm.data.mockito

import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Collection of fluent mock builder factory methods.
 */
object CamundaBpmDataMockito {

    /**
     * Constructs a task service variable mock builder.
     * @param taskService task service mock.
     * @return fluent builder.
     */
    @JvmStatic
    fun taskServiceVariableMockBuilder(taskService: TaskService) = TaskServiceVariableMockBuilder(taskService = taskService)

    /**
     * Constructs a task service variable mock builder.
     * @param taskService task service mock.
     * @param variables variable map to reuse.
     * @param localVariables local variable map to reuse.
     * @return fluent builder.
     */
    @JvmStatic
    fun taskServiceVariableMockBuilder(taskService: TaskService, variables: VariableMap, localVariables: VariableMap) = TaskServiceVariableMockBuilder(taskService = taskService, variables = variables, localVariables = localVariables)


    /**
     * Constructs a verifier for task service mock.
     * @param taskService a mock to work on.
     * @return verifier to simplify assertions.
     */
    @JvmStatic
    fun taskServiceMockVerifier(taskService: TaskService) = TaskServiceMockVerifier(taskService)

    /**
     * Constructs a runtime service variable mock builder.
     * @param runtimeService runtime service mock.
     * @return fluent builder.
     */
    @JvmStatic
    fun runtimeServiceVariableMockBuilder(runtimeService: RuntimeService) = RuntimeServiceVariableMockBuilder(runtimeService = runtimeService)

    /**
     * Constructs a runtime service variable mock builder.
     * @param runtimeService runtime service mock.
     * @param variables variable map to reuse.
     * @param localVariables local variable map to reuse.
     * @return fluent builder.
     */
    @JvmStatic
    fun runtimeServiceVariableMockBuilder(runtimeService: RuntimeService, variables: VariableMap, localVariables: VariableMap) = RuntimeServiceVariableMockBuilder(runtimeService = runtimeService, variables = variables, localVariables = localVariables)

    /**
     * Constructs a verifier for runtime service mock.
     * @param runtimeService a mock to work on.
     * @return verifier to simplify assertions.
     */
    @JvmStatic
    fun runtimeServiceMockVerifier(runtimeService: RuntimeService) = RuntimeServiceMockVerifier(runtimeService)

  /**
     * Constructs a case service variable mock builder.
     * @param caseService case service mock.
     * @return fluent builder.
     */
    @JvmStatic
    fun caseServiceVariableMockBuilder(caseService: CaseService) = CaseServiceVariableMockBuilder(caseService = caseService)

    /**
     * Constructs a case service variable mock builder.
     * @param caseService case service mock.
     * @param variables variable map to reuse.
     * @param localVariables local variable map to reuse.
     * @return fluent builder.
     */
    @JvmStatic
    fun caseServiceVariableMockBuilder(caseService: CaseService, variables: VariableMap, localVariables: VariableMap) = CaseServiceVariableMockBuilder(caseService = caseService, variables = variables, localVariables = localVariables)

    /**
     * Constructs a verifier for case service mock.
     * @param caseService a mock to work on.
     * @return verifier to simplify assertions.
     */
    @JvmStatic
    fun caseServiceMockVerifier(caseService: CaseService) = CaseServiceMockVerifier(caseService)
}
