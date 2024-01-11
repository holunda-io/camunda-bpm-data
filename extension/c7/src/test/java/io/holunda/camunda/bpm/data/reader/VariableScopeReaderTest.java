package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.DelegateExecutionFake;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;

// Testing is a bit different here because DelegateExecutionFake does not correctly support local variables.
public class VariableScopeReaderTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");

  private final String value = "value";
  private final String localValue = "localValue";


  @Test
  public void shouldDelegateGet() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).set(value);
    assertThat(CamundaBpmData.reader(execution).get(STRING)).isEqualTo(value);
  }

  @Test
  public void shouldDelegateGetOptional() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).set(value);
    assertThat(CamundaBpmData.reader(execution).getOptional(STRING)).hasValue(value);
    assertThat(CamundaBpmData.reader(execution).getOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocalOptional() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).setLocal(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocalOptional(STRING)).hasValue(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocalOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocal() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).setLocal(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocal(STRING)).isEqualTo(localValue);
  }

  @Test
  public void shouldDelegateGetOrNull() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).set(value);
    assertThat(CamundaBpmData.reader(execution).getOrNull(STRING)).isEqualTo(value);
    assertThat(CamundaBpmData.reader(execution).getOrNull(stringVariable("xxx"))).isNull();
  }

  @Test
  public void shouldDelegateGetLocalOrNull() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).setLocal(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocalOrNull(STRING)).isEqualTo(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocalOrNull(stringVariable("xxx"))).isNull();
  }

  @Test
  public void shouldDelegateGetOrDefault() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).set(value);
    assertThat(CamundaBpmData.reader(execution).getOrDefault(STRING, "default")).isEqualTo(value);
    assertThat(CamundaBpmData.reader(execution).getOrDefault(stringVariable("xxx"), "default")).isEqualTo("default");

  }

  @Test
  public void shouldDelegateGetLocalOrDefault() {
    DelegateExecution execution = new DelegateExecutionFake();
    STRING.on(execution).setLocal(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocalOrDefault(STRING, "localDefault")).isEqualTo(localValue);
    assertThat(CamundaBpmData.reader(execution).getLocalOrDefault(stringVariable("xxx"), "localDefault")).isEqualTo("localDefault");
  }

}
