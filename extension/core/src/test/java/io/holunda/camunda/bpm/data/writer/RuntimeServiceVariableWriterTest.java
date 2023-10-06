package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.camunda.bpm.engine.variable.Variables.stringValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RuntimeServiceVariableWriterTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private static final String VALUE = "value";
  private static final String EXECUTION_ID = UUID.randomUUID().toString();

  private static final String LOCAL_VALUE = "localValue";
  private final RuntimeService runtimeService = Mockito.mock(RuntimeService.class);

  @BeforeEach
  public void setUp() {
    when(runtimeService.getVariable(EXECUTION_ID, STRING.getName())).thenReturn(VALUE);
    when(runtimeService.getVariableLocal(EXECUTION_ID, STRING.getName())).thenReturn(LOCAL_VALUE);
  }

  @AfterEach
  public void after() {
    Mockito.reset(runtimeService);
  }

  @Test
  public void testSet() {
    CamundaBpmData.writer(runtimeService, EXECUTION_ID)
      .set(STRING, "value");
    verify(runtimeService).setVariable(EXECUTION_ID, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testSetLocal() {
    CamundaBpmData.writer(runtimeService, EXECUTION_ID)
      .setLocal(STRING, "value");
    verify(runtimeService).setVariableLocal(EXECUTION_ID, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testRemove() {
    CamundaBpmData.writer(runtimeService, EXECUTION_ID)
      .remove(STRING);
    verify(runtimeService).removeVariable(EXECUTION_ID, STRING.getName());
  }

  @Test
  public void testRemoveLocal() {
    CamundaBpmData.writer(runtimeService, EXECUTION_ID)
      .removeLocal(STRING);
    verify(runtimeService).removeVariableLocal(EXECUTION_ID, STRING.getName());
  }

  @Test
  public void testUpdate() {
    CamundaBpmData.writer(runtimeService, EXECUTION_ID)
      .update(STRING, (old) -> "new value");
    verify(runtimeService).getVariable(EXECUTION_ID, STRING.getName());
    verify(runtimeService).setVariable(EXECUTION_ID, STRING.getName(), stringValue("new value"));
  }

  @Test
  public void testUpdateLocal() {
    CamundaBpmData.writer(runtimeService, EXECUTION_ID)
      .updateLocal(STRING, (old) -> "new value");
    verify(runtimeService).getVariableLocal(EXECUTION_ID, STRING.getName());
    verify(runtimeService).setVariableLocal(EXECUTION_ID, STRING.getName(), stringValue("new value"));
  }

}
