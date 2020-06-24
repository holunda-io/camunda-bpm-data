package io.holunda.camunda.bpm.data.reader;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.Test;

public class VariableMapReaderTest {

  private static final VariableFactory<String> STRING = CamundaBpmData.stringVariable("myString");

  private final String value = "value";

  private final VariableMap variableMap = CamundaBpmData.builder().set(STRING, value).build();

  @Test
  public void shouldDelegateGet() {
    assertThat(CamundaBpmData.reader(variableMap).get(STRING)).isEqualTo(value);
  }

  @Test
  public void shouldDelegateGetOptional() {
    assertThat(CamundaBpmData.reader(variableMap).getOptional(STRING)).hasValue(value);
    assertThat(CamundaBpmData.reader(variableMap).getOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocalOptional() {
    assertThatThrownBy(() -> CamundaBpmData.reader(variableMap).getLocalOptional(STRING)).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  public void shouldDelegateGetLocal() {
    assertThatThrownBy(() -> CamundaBpmData.reader(variableMap).getLocalOptional(STRING)).isInstanceOf(UnsupportedOperationException.class);
  }
}
