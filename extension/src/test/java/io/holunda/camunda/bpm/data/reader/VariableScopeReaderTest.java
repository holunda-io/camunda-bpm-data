package io.holunda.camunda.bpm.data.reader;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Test;

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
}
