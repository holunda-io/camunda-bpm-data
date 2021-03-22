package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Allows reading multiple variable values from {@link LockedExternalTask}).
 */
public class LockedExternalTaskReader implements VariableReader {

  private final LockedExternalTask lockedExternalTask;

  /**
   * Constructs a reader operating on external task.
   *
   * @param lockedExternalTask scope to operate on.
   */
  public LockedExternalTaskReader(LockedExternalTask lockedExternalTask) {
    this.lockedExternalTask = lockedExternalTask;
  }

  @Override
  @NotNull
  public <T> Optional<T> getOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(lockedExternalTask).getOptional();
  }

  @Override
  @NotNull
  public <T> T get(VariableFactory<T> variableFactory) {
    return variableFactory.from(lockedExternalTask).get();
  }

  @Override
  @NotNull
  public <T> T getLocal(VariableFactory<T> variableFactory) {
    return variableFactory.from(lockedExternalTask).getLocal();
  }

  @Override
  @NotNull
  public <T> Optional<T> getLocalOptional(VariableFactory<T> variableFactory) {
    return variableFactory.from(lockedExternalTask).getLocalOptional();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LockedExternalTaskReader that = (LockedExternalTaskReader) o;

    return Objects.equals(lockedExternalTask, that.lockedExternalTask);
  }

  @Override
  public int hashCode() {
    return lockedExternalTask != null ? lockedExternalTask.hashCode() : 0;
  }
}
