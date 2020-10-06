package io.holunda.camunda.bpm.data.adapter.set;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Collections;
import java.util.Set;

/**
 * Base class for all set type read write adapter.
 *
 * @param <T> member type.
 */
public abstract class AbstractSetReadWriteAdapter<T> extends AbstractReadWriteAdapter<Set<T>> {

  /**
   * Member type.
   */
  protected final Class<T> memberClazz;

  /**
   * Constructs adapter.
   *
   * @param variableName name of the variable.
   * @param memberClazz  member class.
   */
  public AbstractSetReadWriteAdapter(String variableName, Class<T> memberClazz) {
    super(variableName);
    this.memberClazz = memberClazz;
  }

  /**
   * Retrieves the value or null.
   *
   * @param value raw value.
   * @return set or null.
   */
  @SuppressWarnings("unchecked")
  protected Set<T> getOrNull(Object value) {
    if (value == null) {
      return null;
    }

    if (Set.class.isAssignableFrom(value.getClass())) {
      Set<?> valueAsList = (Set<?>) value;
      if (valueAsList.isEmpty()) {
        return Collections.emptySet();
      } else {
        if (memberClazz.isAssignableFrom(valueAsList.iterator().next().getClass())) {
          return (Set<T>) valueAsList;
        } else {
          throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong set type detected, expected " + memberClazz.getName() + ", but was not found in " + valueAsList);
        }
      }
    }

    throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of type Set from " + value);
  }

  @Override
  public TypedValue getTypedValue(Object value, boolean isTransient) {
    return ValueWrapperUtil.getTypedValue(Set.class, value, isTransient);
  }
}
