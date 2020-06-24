package io.holunda.camunda.bpm.data.reader;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.UUID;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProcessExecutionVariableReaderTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private final RuntimeService runtimeService = Mockito.mock(RuntimeService.class);
  private final String executionId = UUID.randomUUID().toString();
  private String value = "value";
  private String valueLocal = "valueLocal";

  @Before
  public void mockExecution() {
    when(runtimeService.getVariable(this.executionId, STRING.getName())).thenReturn(value);
    when(runtimeService.getVariableLocal(this.executionId, STRING.getName())).thenReturn(valueLocal);
  }

  @After
  public void after() {
    Mockito.reset(runtimeService);
  }

  @Test
  public void shouldDelegateGet() {
    assertThat(CamundaBpmData.reader(runtimeService, executionId).get(STRING)).isEqualTo(value);
  }

  @Test
  public void shouldDelegateGetOptional() {
    assertThat(CamundaBpmData.reader(runtimeService, executionId).getOptional(STRING)).hasValue(value);
    assertThat(CamundaBpmData.reader(runtimeService, executionId).getOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocalOptional() {
    assertThat(CamundaBpmData.reader(runtimeService, executionId).getLocalOptional(STRING)).hasValue(valueLocal);
    assertThat(CamundaBpmData.reader(runtimeService, executionId).getLocalOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocal() {
    assertThat(CamundaBpmData.reader(runtimeService, executionId).getLocal(STRING)).isEqualTo(valueLocal);
  }
}
