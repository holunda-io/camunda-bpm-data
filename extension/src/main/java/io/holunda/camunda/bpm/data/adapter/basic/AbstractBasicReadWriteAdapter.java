package io.holunda.camunda.bpm.data.adapter.basic;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.List;

public abstract class AbstractBasicReadWriteAdapter<T> extends AbstractReadWriteAdapter<T> {
  protected final Class<T> clazz;

  public AbstractBasicReadWriteAdapter(String variableName, Class<T> clazz) {
    super(variableName);
    this.clazz = clazz;
  }

  @SuppressWarnings("unchecked")
  protected T getOrNull(Object value) {

    if (value == null) {
      return null;
    }

    if (clazz.isAssignableFrom(value.getClass())) {
      return (T) value;
    }
    throw new IllegalStateException("Error reading " + variableName + ": Couldn't read value of " + clazz + " from " + value);
  }

  protected TypedValue getTypedValue(Object value, boolean isTransient) {
    return ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
  }

}
