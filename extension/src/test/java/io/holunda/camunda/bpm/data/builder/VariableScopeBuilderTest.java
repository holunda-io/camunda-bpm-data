package io.holunda.camunda.bpm.data.builder;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.variable.Variables.createVariables;
import static org.camunda.bpm.engine.variable.Variables.stringValue;

public class VariableMapBuilderTest {

    private static final VariableFactory<String> STRING = CamundaBpmData.stringVariable("myString");
    private static final VariableFactory<String> STRING_2 = CamundaBpmData.stringVariable("myString2");
    private static final VariableFactory<String> STRING_3 = CamundaBpmData.stringVariable("myString3");

    @Test
    public void testSet() {
        VariableMap newVariables = CamundaBpmData
            .variableMapBuilder()
            .set(STRING, "value")
            .build();
        assertThat(newVariables.get(STRING.getName())).isEqualTo("value");
    }

    @Test
    public void testRemove() {
        VariableMap variables = createVariables();
        STRING.on(variables).set("value");
        VariableMap newVariables = CamundaBpmData
            .variableMapBuilder(variables)
            .remove(STRING)
            .build();
        assertThat(newVariables).isEmpty();
    }

    @Test
    public void testUpdate() {
        VariableMap variables = createVariables();
        STRING.on(variables).set("value");
        VariableMap newVariables = CamundaBpmData
            .variableMapBuilder(variables)
            .remove(STRING)
            .build();
        assertThat(newVariables).isEmpty();
    }


    @Test
    public void testNoSideEffects() {
        // initial map
        VariableMap variables = createVariables()
            .putValueTyped("myString", stringValue("value"))
            .putValueTyped("myString3", stringValue("value3"));


        VariableMap newVariables = CamundaBpmData
            .variableMapBuilder(variables)
            .set(STRING_2, "anotherString")
            .update(STRING, String::toUpperCase)
            .remove(STRING_3)
            .build();

        assertThat(variables).containsOnlyKeys(STRING.getName(), STRING_3.getName());
        assertThat(variables.get(STRING.getName())).isEqualTo("value");

        assertThat(newVariables).containsOnlyKeys(STRING.getName(), STRING_2.getName());
        assertThat(newVariables.get(STRING.getName())).isEqualTo("VALUE");
        assertThat(newVariables.get(STRING_2.getName())).isEqualTo("anotherString");

    }
}
