package io.holunda.camunda.bpm.data.factory;

import org.junit.Test;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.listVariable;
import static org.assertj.core.api.Assertions.assertThat;

public class VariableFactoryTest {

  @Test
  public void testFactoryVariableName() {
    VariableFactory<List<String>> factoryForStrings = listVariable("listOfStrings", String.class);
    assertThat(factoryForStrings.getName()).isEqualTo("listOfStrings");
  }
}
