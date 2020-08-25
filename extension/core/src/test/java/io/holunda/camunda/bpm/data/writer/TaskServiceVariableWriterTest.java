package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.camunda.bpm.engine.variable.Variables.stringValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceVariableWriterTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private static final String VALUE = "value";
  private static final String TASK_ID = UUID.randomUUID().toString();

  private final TaskService taskService = Mockito.mock(TaskService.class);

  @Before
  public void setUp() {
    when(taskService.getVariable(TASK_ID, STRING.getName())).thenReturn(VALUE);
    when(taskService.getVariableLocal(TASK_ID, STRING.getName())).thenReturn(VALUE);
  }

  @After
  public void after() {
    Mockito.reset(taskService);
  }

  @Test
  public void testSet() {
    CamundaBpmData
      .writer(taskService, TASK_ID)
      .set(STRING, "value");
    verify(taskService).setVariable(TASK_ID, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testSetLocal() {
    CamundaBpmData.writer(taskService, TASK_ID)
      .setLocal(STRING, "value");
    verify(taskService).setVariableLocal(TASK_ID, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testRemove() {
    CamundaBpmData
      .writer(taskService, TASK_ID)
      .remove(STRING);
    verify(taskService).removeVariable(TASK_ID, STRING.getName());
  }

  @Test
  public void testRemoveLocal() {
    CamundaBpmData.writer(taskService, TASK_ID)
      .removeLocal(STRING);
    verify(taskService).removeVariableLocal(TASK_ID, STRING.getName());
  }

  @Test
  public void testUpdate() {
    CamundaBpmData.writer(taskService, TASK_ID)
      .update(STRING, (old) -> "new value");
    verify(taskService).getVariable(TASK_ID, STRING.getName());
    verify(taskService).setVariable(TASK_ID, STRING.getName(), stringValue("new value"));
  }

  @Test
  public void testUpdateLocal() {
    CamundaBpmData.writer(taskService, TASK_ID)
      .updateLocal(STRING, (old) -> "new value");
    verify(taskService).getVariableLocal(TASK_ID, STRING.getName());
    verify(taskService).setVariableLocal(TASK_ID, STRING.getName(), stringValue("new value"));
  }

}
