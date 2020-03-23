package io.holunda.camunda.bpm.data.builder;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTaskVariableBuilderTest {

    private static final VariableFactory<String> STRING = stringVariable("myString");
    private TaskService taskService = Mockito.mock(TaskService.class);
    private String taskId;
    private String value = "value";

    @Before
    public void mockExecution() {
        this.taskId = UUID.randomUUID().toString();
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
            .builder(taskService, taskId)
            .set(STRING, "value")
            .variablesLocal();
        verify(taskService).setVariable(this.taskId, STRING.getName(), Variables.stringValue("value"));
    }

    @Test
    public void testSetLocal() {
        CamundaBpmData
            .builder(taskService, taskId)
            .setLocal(STRING, "value")
            .variablesLocal();
        verify(taskService).setVariableLocal(this.taskId, STRING.getName(), Variables.stringValue("value"));
    }

    @Test
    public void testRemove() {
        CamundaBpmData
            .builder(taskService, taskId)
            .remove(STRING)
            .variables();
        verify(taskService).removeVariable(this.taskId, STRING.getName());
    }

    @Test
    public void testRemoveLocal() {
        CamundaBpmData
            .builder(taskService, taskId)
            .removeLocal(STRING)
            .variables();
        verify(taskService).removeVariableLocal(this.taskId, STRING.getName());
    }

    @Test
    public void testUpdate() {
        CamundaBpmData
            .builder(taskService, taskId)
            .update(STRING, String::toUpperCase);
        verify(taskService).getVariable(this.taskId, STRING.getName());
        verify(taskService).setVariable(this.taskId, STRING.getName(), Variables.stringValue("VALUE"));
    }

    @Test
    public void testUpdateLocal() {
        CamundaBpmData
            .builder(taskService, taskId)
            .updateLocal(STRING, String::toUpperCase);
        verify(taskService).getVariableLocal(this.taskId, STRING.getName());
        verify(taskService).setVariableLocal(this.taskId, STRING.getName(), Variables.stringValue("VALUE"));
    }

}
