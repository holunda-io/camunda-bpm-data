package io.holunda.camunda.bpm.data.writer;

import static org.assertj.core.api.Assertions.assertThat;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Test;

public class VariableMapWriterTest {

  private static final VariableFactory<String> STRING = CamundaBpmData.stringVariable("myString");

  private final VariableMap variables = Variables.createVariables();

  @Test
  public void testSet() {
    CamundaBpmData.writer(variables)
      .set(STRING, "value");
    assertThat(variables.get(STRING.getName())).isEqualTo("value");
  }

  @Test
  public void testRemove() {
    STRING.on(variables).set("value");
    CamundaBpmData.writer(variables)
      .remove(STRING);
    assertThat(variables).isEmpty();
  }

}
