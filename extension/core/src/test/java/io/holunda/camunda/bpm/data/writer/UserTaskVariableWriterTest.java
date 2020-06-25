package io.holunda.camunda.bpm.data.writer;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.UUID;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserTaskVariableWriterTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private final TaskService taskService = Mockito.mock(TaskService.class);
  private final String taskId = UUID.randomUUID().toString();
  private String value = "value";

  @Before
  public void setUp() {
    when(taskService.getVariable(this.taskId, STRING.getName())).thenReturn(value);
    when(taskService.getVariableLocal(this.taskId, STRING.getName())).thenReturn(value);
  }

  @After
  public void after() {
    Mockito.reset(taskService);
  }

  @Test
  public void testSet() {
    CamundaBpmData
      .writer(taskService, taskId)
      .set(STRING, "value");
    verify(taskService).setVariable(this.taskId, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testSetLocal() {
    CamundaBpmData.writer(taskService, taskId)
      .setLocal(STRING, "value");
    verify(taskService).setVariableLocal(this.taskId, STRING.getName(), Variables.stringValue("value"));
  }

  @Test
  public void testRemove() {
    CamundaBpmData
      .writer(taskService, taskId)
      .remove(STRING);
    verify(taskService).removeVariable(this.taskId, STRING.getName());
  }

  @Test
  public void testRemoveLocal() {
    CamundaBpmData.writer(taskService, taskId)
      .removeLocal(STRING);
    verify(taskService).removeVariableLocal(this.taskId, STRING.getName());
  }

}
