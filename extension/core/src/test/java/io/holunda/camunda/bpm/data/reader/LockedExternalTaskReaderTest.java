package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Test;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LockedExternalTaskReaderTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");

  private final String value = "value";

  LockedExternalTask externalTask = mock(LockedExternalTask.class);
  private VariableReader reader = CamundaBpmData.reader(externalTask);


  @Before
  public void setUp() {
    when(externalTask.getVariables()).thenReturn(Variables.putValue(STRING.getName(), value));
  }

  @Test
  public void shouldDelegateGet() {
    assertThat(reader.get(STRING)).isEqualTo(value);
  }

  @Test
  public void shouldDelegateGetOptional() {
    assertThat(reader.getOptional(STRING)).hasValue(value);
    assertThat(reader.getOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocalOptional() {
    assertThatThrownBy(() -> reader.getLocalOptional(STRING))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }

  @Test
  public void shouldDelegateGetLocal() {
    assertThatThrownBy(() -> reader.getLocal(STRING))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }

  @Test
  public void shouldDelegateGetOrNull() {
    assertThat(reader.getOrNull(STRING)).isEqualTo(value);
    assertThat(reader.getOrNull(stringVariable("xxx"))).isNull();
  }

  @Test
  public void shouldDelegateGetLocalOrNull() {
    assertThatThrownBy(() -> reader.getLocalOrNull(STRING))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }

  @Test
  public void shouldDelegateGetOrDefault() {
    assertThat(reader.getOrDefault(STRING, "default")).isEqualTo(value);
    assertThat(reader.getOrDefault(stringVariable("xxx"), "default")).isEqualTo("default");

  }

  @Test
  public void shouldDelegateGetLocalOrDefault() {
    assertThatThrownBy(() -> reader.getLocalOrDefault(STRING, value))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }
}
