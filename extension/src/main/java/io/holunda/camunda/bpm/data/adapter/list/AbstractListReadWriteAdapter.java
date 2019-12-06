package io.holunda.camunda.bpm.data.adapter.list;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ReadAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Collections;
import java.util.List;

public abstract class AbstractListReadWriteAdapter<T> extends AbstractReadWriteAdapter<List<T>> {

  protected final Class<T> memberClazz;

  public AbstractListReadWriteAdapter(String variableName, Class<T> memberClazz) {
    super(variableName);
    this.memberClazz = memberClazz;
  }

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
          throw new IllegalStateException("Error reading " + variableName + ": Wrong list type detected, expected " + memberClazz.getName() + ", but was not found in " + valueAsList);
        }
      }
    }

    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read value of type List from " + value);
  }

  protected TypedValue getTypedValue(List<T> value, boolean isTransient) {
    return ValueWrapperUtil.getTypedValue(List.class, value, isTransient);
  }
}
