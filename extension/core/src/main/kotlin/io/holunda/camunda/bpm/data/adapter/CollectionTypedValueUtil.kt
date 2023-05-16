package io.holunda.camunda.bpm.data.adapter

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.MapType
import org.camunda.bpm.engine.variable.impl.value.ObjectValueImpl
import org.camunda.bpm.engine.variable.value.TypedValue

/**
 * Helper class to read values from collections.
 */
object CollectionTypedValueUtil {
    /**
     * Tries to read a collection type from a typed value containing the serialized json.
     *
     * @param typedValue     value from camunda.
     * @param variableName   name of the variable.
     * @param memberClazz    clazz of the collection member.
     * @param objectMapper   jackson object mapper.
     * @param collectionType constructed collection type.
     * @param [T]            type of collection member.
     * @param [C]            type of collection.
     * @return collection of specified type or null.
     */
    fun <T, C : Collection<T>?> readFromTypedValue(
        typedValue: TypedValue?,
        variableName: String,
        memberClazz: Class<T>,
        objectMapper: ObjectMapper,
        collectionType: CollectionType?
    ): C? {
        if (typedValue == null) {
            return null
        }
        return if (typedValue is ObjectValueImpl) {
            val json = typedValue.valueSerialized
            try {
                val values = objectMapper.readValue<C>(json, collectionType)
                if (values!!.isEmpty()) {
                    values
                } else {
                    val value: T = values.iterator().next()
                    if (memberClazz.isAssignableFrom(value!!::class.java)) {
                        values
                    } else {
                        throw WrongVariableTypeException("Error reading " + variableName + ": Wrong member type detected, expected " + memberClazz.name + ", but was not found in " + values)
                    }
                }
            } catch (jpe: JsonProcessingException) {
                throw WrongVariableTypeException("Error reading $variableName: Couldn't read value from $json")
            }
        } else {
            throw WrongVariableTypeException("Error reading $variableName: Couldn't read value from $typedValue")
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
     * @param [K]          type of map key.
     * @param [V]          type of myp value.
     * @return map of specified types or null.
     */
    fun <K, V> readFromTypedValue(
        typedValue: TypedValue?, variableName: String, keyClazz: Class<K>, valueClazz: Class<V>,
        objectMapper: ObjectMapper, mapType: MapType?
    ): Map<K, V>? {
        if (typedValue == null) {
            return null
        }
        return if (typedValue is ObjectValueImpl) {
            val json = typedValue.valueSerialized
            try {
                val map = objectMapper.readValue<Map<K, V>>(json, mapType)
                if (map.isEmpty()) {
                    map
                } else {
                    val (key, value) = map.entries.iterator().next()
                    if (valueClazz.isAssignableFrom(value!!::class.java) && keyClazz.isAssignableFrom(key!!::class.java)) {
                        map
                    } else {
                        throw WrongVariableTypeException(
                            "Error reading " + variableName + ": Wrong map type detected, expected key "
                                    + keyClazz.name + " and value " + valueClazz.name + ", but was not found in " + map
                        )
                    }
                }
            } catch (jpe: JsonProcessingException) {
                throw WrongVariableTypeException("Error reading $variableName: Couldn't read value from $json")
            }
        } else {
            throw WrongVariableTypeException("Error reading $variableName: Couldn't read value from $typedValue")
        }
    }
}
