package io.holunda.camunda.bpm.data.mockito

import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.VariableMap

/**
 * Collection of fluent mock builder factory methods.
 */
@Deprecated(
  message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
  replaceWith = ReplaceWith("ServiceExpressions", "org.camunda.community.mockito.ServiceExpressions"),
  level = DeprecationLevel.WARNING
)
object CamundaBpmDataMockito {

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a task service variable mock builder.
   * @param taskService task service mock.
   * @return fluent builder.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.taskServiceVariableStubBuilder(TaskService)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun taskServiceVariableMockBuilder(taskService: TaskService) = TaskServiceVariableMockBuilder(taskService = taskService)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a task service variable mock builder.
   * @param taskService task service mock.
   * @param variables variable map to reuse.
   * @param localVariables local variable map to reuse.
   * @return fluent builder.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.taskServiceVariableStubBuilder(TaskService, VariableMap, VariableMap)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun taskServiceVariableMockBuilder(taskService: TaskService, variables: VariableMap, localVariables: VariableMap) = TaskServiceVariableMockBuilder(taskService = taskService, variables = variables, localVariables = localVariables)


  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a verifier for task service mock.
   * @param taskService a mock to work on.
   * @return verifier to simplify assertions.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.taskServiceVerification(TaskService)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun taskServiceMockVerifier(taskService: TaskService) = TaskServiceMockVerifier(taskService)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a runtime service variable mock builder.
   * @param runtimeService runtime service mock.
   * @return fluent builder.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.runtimeServiceVariableStubBuilder(RuntimeService)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun runtimeServiceVariableMockBuilder(runtimeService: RuntimeService) = RuntimeServiceVariableMockBuilder(runtimeService = runtimeService)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a runtime service variable mock builder.
   * @param runtimeService runtime service mock.
   * @param variables variable map to reuse.
   * @param localVariables local variable map to reuse.
   * @return fluent builder.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.runtimeServiceVariableStubBuilder(RuntimeService, VariableMap, VariableMap)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun runtimeServiceVariableMockBuilder(runtimeService: RuntimeService, variables: VariableMap, localVariables: VariableMap) = RuntimeServiceVariableMockBuilder(runtimeService = runtimeService, variables = variables, localVariables = localVariables)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a verifier for runtime service mock.
   * @param runtimeService a mock to work on.
   * @return verifier to simplify assertions.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.runtimeServiceVerification(RuntimeService)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun runtimeServiceMockVerifier(runtimeService: RuntimeService) = RuntimeServiceMockVerifier(runtimeService)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a case service variable mock builder.
   * @param caseService case service mock.
   * @return fluent builder.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.caseServiceVariableStubBuilder(CaseService)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun caseServiceVariableMockBuilder(caseService: CaseService) = CaseServiceVariableMockBuilder(caseService = caseService)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a case service variable mock builder.
   * @param caseService case service mock.
   * @param variables variable map to reuse.
   * @param localVariables local variable map to reuse.
   * @return fluent builder.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.caseServiceVariableStubBuilder(CaseService, VariableMap, VariableMap)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun caseServiceVariableMockBuilder(caseService: CaseService, variables: VariableMap, localVariables: VariableMap) = CaseServiceVariableMockBuilder(caseService = caseService, variables = variables, localVariables = localVariables)

  /**
   * @Deprecated "Moved to camunda-platform-7-mockito, will be removed in 1.3.x"
   * Constructs a verifier for case service mock.
   * @param caseService a mock to work on.
   * @return verifier to simplify assertions.
   */
  @JvmStatic
  @Deprecated(
    message = "Moved to camunda-platform-7-mockito, will be removed in 1.3.x",
    replaceWith = ReplaceWith("ServiceExpressions.caseServiceVerification(CaseService)", "org.camunda.community.mockito.ServiceExpressions"),
    level = DeprecationLevel.WARNING
  )
  fun caseServiceMockVerifier(caseService: CaseService) = CaseServiceMockVerifier(caseService)
}
