package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcessExecutionVaraibleBuilderTest {

    private static final VariableFactory<String> STRING = CamundaBpmData.stringVariable("myString");
    private RuntimeService runtimeService = Mockito.mock(RuntimeService.class);
    private String executionId;

    @Before
    public void mockExecution() {
        this.executionId = UUID.randomUUID().toString();
    }

    @After
    public void after() {
        Mockito.reset(runtimeService);
    }

    @Test
    public void testSet() {
        DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
        VariableScope newVariables = CamundaBpmData
            .builder(execution)
            .set(STRING, "value")
            .build();
        assertThat(newVariables.getVariable(STRING.getName())).isEqualTo("value");
    }

    @Test
    public void testSetLocal() {
        DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
        VariableScope newVariables = CamundaBpmData
            .builder(execution)
            .setLocal(STRING, "value")
            .build();
        assertThat(newVariables.getVariableLocal(STRING.getName())).isEqualTo("value");
    }

    @Test
    public void testRemove() {
        DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
        STRING.on(execution).set("value");
        VariableScope newVariables = CamundaBpmData
            .builder(execution)
            .remove(STRING)
            .build();
        assertThat(newVariables.getVariableNames()).isEmpty();
    }

    @Test
    public void testRemoveLocal() {
        DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
        STRING.on(execution).setLocal("value");
        VariableScope newVariables = CamundaBpmData
            .builder(execution)
            .removeLocal(STRING)
            .build();
        assertThat(newVariables.getVariableNames()).isEmpty();
    }

    @Test
    public void testUpdate() {
        DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
        STRING.on(execution).set("value");
        VariableScope newVariables = CamundaBpmData
            .builder(execution)
            .update(STRING, String::toUpperCase)
            .build();
        assertThat(newVariables.getVariable(STRING.getName())).isEqualTo("VALUE");
    }

    @Test
    public void testUpdateLocal() {
        DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
        STRING.on(execution).setLocal("value");
        VariableScope newVariables = CamundaBpmData
            .builder(execution)
            .updateLocal(STRING, String::toUpperCase)
            .build();
        assertThat(newVariables.getVariable(STRING.getName())).isEqualTo("VALUE");
    }

}
