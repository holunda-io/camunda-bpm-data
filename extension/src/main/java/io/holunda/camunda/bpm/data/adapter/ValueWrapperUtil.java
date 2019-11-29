package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Date;

import static org.camunda.bpm.engine.variable.Variables.*;

/**
 * Static util methods.
 */
public class ValueWrapperUtil {

  /**
   * Hide instantiation.
   */
  private ValueWrapperUtil() {

  }

  /**
   * Delivers typed value for a given type and value.
   * @param clazz class of value.
   * @param value value to encapsulate.
   * @param isTransient transient flag.
   * @param <T> type of value.
   * @exception IllegalArgumentException if value and clazz are incompatible.
   * @return typed value.
   */
  public static <T> TypedValue getTypedValue(Class<T> clazz, Object value, boolean isTransient) {
    if (!clazz.isAssignableFrom(value.getClass())) {
      throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
    }

    if (String.class.equals(clazz)) {
      return stringValue((String) value, isTransient);
    } else if (Boolean.class.equals(clazz)) {
      return booleanValue((Boolean) value, isTransient);
    } else if (Integer.class.equals(clazz)) {
      return integerValue((Integer) value, isTransient);
    } else if (Short.class.equals(clazz)) {
      return shortValue((Short) value, isTransient);
    } else if (Long.class.equals(clazz)) {
      return longValue((Long) value, isTransient);
    } else if (Date.class.equals(clazz)) {
      return dateValue((Date) value, isTransient);
    } else if (Double.class.equals(clazz)) {
      return doubleValue((Double) value, isTransient);
    } else if (Object.class.equals(clazz)) {
      return objectValue(value, isTransient).create();
    } else {
      // fallback for null-type
      return untypedValue(value, isTransient);
    }
  }

}
