package io.holunda.camunda.bpm.data.adapter

import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.value.TypedValue
import java.io.File
import java.util.*

/**
 * Static util methods to detect Camunda's value type best fitting to the requested class.
 */
object ValueWrapperUtil {

  /**
   * Delivers typed value for a given type and value.
   * Supports every type except byteArray.
   *
   * @param clazz       class of value.
   * @param value       value to encapsulate.
   * @param isTransient transient flag.
   * @param <T>         type of value.
   * @return typed value.
   * @throws IllegalArgumentException if value and clazz are incompatible.
  </T> */
  @JvmStatic
  fun <T> getTypedValue(clazz: Class<T>, value: Any?, isTransient: Boolean): TypedValue {
    require(
      !(value != null && !isAssignableFrom(
        clazz,
        value.javaClass
      ))
    ) { "Couldn't create TypedValue for '" + clazz.name + "' from value '" + value + "'" }

    return when (clazz) {
      String::class.java -> Variables.stringValue(value as String?, isTransient)
      Boolean::class.java, java.lang.Boolean::class.java -> Variables.booleanValue(value as Boolean?, isTransient)
      Int::class.java, java.lang.Integer::class.java -> Variables.integerValue(value as Int?, isTransient)
      Short::class.java, java.lang.Short::class.java -> Variables.shortValue(value as Short?, isTransient)
      Long::class.java, java.lang.Long::class.java -> Variables.longValue(value as Long?, isTransient)
      Date::class.java -> Variables.dateValue(value as Date?, isTransient)
      Double::class.java, java.lang.Double::class.java -> Variables.doubleValue(value as Double?, isTransient)
      File::class.java -> Variables.fileValue(value as File?, isTransient)
      UUID::class.java -> Variables.stringValue((value as UUID?)?.toString(), isTransient)
      Object::class.java -> Variables.objectValue(value, isTransient).create() // explicit java.lang.Object as a type
      else -> if (value == null) {
        Variables.untypedNullValue(isTransient)
      } else {
        // fallback for unknown-type
        Variables.untypedValue(value, isTransient)
      }
    }
  }

  /**
   * Checks type compatibility.
   *
   * @param requestedType requested class.
   * @param typeOfValue   class extracted from the value.
   * @return true, if the type of value is assignable from the requested type or its unboxed version
   */
  @JvmStatic
  fun isAssignableFrom(requestedType: Class<*>, typeOfValue: Class<*>): Boolean =
    when {
      requestedType.isPrimitive -> requestedType.kotlin.javaObjectType.isAssignableFrom(typeOfValue)
      requestedType.isAssignableFrom(typeOfValue) -> true
      else -> false
    }
}
