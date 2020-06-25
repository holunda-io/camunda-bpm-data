package io.holunda.camunda.bpm.data.reader;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import java.util.UUID;
import org.camunda.bpm.engine.TaskService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserTaskVariableReaderTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");
  private final TaskService taskService = Mockito.mock(TaskService.class);
  private final String taskId = UUID.randomUUID().toString();
  private final String value = "value";
  private final String localValue = "localValue";

  @Before
  public void mockExecution() {
    when(taskService.getVariable(this.taskId, STRING.getName())).thenReturn(value);
    when(taskService.getVariableLocal(this.taskId, STRING.getName())).thenReturn(localValue);
  }

  @After
  public void after() {
    Mockito.reset(taskService);
  }

  @Test
  public void shouldDelegateGet() {
    assertThat(CamundaBpmData.reader(taskService, taskId).get(STRING)).isEqualTo(value);
  }

  @Test
  public void shouldDelegateGetOptional() {
    assertThat(CamundaBpmData.reader(taskService, taskId).getOptional(STRING)).hasValue(value);
    assertThat(CamundaBpmData.reader(taskService, taskId).getOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocalOptional() {
    assertThat(CamundaBpmData.reader(taskService, taskId).getLocalOptional(STRING)).hasValue(localValue);
    assertThat(CamundaBpmData.reader(taskService, taskId).getLocalOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocal() {
    assertThat(CamundaBpmData.reader(taskService, taskId).getLocal(STRING)).isEqualTo(localValue);
  }
}
