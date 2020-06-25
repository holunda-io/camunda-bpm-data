package io.holunda.camunda.bpm.data.adapter.list;

import org.camunda.bpm.engine.TaskService;

import java.util.List;
import java.util.Optional;

/**
 * Read write adapter for task service access.
 *
 * @param <T> type of value.
 */
public class ListReadWriteAdapterTaskService<T> extends AbstractListReadWriteAdapter<T> {

    private final TaskService taskService;
    private final String taskId;

    /**
     * Constructs the adapter.
     *
     * @param taskService  task service to use.
     * @param taskId       id of the task to read from and write to.
     * @param variableName name of the variable.
     * @param memberClazz  class of the variable.
     */
    public ListReadWriteAdapterTaskService(TaskService taskService, String taskId, String variableName, Class<T> memberClazz) {
        super(variableName, memberClazz);
        this.taskService = taskService;
        this.taskId = taskId;
    }

    @Override
    public Optional<List<T>> getOptional() {
        return Optional.ofNullable(getOrNull(taskService.getVariable(taskId, variableName)));
    }

    @Override
    public void set(List<T> value, boolean isTransient) {
        taskService.setVariable(taskId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public Optional<List<T>> getLocalOptional() {
        return Optional.ofNullable(getOrNull(taskService.getVariableLocal(taskId, variableName)));
    }

    @Override
    public void setLocal(List<T> value, boolean isTransient) {
        taskService.setVariableLocal(taskId, variableName, getTypedValue(value, isTransient));
    }

    @Override
    public void remove() {
        taskService.removeVariable(taskId, variableName);
    }

    @Override
    public void removeLocal() {
        taskService.removeVariableLocal(taskId, variableName);
    }

}
