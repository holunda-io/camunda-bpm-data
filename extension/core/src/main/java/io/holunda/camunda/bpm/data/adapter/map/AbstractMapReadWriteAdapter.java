package io.holunda.camunda.bpm.data.adapter.map;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Collections;
import java.util.Map;

/**
 * Base class for all map type read write adapter.
 *
 * @param <K> key type.
 * @param <V> value type.
 */
public abstract class AbstractMapReadWriteAdapter<K, V> extends AbstractReadWriteAdapter<Map<K, V>> {

  /**
   * Key type.
   */
  protected final Class<K> keyClazz;
  /**
   * Value type.
   */
  protected final Class<V> valueClazz;

  /**
   * Constructs adapter.
   *
   * @param variableName name of the variable.
   * @param keyClazz     key class.
   * @param valueClazz   value class.
   */
  public AbstractMapReadWriteAdapter(String variableName, Class<K> keyClazz, Class<V> valueClazz) {
    super(variableName);
    this.keyClazz = keyClazz;
    this.valueClazz = valueClazz;
  }

  /**
   * Retrieves the value or null.
   *
   * @param value raw value.
   * @return set or null.
   */
  @SuppressWarnings("unchecked")
  protected Map<K, V> getOrNull(Object value) {
    if (value == null) {
      return null;
    }

    if (Map.class.isAssignableFrom(value.getClass())) {
      Map<?, ?> valueAsMap = (Map<?, ?>) value;
      if (valueAsMap.isEmpty()) {
        return Collections.emptyMap();
      } else {
        Map.Entry<?, ?> entry = valueAsMap.entrySet().iterator().next();
        if (keyClazz.isAssignableFrom(entry.getKey().getClass()) && valueClazz.isAssignableFrom(entry.getValue().getClass())) {
          return (Map<K, V>) valueAsMap;
        } else {
          throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong map type detected, expected Map<"
            + keyClazz.getName() + "," + valueClazz.getName()
            + ", but was not found in " + valueAsMap);
        }
      }
    }

    throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of type Map from " + value);
  }

  @Override
  public TypedValue getTypedValue(Object value, boolean isTransient) {
    return ValueWrapperUtil.getTypedValue(Map.class, value, isTransient);
  }
}
