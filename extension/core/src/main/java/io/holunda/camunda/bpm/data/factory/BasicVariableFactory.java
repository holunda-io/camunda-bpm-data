package io.holunda.camunda.bpm.data.factory;

import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.adapter.basic.ReadAdapterLockedExternalTask;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterCaseService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterRuntimeService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterTaskService;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterVariableMap;
import io.holunda.camunda.bpm.data.adapter.basic.ReadWriteAdapterVariableScope;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Variable factory of a base type(non parametrized).
 *
 * @param <T> type of the factory.
 */
public class BasicVariableFactory<T> implements VariableFactory<T> {

  @NotNull
  private final String name;

  @NotNull
  private final Class<T> clazz;

  /**
   * Creates variable factory for a given type and name.
   *
   * @param name  name of the variable.
   * @param clazz class of the type.
   */
  public BasicVariableFactory(@NotNull String name, @NotNull Class<T> clazz) {
    this.name = name;
    this.clazz = clazz;
  }

  @Override
  public WriteAdapter<T> on(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(VariableScope variableScope) {
    return new ReadWriteAdapterVariableScope<>(variableScope, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(VariableMap variableMap) {
    return new ReadWriteAdapterVariableMap<>(variableMap, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(RuntimeService runtimeService, String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(RuntimeService runtimeService, String executionId) {
    return new ReadWriteAdapterRuntimeService<>(runtimeService, executionId, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(TaskService taskService, String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(TaskService taskService, String taskId) {
    return new ReadWriteAdapterTaskService<>(taskService, taskId, name, clazz);
  }

  @Override
  public WriteAdapter<T> on(CaseService caseService, String caseExecutionId) {
    return new ReadWriteAdapterCaseService<>(caseService, caseExecutionId, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(CaseService caseService, String caseExecutionId) {
    return new ReadWriteAdapterCaseService<>(caseService, caseExecutionId, name, clazz);
  }

  @Override
  public ReadAdapter<T> from(LockedExternalTask lockedExternalTask) {
    return new ReadAdapterLockedExternalTask<>(lockedExternalTask, name);
  }

  /**
   * Creates a reusable adapter builder using a runtime service.
   *
   * @param runtimeService runtime service to operate on.
   * @return adapter builder.
   */
  public BasicRuntimeServiceAdapterBuilder<T> using(RuntimeService runtimeService) {
    return new BasicRuntimeServiceAdapterBuilder<>(this, runtimeService);
  }

  /**
   * Creates a reusable adapter builder using a task service.
   *
   * @param taskService task service to operate on.
   * @return adapter builder.
   */
  public BasicTaskServiceAdapterBuilder<T> using(TaskService taskService) {
    return new BasicTaskServiceAdapterBuilder<>(this, taskService);
  }

  /**
   * Creates a reusable adapter builder using a case service.
   *
   * @param caseService case service to operate on.
   * @return adapter builder.
   */
  public BasicCaseServiceAdapterBuilder<T> using(CaseService caseService) {
    return new BasicCaseServiceAdapterBuilder<>(this, caseService);
  }

  @Override
  @NotNull
  public String getName() {
    return this.name;
  }

  /**
   * Retrieves the variable class.
   *
   * @return class of the variable.
   */
  @NotNull
  public Class<T> getVariableClass() {
    return clazz;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    BasicVariableFactory<?> that = (BasicVariableFactory<?>) o;
    return name.equals(that.name) &&
      clazz.equals(that.clazz);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, clazz);
  }

  @Override
  public String toString() {
    return "BasicVariableFactory{" +
      "name='" + name + '\'' +
      ", clazz=" + clazz +
      '}';
  }

  /**
   * Creates a builder to encapsulate the runtime service access.
   *
   * @param <T> type of builder.
   */
  public static class BasicRuntimeServiceAdapterBuilder<T> {

    private final RuntimeService runtimeService;
    private final BasicVariableFactory<T> basicVariableFactory;

    /**
     * Constructs the builder.
     *
     * @param basicVariableFactory variable factory to use.
     * @param runtimeService       task service to build adapter with.
     */
    public BasicRuntimeServiceAdapterBuilder(BasicVariableFactory<T> basicVariableFactory, RuntimeService runtimeService) {
      this.runtimeService = runtimeService;
      this.basicVariableFactory = basicVariableFactory;
    }

    private ReadWriteAdapterRuntimeService<T> readWriteAdapter(String executionId) {
      return new ReadWriteAdapterRuntimeService<>(
        runtimeService,
        executionId,
        basicVariableFactory.getName(),
        basicVariableFactory.getVariableClass()
      );
    }

    /**
     * Creates a write adapter on execution.
     *
     * @param executionId id identifying execution.
     * @return write adapter
     */
    public WriteAdapter<T> on(String executionId) {
      return readWriteAdapter(executionId);
    }

    /**
     * Creates a read adapter on execution.
     *
     * @param executionId id identifying execution.
     * @return read adapter.
     */
    public ReadAdapter<T> from(String executionId) {
      return readWriteAdapter(executionId);
    }
  }

  /**
   * Creates a builder to encapsulate the task service access.
   *
   * @param <T> type of builder.
   */
  public static class BasicTaskServiceAdapterBuilder<T> {

    private final TaskService taskService;
    private final BasicVariableFactory<T> basicVariableFactory;

    /**
     * Constructs the builder.
     *
     * @param basicVariableFactory variable factory to use.
     * @param taskService          task service to build adapter with.
     */
    public BasicTaskServiceAdapterBuilder(BasicVariableFactory<T> basicVariableFactory, TaskService taskService) {
      this.taskService = taskService;
      this.basicVariableFactory = basicVariableFactory;
    }

    private ReadWriteAdapterTaskService<T> readWriteAdapter(String taskId) {
      return new ReadWriteAdapterTaskService<>(
        taskService,
        taskId,
        basicVariableFactory.getName(),
        basicVariableFactory.getVariableClass()
      );
    }

    /**
     * Creates a write adapter on task.
     *
     * @param taskId id identifying task.
     * @return write adapter
     */
    public WriteAdapter<T> on(String taskId) {
      return readWriteAdapter(taskId);
    }

    /**
     * Creates a read adapter on task.
     *
     * @param taskId id identifying task.
     * @return read adapter.
     */
    public ReadAdapter<T> from(String taskId) {
      return readWriteAdapter(taskId);
    }
  }


  /**
   * Creates a builder to encapsulate the case service access.
   *
   * @param <T> type of builder.
   */
  public static class BasicCaseServiceAdapterBuilder<T> {

    private final CaseService caseService;
    private final BasicVariableFactory<T> basicVariableFactory;

    /**
     * Constructs the builder.
     *
     * @param basicVariableFactory variable factory to use.
     * @param caseService          task service to build adapter with.
     */
    public BasicCaseServiceAdapterBuilder(BasicVariableFactory<T> basicVariableFactory, CaseService caseService) {
      this.caseService = caseService;
      this.basicVariableFactory = basicVariableFactory;
    }

    private ReadWriteAdapterCaseService<T> readWriteAdapter(String caseExecutionId) {
      return new ReadWriteAdapterCaseService<>(
        caseService,
        caseExecutionId,
        basicVariableFactory.getName(),
        basicVariableFactory.getVariableClass()
      );
    }

    /**
     * Creates a write adapter on caseExecution.
     *
     * @param caseExecutionId id identifying caseExecution.
     * @return write adapter
     */
    public WriteAdapter<T> on(String caseExecutionId) {
      return readWriteAdapter(caseExecutionId);
    }

    /**
     * Creates a read adapter on caseExecution.
     *
     * @param caseExecutionId id identifying caseExecution.
     * @return read adapter.
     */
    public ReadAdapter<T> from(String caseExecutionId) {
      return readWriteAdapter(caseExecutionId);
    }
  }
}
