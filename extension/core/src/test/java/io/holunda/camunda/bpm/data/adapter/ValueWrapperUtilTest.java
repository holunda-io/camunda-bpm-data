package io.holunda.camunda.bpm.data.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.Date;

import org.camunda.bpm.engine.variable.type.PrimitiveValueType;
import org.camunda.bpm.engine.variable.value.BooleanValue;
import org.camunda.bpm.engine.variable.value.DateValue;
import org.camunda.bpm.engine.variable.value.DoubleValue;
import org.camunda.bpm.engine.variable.value.IntegerValue;
import org.camunda.bpm.engine.variable.value.LongValue;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.ShortValue;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.junit.jupiter.api.Test;

public class ValueWrapperUtilTest {

  @Test
  public void shouldReturnDoubleValue() {
    TypedValue doubleValue = ValueWrapperUtil.getTypedValue(Double.class, Double.MIN_VALUE, false);
    assertThat(doubleValue).isInstanceOf(DoubleValue.class);
    assertThat(doubleValue.getType()).isExactlyInstanceOf(PrimitiveValueType.DOUBLE.getClass());
    assertThat(doubleValue.getValue()).isEqualTo(Double.MIN_VALUE);
    assertThat(doubleValue.isTransient()).isFalse();

    doubleValue = ValueWrapperUtil.getTypedValue(Double.class, Double.MAX_VALUE, true);
    assertThat(doubleValue).isInstanceOf(DoubleValue.class);
    assertThat(doubleValue.getType()).isExactlyInstanceOf(PrimitiveValueType.DOUBLE.getClass());
    assertThat(doubleValue.getValue()).isEqualTo(Double.MAX_VALUE);
    assertThat(doubleValue.isTransient()).isTrue();

    doubleValue = ValueWrapperUtil.getTypedValue(Double.class, null, true);
    assertThat(doubleValue).isInstanceOf(DoubleValue.class);
    assertThat(doubleValue.getType()).isExactlyInstanceOf(PrimitiveValueType.DOUBLE.getClass());
    assertThat(doubleValue.getValue()).isNull();
    assertThat(doubleValue.isTransient()).isTrue();
  }

  @Test
  public void shouldReturnIntegerValue() {
    TypedValue integerValue = ValueWrapperUtil.getTypedValue(Integer.class, Integer.MIN_VALUE, false);
    assertThat(integerValue).isInstanceOf(IntegerValue.class);
    assertThat(integerValue.getType()).isExactlyInstanceOf(PrimitiveValueType.INTEGER.getClass());
    assertThat(integerValue.getValue()).isEqualTo(Integer.MIN_VALUE);
    assertThat(integerValue.isTransient()).isFalse();

    integerValue = ValueWrapperUtil.getTypedValue(Integer.class, Integer.MAX_VALUE, true);
    assertThat(integerValue).isInstanceOf(IntegerValue.class);
    assertThat(integerValue.getType()).isExactlyInstanceOf(PrimitiveValueType.INTEGER.getClass());
    assertThat(integerValue.getValue()).isEqualTo(Integer.MAX_VALUE);
    assertThat(integerValue.isTransient()).isTrue();

    integerValue = ValueWrapperUtil.getTypedValue(Integer.class, null, true);
    assertThat(integerValue).isInstanceOf(IntegerValue.class);
    assertThat(integerValue.getType()).isExactlyInstanceOf(PrimitiveValueType.INTEGER.getClass());
    assertThat(integerValue.getValue()).isNull();
    assertThat(integerValue.isTransient()).isTrue();

  }

  @Test
  public void shouldReturnDateValue() {
    Date value = new Date();

    TypedValue dateValue = ValueWrapperUtil.getTypedValue(Date.class, value.clone(), false);
    assertThat(dateValue).isInstanceOf(DateValue.class);
    assertThat(dateValue.getType()).isExactlyInstanceOf(PrimitiveValueType.DATE.getClass());
    assertThat(dateValue.getValue()).isEqualTo(value);
    assertThat(dateValue.isTransient()).isFalse();

    dateValue = ValueWrapperUtil.getTypedValue(Date.class, value.clone(), true);
    assertThat(dateValue).isInstanceOf(DateValue.class);
    assertThat(dateValue.getType()).isExactlyInstanceOf(PrimitiveValueType.DATE.getClass());
    assertThat(dateValue.getValue()).isEqualTo(value);
    assertThat(dateValue.isTransient()).isTrue();

    dateValue = ValueWrapperUtil.getTypedValue(Date.class, null, true);
    assertThat(dateValue).isInstanceOf(DateValue.class);
    assertThat(dateValue.getType()).isExactlyInstanceOf(PrimitiveValueType.DATE.getClass());
    assertThat(dateValue.getValue()).isNull();
    assertThat(dateValue.isTransient()).isTrue();

  }

  @Test
  public void shouldReturnBooleanValue() {
    TypedValue booleanValue = ValueWrapperUtil.getTypedValue(Boolean.class, Boolean.TRUE, false);
    assertThat(booleanValue).isInstanceOf(BooleanValue.class);
    assertThat(booleanValue.getType()).isExactlyInstanceOf(PrimitiveValueType.BOOLEAN.getClass());
    assertThat(booleanValue.getValue()).isEqualTo(Boolean.TRUE);
    assertThat(booleanValue.isTransient()).isFalse();

    booleanValue = ValueWrapperUtil.getTypedValue(Boolean.class, Boolean.FALSE, true);
    assertThat(booleanValue).isInstanceOf(BooleanValue.class);
    assertThat(booleanValue.getType()).isExactlyInstanceOf(PrimitiveValueType.BOOLEAN.getClass());
    assertThat(booleanValue.getValue()).isEqualTo(Boolean.FALSE);
    assertThat(booleanValue.isTransient()).isTrue();

    booleanValue = ValueWrapperUtil.getTypedValue(Boolean.class, null, true);
    assertThat(booleanValue).isInstanceOf(BooleanValue.class);
    assertThat(booleanValue.getType()).isExactlyInstanceOf(PrimitiveValueType.BOOLEAN.getClass());
    assertThat(booleanValue.getValue()).isNull();
    assertThat(booleanValue.isTransient()).isTrue();

  }

  @Test
  public void shouldReturnLongValue() {
    TypedValue longValue = ValueWrapperUtil.getTypedValue(Long.class, Long.MIN_VALUE, false);
    assertThat(longValue).isInstanceOf(LongValue.class);
    assertThat(longValue.getType()).isExactlyInstanceOf(PrimitiveValueType.LONG.getClass());
    assertThat(longValue.getValue()).isEqualTo(Long.MIN_VALUE);
    assertThat(longValue.isTransient()).isFalse();

    longValue = ValueWrapperUtil.getTypedValue(Long.class, Long.MAX_VALUE, true);
    assertThat(longValue).isInstanceOf(LongValue.class);
    assertThat(longValue.getType()).isExactlyInstanceOf(PrimitiveValueType.LONG.getClass());
    assertThat(longValue.getValue()).isEqualTo(Long.MAX_VALUE);
    assertThat(longValue.isTransient()).isTrue();

    longValue = ValueWrapperUtil.getTypedValue(Long.class, null, true);
    assertThat(longValue).isInstanceOf(LongValue.class);
    assertThat(longValue.getType()).isExactlyInstanceOf(PrimitiveValueType.LONG.getClass());
    assertThat(longValue.getValue()).isNull();
    assertThat(longValue.isTransient()).isTrue();

  }

  @Test
  public void shouldReturnStringValue() {
    TypedValue stringValue = ValueWrapperUtil.getTypedValue(String.class, "foo", false);
    assertThat(stringValue).isInstanceOf(StringValue.class);
    assertThat(stringValue.getType()).isExactlyInstanceOf(PrimitiveValueType.STRING.getClass());
    assertThat(stringValue.getValue()).isEqualTo("foo");
    assertThat(stringValue.isTransient()).isFalse();

    stringValue = ValueWrapperUtil.getTypedValue(String.class, "foo", true);
    assertThat(stringValue).isInstanceOf(StringValue.class);
    assertThat(stringValue.getType()).isExactlyInstanceOf(PrimitiveValueType.STRING.getClass());
    assertThat(stringValue.getValue()).isEqualTo("foo");
    assertThat(stringValue.isTransient()).isTrue();

    stringValue = ValueWrapperUtil.getTypedValue(String.class, null, true);
    assertThat(stringValue).isInstanceOf(StringValue.class);
    assertThat(stringValue.getType()).isExactlyInstanceOf(PrimitiveValueType.STRING.getClass());
    assertThat(stringValue.getValue()).isNull();
    assertThat(stringValue.isTransient()).isTrue();
  }

  @Test
  public void shouldReturnShortValue() {
    TypedValue shortValue = ValueWrapperUtil.getTypedValue(Short.class, Short.MIN_VALUE, false);
    assertThat(shortValue).isInstanceOf(ShortValue.class);
    assertThat(shortValue.getType()).isExactlyInstanceOf(PrimitiveValueType.SHORT.getClass());
    assertThat(shortValue.getValue()).isEqualTo(Short.MIN_VALUE);
    assertThat(shortValue.isTransient()).isFalse();

    shortValue = ValueWrapperUtil.getTypedValue(Short.class, Short.MAX_VALUE, true);
    assertThat(shortValue).isInstanceOf(ShortValue.class);
    assertThat(shortValue.getType()).isExactlyInstanceOf(PrimitiveValueType.SHORT.getClass());
    assertThat(shortValue.getValue()).isEqualTo(Short.MAX_VALUE);
    assertThat(shortValue.isTransient()).isTrue();

    shortValue = ValueWrapperUtil.getTypedValue(Short.class, null, true);
    assertThat(shortValue).isInstanceOf(ShortValue.class);
    assertThat(shortValue.getType()).isExactlyInstanceOf(PrimitiveValueType.SHORT.getClass());
    assertThat(shortValue.getValue()).isNull();
    assertThat(shortValue.isTransient()).isTrue();

  }

  @Test
  public void shouldReturnObjectValue() {
    Instant value = Instant.now();
    TypedValue objectValue = ValueWrapperUtil.getTypedValue(Object.class, value, false);
    assertThat(objectValue).isInstanceOf(ObjectValue.class);
    assertThat(objectValue.getType()).isExactlyInstanceOf(PrimitiveValueType.OBJECT.getClass());
    assertThat(objectValue.getValue()).isEqualTo(value);
    assertThat(objectValue.isTransient()).isFalse();

    objectValue = ValueWrapperUtil.getTypedValue(Object.class, value, true);
    assertThat(objectValue).isInstanceOf(ObjectValue.class);
    assertThat(objectValue.getType()).isExactlyInstanceOf(PrimitiveValueType.OBJECT.getClass());
    assertThat(objectValue.getValue()).isEqualTo(value);
    assertThat(objectValue.isTransient()).isTrue();

    objectValue = ValueWrapperUtil.getTypedValue(Object.class, null, true);
    assertThat(objectValue).isInstanceOf(ObjectValue.class);
    assertThat(objectValue.getType()).isExactlyInstanceOf(PrimitiveValueType.OBJECT.getClass());
    assertThat(objectValue.getValue()).isNull();
    assertThat(objectValue.isTransient()).isTrue();

  }

  @Test
  public void shouldReturnUntypedValue() {
    Instant value = Instant.now();
    TypedValue untypedValue = ValueWrapperUtil.getTypedValue(Instant.class, value, false);
    assertThat(untypedValue.getType()).isNull();
    assertThat(untypedValue.getValue()).isEqualTo(value);
    assertThat(untypedValue.isTransient()).isFalse();

    untypedValue = ValueWrapperUtil.getTypedValue(Instant.class, value, true);
    assertThat(untypedValue.getType()).isNull();
    assertThat(untypedValue.getValue()).isEqualTo(value);
    assertThat(untypedValue.isTransient()).isTrue();

    untypedValue = ValueWrapperUtil.getTypedValue(Instant.class, null, true);
    assertThat(untypedValue.getType()).isInstanceOf(PrimitiveValueType.NULL.getClass());
    assertThat(untypedValue.getValue()).isNull();
    assertThat(untypedValue.isTransient()).isTrue();

  }

  @Test
  public void shouldThrowExceptionOnWrongType() {
    assertThatThrownBy(() -> ValueWrapperUtil.getTypedValue(Date.class, Instant.now(), false))
      .isInstanceOf(IllegalArgumentException.class);
  }

}
