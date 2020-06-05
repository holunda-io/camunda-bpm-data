package io.holunda.camunda.bpm.data.example.kotlin.spin

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat
import org.camunda.spin.spi.DataFormatConfigurator
import org.camunda.spin.spi.TypeDetector

class CamundaBpmDataJacksonDataFormatConfigurator : DataFormatConfigurator<JacksonJsonDataFormat> {

    override fun configure(dataFormat: JacksonJsonDataFormat) {
        val objectMapper = dataFormat.objectMapper
        objectMapper.registerModule(KotlinModule())
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        // dataFormat.addTypeDetector(ErasedCollectionTypeDetector)
    }

    override fun getDataFormatClass(): Class<JacksonJsonDataFormat> = JacksonJsonDataFormat::class.java
}
/*
object ErasedCollectionTypeDetector : TypeDetector {
    override fun canHandle(value: Any): Boolean {
        return value is Set<*>
            || value is List<*>
            || value is Map<*, *>
    }

    override fun detectType(value: Any): String {
        return constructType(value).toCanonical()
    }
}

private fun bindingsArePresent(erasedType: Class<*>?, expected: Int): Boolean {
    if (erasedType == null) {
        return false
    }
    val varLen = erasedType.typeParameters?.size ?: 0
    if (varLen == 0) {
        return false
    }
    require(varLen == expected) { "Cannot create TypeBindings for class ${erasedType.name} with $expected type parameter: class expects $varLen type parameters." }
    return true
}

private fun constructType(value: Any): JavaType {
    val typeFactory = TypeFactory.defaultInstance()
    if (value is List<*> && value.isNotEmpty()) {
        val firstElement = value.first()!!
        if (bindingsArePresent(value.javaClass, 1)) {
            val elementType = constructType(firstElement)
            return typeFactory.constructCollectionType(value.javaClass, elementType)
        }
    } else if (value is Set<*> && value.isNotEmpty()) {
        val firstElement = value.first()!!
        if (bindingsArePresent(value.javaClass, 1)) {
            val elementType = constructType(firstElement)
            return typeFactory.constructCollectionType(value.javaClass, elementType)
        }
    } else if (value is Map<*, *> && value.entries.isNotEmpty()) {
        val firstEntry = value.entries.first()
        if (bindingsArePresent(firstEntry.javaClass, 2)) {
            val keyType = constructType(firstEntry.key!!)
            val valueType = constructType(firstEntry.value!!)
            return typeFactory.constructMapType(value.javaClass, keyType, valueType)
        }
    }
    return typeFactory.constructType(value.javaClass)
}
*/
