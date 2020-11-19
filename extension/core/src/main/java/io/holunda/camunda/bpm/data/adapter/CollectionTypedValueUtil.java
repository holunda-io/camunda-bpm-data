package io.holunda.camunda.bpm.data.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import org.camunda.bpm.engine.variable.impl.value.ObjectValueImpl;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.Collection;
import java.util.Map;

/**
 * Helper class to read values from collections.
 */
public class CollectionTypedValueUtil {

  /**
   * Avoid instantiations.
   */
  private CollectionTypedValueUtil() {

  }
  /**
   * Tries to read a collection type from a typed value containing the serialized json.
   *
   * @param typedValue     value from camunda.
   * @param variableName   name of the variable.
   * @param memberClazz    clazz of the collection member.
   * @param objectMapper   jackson object mapper.
   * @param collectionType constructed collection type.
   * @param <T>            type of collection member.
   * @param <C>            type of collection.
   * @return collection of specified type or null.
   */
  public static <T, C extends Collection<T>> C readFromTypedValue(
    TypedValue typedValue, String variableName, Class<T> memberClazz, ObjectMapper objectMapper, CollectionType collectionType) {
    if (typedValue == null) {
      return null;
    }
    if (typedValue instanceof ObjectValueImpl) {
      final ObjectValue objectValue = (ObjectValueImpl) typedValue;
      final String json = objectValue.getValueSerialized();
      try {
        final C values = objectMapper.readValue(json, collectionType);
        if (values.isEmpty()) {
          return values;
        } else {
          if (memberClazz.isAssignableFrom(values.iterator().next().getClass())) {
            return values;
          } else {
            throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong member type detected, expected " + memberClazz.getName() + ", but was not found in " + values);
          }
        }
      } catch (JsonProcessingException jpe) {
        throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value from " + json);
      }
    } else {
      throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value from " + typedValue);
    }
  }

  /**
   * Tries to read a collection type from a typed value containing the serialized json.
   *
   * @param typedValue   value from camunda.
   * @param variableName name of the variable.
   * @param keyClazz     clazz of the map key.
   * @param valueClazz   clazz of the map value.
   * @param objectMapper jackson object mapper.
   * @param mapType      constructed map type.
   * @param <K>          type of map key.
   * @param <V>          type of myp value.
   * @return map of specified types or null.
   */
  public static <K, V> Map<K, V> readFromTypedValue(
    TypedValue typedValue, String variableName, Class<K> keyClazz, Class<V> valueClazz,
    ObjectMapper objectMapper, MapType mapType) {
    if (typedValue == null) {
      return null;
    }
    if (typedValue instanceof ObjectValueImpl) {
      final ObjectValue objectValue = (ObjectValueImpl) typedValue;
      final String json = objectValue.getValueSerialized();
      try {
        final Map<K, V> map = objectMapper.readValue(json, mapType);
        if (map.isEmpty()) {
          return map;
        } else {
          final Map.Entry<K, V> entry = map.entrySet().iterator().next();
          if (valueClazz.isAssignableFrom(entry.getValue().getClass()) && keyClazz.isAssignableFrom(entry.getKey().getClass())) {
            return map;
          } else {
            throw new WrongVariableTypeException("Error reading " + variableName + ": Wrong map type detected, expected key "
              + keyClazz.getName() + " and value " + valueClazz.getName() + ", but was not found in " + map);
          }
        }
      } catch (JsonProcessingException jpe) {
        throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value from " + json);
      }
    } else {
      throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value from " + typedValue);
    }
  }
}
