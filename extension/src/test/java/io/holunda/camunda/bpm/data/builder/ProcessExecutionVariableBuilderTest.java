package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProcessExecutionVariableBuilderTest {

    private static final VariableFactory<String> STRING = stringVariable("myString");
    private RuntimeService runtimeService = Mockito.mock(RuntimeService.class);
    private String executionId;
    private String value = "value";

    @Before
    public void mockExecution() {
        this.executionId = UUID.randomUUID().toString();
        when(runtimeService.getVariable(this.executionId, STRING.getName())).thenReturn(value);
        when(runtimeService.getVariableLocal(this.executionId, STRING.getName())).thenReturn(value);
    }

    @After
    public void after() {
        Mockito.reset(runtimeService);
    }

    @Test
    public void testSet() {
        CamundaBpmData
            .builder(runtimeService, executionId)
            .set(STRING, "value")
            .variablesLocal();
        verify(runtimeService).setVariable(this.executionId, STRING.getName(), Variables.stringValue("value"));
    }

    @Test
    public void testSetLocal() {
        CamundaBpmData
            .builder(runtimeService, executionId)
            .setLocal(STRING, "value")
            .variablesLocal();
        verify(runtimeService).setVariableLocal(this.executionId, STRING.getName(), Variables.stringValue("value"));
    }

    @Test
    public void testRemove() {
        CamundaBpmData
            .builder(runtimeService, executionId)
            .remove(STRING)
            .variables();
        verify(runtimeService).removeVariable(this.executionId, STRING.getName());
    }

    @Test
    public void testRemoveLocal() {
        CamundaBpmData
            .builder(runtimeService, executionId)
            .removeLocal(STRING)
            .variables();
        verify(runtimeService).removeVariableLocal(this.executionId, STRING.getName());
    }

    @Test
    public void testUpdate() {
        CamundaBpmData
            .builder(runtimeService, executionId)
            .update(STRING, String::toUpperCase);
        verify(runtimeService).getVariable(this.executionId, STRING.getName());
        verify(runtimeService).setVariable(this.executionId, STRING.getName(), Variables.stringValue("VALUE"));
    }

    @Test
    public void testUpdateLocal() {
        CamundaBpmData
            .builder(runtimeService, executionId)
            .updateLocal(STRING, String::toUpperCase);
        verify(runtimeService).getVariableLocal(this.executionId, STRING.getName());
        verify(runtimeService).setVariableLocal(this.executionId, STRING.getName(), Variables.stringValue("VALUE"));
    }

}
