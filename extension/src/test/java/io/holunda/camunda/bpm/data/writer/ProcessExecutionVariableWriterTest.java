package io.holunda.camunda.bpm.data.writer;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.UUID;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProcessExecutionVariableWriterTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private final RuntimeService runtimeService = Mockito.mock(RuntimeService.class);
  private final String executionId = UUID.randomUUID().toString();
  private final String value = "value";
  private final String localValue = "localValue";

  @Before
  public void setUp() {
    when(runtimeService.getVariable(this.executionId, STRING.getName())).thenReturn(value);
    when(runtimeService.getVariableLocal(this.executionId, STRING.getName())).thenReturn(localValue);
  }

  @After
  public void after() {
    Mockito.reset(runtimeService);
  }

  @Test
  public void testSet() {
    CamundaBpmData.writer(runtimeService, executionId)
      .set(STRING, "value");
    verify(runtimeService).setVariable(this.executionId, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testSetLocal() {
    CamundaBpmData.writer(runtimeService, executionId)
      .setLocal(STRING, "value");
    verify(runtimeService).setVariableLocal(this.executionId, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testRemove() {
    CamundaBpmData.writer(runtimeService, executionId)
      .remove(STRING);
    verify(runtimeService).removeVariable(this.executionId, STRING.getName());
  }

  @Test
  public void testRemoveLocal() {
    CamundaBpmData.writer(runtimeService, executionId)
      .removeLocal(STRING);
    verify(runtimeService).removeVariableLocal(this.executionId, STRING.getName());
  }

}
