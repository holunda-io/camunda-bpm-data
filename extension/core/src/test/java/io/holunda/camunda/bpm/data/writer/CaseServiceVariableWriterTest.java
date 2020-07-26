package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CaseServiceVariableWriterTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private static final String VALUE = "value";
  private static final String LOCAL_VALUE = "localValue";
  private static final String CASE_EXECUTION_ID = UUID.randomUUID().toString();

  private final CaseService caseService = Mockito.mock(CaseService.class);

  @Before
  public void setUp() {
    when(caseService.getVariable(CASE_EXECUTION_ID, STRING.getName())).thenReturn(VALUE);
    when(caseService.getVariableLocal(CASE_EXECUTION_ID, STRING.getName())).thenReturn(LOCAL_VALUE);
  }

  @After
  public void after() {
    Mockito.reset(caseService);
  }

  @Test
  public void testSet() {
    CamundaBpmData.writer(caseService, CASE_EXECUTION_ID)
      .set(STRING, "value");
    verify(caseService).setVariable(CASE_EXECUTION_ID, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testSetLocal() {
    CamundaBpmData.writer(caseService, CASE_EXECUTION_ID)
      .setLocal(STRING, "value");
    verify(caseService).setVariableLocal(CASE_EXECUTION_ID, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testRemove() {
    CamundaBpmData.writer(caseService, CASE_EXECUTION_ID)
      .remove(STRING);
    verify(caseService).removeVariable(CASE_EXECUTION_ID, STRING.getName());
  }

  @Test
  public void testRemoveLocal() {
    CamundaBpmData.writer(caseService, CASE_EXECUTION_ID)
      .removeLocal(STRING);
    verify(caseService).removeVariableLocal(CASE_EXECUTION_ID, STRING.getName());
  }

}
