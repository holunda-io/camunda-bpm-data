package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.Test;

import static io.holunda.camunda.bpm.data.CamundaBpmData.builder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.variable.Variables.createVariables;

public class VariableMapBuilderTest {

    private static final VariableFactory<String> STRING = CamundaBpmData.stringVariable("myString");

    @Test
    public void testSet() {
        VariableMap newVariables = builder()
            .set(STRING, "value")
            .build();
        assertThat(newVariables.get(STRING.getName())).isEqualTo("value");
    }

    @Test
    public void testRemove() {
        VariableMap variables = createVariables();
        STRING.on(variables).set("value");
        VariableMap newVariables = builder(variables)
            .remove(STRING)
            .build();
        assertThat(newVariables).isEmpty();
    }

    @Test
    public void testUpdate() {
        VariableMap variables = createVariables();
        STRING.on(variables).set("value");
        VariableMap newVariables = builder(variables)
            .update(STRING, String::toUpperCase)
            .build();
        assertThat(newVariables.get(STRING.getName())).isEqualTo("VALUE");
    }

}
