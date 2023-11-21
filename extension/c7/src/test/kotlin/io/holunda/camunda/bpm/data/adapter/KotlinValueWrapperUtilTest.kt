package io.holunda.camunda.bpm.data.adapter

import org.assertj.core.api.Assertions
import org.camunda.bpm.engine.variable.type.PrimitiveValueType
import org.camunda.bpm.engine.variable.value.*
import org.junit.jupiter.api.Test

/**
 * Check value wrapper util for Kotlin classes.
 *
 * @see [io.holunda.camunda.bpm.data.adapter.ValueWrapperUtilTest]
 */
class KotlinValueWrapperUtilTest {
  @Test
  fun shouldReturnDoubleValue() {
    var doubleValue = ValueWrapperUtil.getTypedValue(Double::class.java, Double.MIN_VALUE, false)
    Assertions.assertThat(doubleValue).isInstanceOf(DoubleValue::class.java)
    Assertions.assertThat(doubleValue.type).isExactlyInstanceOf(PrimitiveValueType.DOUBLE.javaClass)
    Assertions.assertThat(doubleValue.value).isEqualTo(Double.MIN_VALUE)
    Assertions.assertThat(doubleValue.isTransient).isFalse
    doubleValue = ValueWrapperUtil.getTypedValue(Double::class.java, Double.MAX_VALUE, true)
    Assertions.assertThat(doubleValue).isInstanceOf(DoubleValue::class.java)
    Assertions.assertThat(doubleValue.type).isExactlyInstanceOf(PrimitiveValueType.DOUBLE.javaClass)
    Assertions.assertThat(doubleValue.value).isEqualTo(Double.MAX_VALUE)
    Assertions.assertThat(doubleValue.isTransient).isTrue
    doubleValue = ValueWrapperUtil.getTypedValue(Double::class.java, null, true)
    Assertions.assertThat(doubleValue).isInstanceOf(DoubleValue::class.java)
    Assertions.assertThat(doubleValue.type).isExactlyInstanceOf(PrimitiveValueType.DOUBLE.javaClass)
    Assertions.assertThat(doubleValue.value).isNull()
    Assertions.assertThat(doubleValue.isTransient).isTrue
  }

  @Test
  fun shouldReturnIntegerValue() {
    var integerValue = ValueWrapperUtil.getTypedValue(Int::class.java, Int.MIN_VALUE, false)
    Assertions.assertThat(integerValue).isInstanceOf(IntegerValue::class.java)
    Assertions.assertThat(integerValue.type).isExactlyInstanceOf(PrimitiveValueType.INTEGER.javaClass)
    Assertions.assertThat(integerValue.value).isEqualTo(Int.MIN_VALUE)
    Assertions.assertThat(integerValue.isTransient).isFalse
    integerValue = ValueWrapperUtil.getTypedValue(Int::class.java, Int.MAX_VALUE, true)
    Assertions.assertThat(integerValue).isInstanceOf(IntegerValue::class.java)
    Assertions.assertThat(integerValue.type).isExactlyInstanceOf(PrimitiveValueType.INTEGER.javaClass)
    Assertions.assertThat(integerValue.value).isEqualTo(Int.MAX_VALUE)
    Assertions.assertThat(integerValue.isTransient).isTrue
    integerValue = ValueWrapperUtil.getTypedValue(Int::class.java, null, true)
    Assertions.assertThat(integerValue).isInstanceOf(IntegerValue::class.java)
    Assertions.assertThat(integerValue.type).isExactlyInstanceOf(PrimitiveValueType.INTEGER.javaClass)
    Assertions.assertThat(integerValue.value).isNull()
    Assertions.assertThat(integerValue.isTransient).isTrue

    integerValue = ValueWrapperUtil.getTypedValue(Integer::class.java, Int.MIN_VALUE, false)
    Assertions.assertThat(integerValue).isInstanceOf(IntegerValue::class.java)
    Assertions.assertThat(integerValue.type).isExactlyInstanceOf(PrimitiveValueType.INTEGER.javaClass)
    Assertions.assertThat(integerValue.value).isEqualTo(Int.MIN_VALUE)
    Assertions.assertThat(integerValue.isTransient).isFalse
    integerValue = ValueWrapperUtil.getTypedValue(Integer::class.java, Int.MAX_VALUE, true)
    Assertions.assertThat(integerValue).isInstanceOf(IntegerValue::class.java)
    Assertions.assertThat(integerValue.type).isExactlyInstanceOf(PrimitiveValueType.INTEGER.javaClass)
    Assertions.assertThat(integerValue.value).isEqualTo(Int.MAX_VALUE)
    Assertions.assertThat(integerValue.isTransient).isTrue
    integerValue = ValueWrapperUtil.getTypedValue(Integer::class.java, null, true)
    Assertions.assertThat(integerValue).isInstanceOf(IntegerValue::class.java)
    Assertions.assertThat(integerValue.type).isExactlyInstanceOf(PrimitiveValueType.INTEGER.javaClass)
    Assertions.assertThat(integerValue.value).isNull()
    Assertions.assertThat(integerValue.isTransient).isTrue

  }

  @Test
  fun shouldReturnBooleanValue() {
    var booleanValue = ValueWrapperUtil.getTypedValue(Boolean::class.java, java.lang.Boolean.TRUE, false)
    Assertions.assertThat(booleanValue).isInstanceOf(BooleanValue::class.java)
    Assertions.assertThat(booleanValue.type).isExactlyInstanceOf(PrimitiveValueType.BOOLEAN.javaClass)
    Assertions.assertThat(booleanValue.value).isEqualTo(java.lang.Boolean.TRUE)
    Assertions.assertThat(booleanValue.isTransient).isFalse
    booleanValue = ValueWrapperUtil.getTypedValue(Boolean::class.java, java.lang.Boolean.FALSE, true)
    Assertions.assertThat(booleanValue).isInstanceOf(BooleanValue::class.java)
    Assertions.assertThat(booleanValue.type).isExactlyInstanceOf(PrimitiveValueType.BOOLEAN.javaClass)
    Assertions.assertThat(booleanValue.value).isEqualTo(java.lang.Boolean.FALSE)
    Assertions.assertThat(booleanValue.isTransient).isTrue
    booleanValue = ValueWrapperUtil.getTypedValue(Boolean::class.java, null, true)
    Assertions.assertThat(booleanValue).isInstanceOf(BooleanValue::class.java)
    Assertions.assertThat(booleanValue.type).isExactlyInstanceOf(PrimitiveValueType.BOOLEAN.javaClass)
    Assertions.assertThat(booleanValue.value).isNull()
    Assertions.assertThat(booleanValue.isTransient).isTrue
  }

  @Test
  fun shouldReturnLongValue() {
    var longValue = ValueWrapperUtil.getTypedValue(Long::class.java, Long.MIN_VALUE, false)
    Assertions.assertThat(longValue).isInstanceOf(LongValue::class.java)
    Assertions.assertThat(longValue.type).isExactlyInstanceOf(PrimitiveValueType.LONG.javaClass)
    Assertions.assertThat(longValue.value).isEqualTo(Long.MIN_VALUE)
    Assertions.assertThat(longValue.isTransient).isFalse
    longValue = ValueWrapperUtil.getTypedValue(Long::class.java, Long.MAX_VALUE, true)
    Assertions.assertThat(longValue).isInstanceOf(LongValue::class.java)
    Assertions.assertThat(longValue.type).isExactlyInstanceOf(PrimitiveValueType.LONG.javaClass)
    Assertions.assertThat(longValue.value).isEqualTo(Long.MAX_VALUE)
    Assertions.assertThat(longValue.isTransient).isTrue
    longValue = ValueWrapperUtil.getTypedValue(Long::class.java, null, true)
    Assertions.assertThat(longValue).isInstanceOf(LongValue::class.java)
    Assertions.assertThat(longValue.type).isExactlyInstanceOf(PrimitiveValueType.LONG.javaClass)
    Assertions.assertThat(longValue.value).isNull()
    Assertions.assertThat(longValue.isTransient).isTrue
  }


  @Test
  fun shouldReturnShortValue() {
    var shortValue = ValueWrapperUtil.getTypedValue(Short::class.java, Short.MIN_VALUE, false)
    Assertions.assertThat(shortValue).isInstanceOf(ShortValue::class.java)
    Assertions.assertThat(shortValue.type).isExactlyInstanceOf(PrimitiveValueType.SHORT.javaClass)
    Assertions.assertThat(shortValue.value).isEqualTo(Short.MIN_VALUE)
    Assertions.assertThat(shortValue.isTransient).isFalse
    shortValue = ValueWrapperUtil.getTypedValue(Short::class.java, Short.MAX_VALUE, true)
    Assertions.assertThat(shortValue).isInstanceOf(ShortValue::class.java)
    Assertions.assertThat(shortValue.type).isExactlyInstanceOf(PrimitiveValueType.SHORT.javaClass)
    Assertions.assertThat(shortValue.value).isEqualTo(Short.MAX_VALUE)
    Assertions.assertThat(shortValue.isTransient).isTrue
    shortValue = ValueWrapperUtil.getTypedValue(Short::class.java, null, true)
    Assertions.assertThat(shortValue).isInstanceOf(ShortValue::class.java)
    Assertions.assertThat(shortValue.type).isExactlyInstanceOf(PrimitiveValueType.SHORT.javaClass)
    Assertions.assertThat(shortValue.value).isNull()
    Assertions.assertThat(shortValue.isTransient).isTrue
  }

  @Test
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
  fun shouldBeAssignableFromJavaDoubleToKotlinDouble() {
    val variable = 1.0 as java.lang.Double
    Assertions.assertThat(ValueWrapperUtil.isAssignableFrom(Double::class.java, variable.javaClass)).isTrue
  }

  @Test
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
  fun shouldBeAssignableFromJavaIntegerToKotlinInteger() {
    val variable = 1 as Integer
    Assertions.assertThat(ValueWrapperUtil.isAssignableFrom(Int::class.java, variable.javaClass)).isTrue
  }

  @Test
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
  fun shouldBeAssignableFromJavaBooleanToKotlinBoolean() {
    val variable = true as java.lang.Boolean
    Assertions.assertThat(ValueWrapperUtil.isAssignableFrom(Boolean::class.java, variable.javaClass)).isTrue
  }

  @Test
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
  fun shouldBeAssignableFromJavaLongToKotlinLong() {
    val variable = 1L as java.lang.Long
    Assertions.assertThat(ValueWrapperUtil.isAssignableFrom(Long::class.java, variable.javaClass)).isTrue
  }

  @Test
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
  fun shouldBeAssignableFromJavaShortToKotlinShort() {
    val variable = java.lang.Short.valueOf(1)
    Assertions.assertThat(ValueWrapperUtil.isAssignableFrom(Short::class.java, variable.javaClass)).isTrue
  }

}
