package io.holunda.camunda.bpm.data.guard.condition

import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.guard.GuardViolation
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.delegate.VariableScope
import org.camunda.bpm.engine.variable.VariableMap
import java.util.*

abstract class VariableGuardCondition<T>(
        internal val variableFactory: VariableFactory<T>,
        internal val local: Boolean = false
) {

  val localLabel: String by lazy { if (local) " local" else "" }

  internal open fun evaluate(option: Optional<T>): List<GuardViolation<T>> = emptyList()

  fun evaluate(variableMap: VariableMap): List<GuardViolation<T>> {
    return evaluate(if (local) variableFactory.from(variableMap).localOptional else variableFactory.from(variableMap).optional)
  }

  fun evaluate(variableScope: VariableScope): List<GuardViolation<T>> {
    return evaluate(if (local) variableFactory.from(variableScope).localOptional else variableFactory.from(variableScope).optional)
  }

  fun evaluate(taskService: TaskService, taskId: String): List<GuardViolation<T>> {
    return evaluate(if (local) variableFactory.from(taskService, taskId).localOptional else variableFactory.from(taskService, taskId).optional)
  }

  fun evaluate(runtimeService: RuntimeService, executionId: String): List<GuardViolation<T>> {
    return evaluate(if (local) variableFactory.from(runtimeService, executionId).localOptional else variableFactory.from(runtimeService, executionId).optional)
  }
}
