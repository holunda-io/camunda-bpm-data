package io.holunda.camunda.bpm.data.adapter;

import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Date;

import static org.camunda.bpm.engine.variable.Variables.*;


public class ValueWrapperUtil {

  public static <T> TypedValue getTypedValue(Class<T> clazz, Object value, boolean isTransient) {
    if (String.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return stringValue((String) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Boolean.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return booleanValue((Boolean) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Integer.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return integerValue((Integer) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Short.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return shortValue((Short) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Long.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return longValue((Long) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Date.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return dateValue((Date) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Double.class.equals(clazz)) {
      if (value.getClass().isAssignableFrom(clazz)) {
        return doubleValue((Double) value, isTransient);
      } else {
        throw new IllegalArgumentException("Couldn't create TypedValue for " + clazz.getSimpleName() + " from value " + value);
      }
    } else if (Object.class.equals(clazz)) {
        return objectValue(value, isTransient).create();
    } else {
      // fallback for null-type
      return untypedValue(value, isTransient);
    }
  }

}
