package io.holunda.camunda.bpm.data.adapter.list;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Collections;
import java.util.List;

/**
 * Base class for all list read-write adapter.
 *
 * @param <T> member type.
 */
public abstract class AbstractListReadWriteAdapter<T> extends AbstractReadWriteAdapter<List<T>> {

  /**
   * Member class.
   */
  protected final Class<T> memberClazz;

  /**
   * Constructs the adapter.
   *
   * @param variableName name of variable.
   * @param memberClazz  member class.
   */
  public AbstractListReadWriteAdapter(String variableName, Class<T> memberClazz) {
    super(variableName);
    this.memberClazz = memberClazz;
  }

  /**
   * Read the value of null.
   *
   * @param value raw value.
   *
   * @return list or null.
   */
  @SuppressWarnings("unchecked")
  protected List<T> getOrNull(Object value) {
    if (value == null) {
      return null;
    }

    if (List.class.isAssignableFrom(value.getClass())) {
      List<?> valueAsList = (List<?>) value;
      if (valueAsList.isEmpty()) {
        return Collections.<T>emptyList();
      } else {
        if (memberClazz.isAssignableFrom(valueAsList.iterator().next().getClass())) {
          return (List<T>) valueAsList;
        } else {
          throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.getName() + ", but was not found in " + valueAsList);
        }
      }
    }

    throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of type List from " + value);
  }

  /**
   * Retrieve typed value.
   *
   * @param value       raw value.
   * @param isTransient transient flag.
   *
   * @return typed value.
   */
  protected TypedValue getTypedValue(List<T> value, boolean isTransient) {
    return ValueWrapperUtil.getTypedValue(List.class, value, isTransient);
  }
}
