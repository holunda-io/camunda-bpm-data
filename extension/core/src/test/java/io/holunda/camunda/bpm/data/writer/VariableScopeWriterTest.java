package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.community.mockito.delegate.DelegateExecutionFake;
import org.junit.Test;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.assertj.core.api.Assertions.assertThat;

public class VariableScopeWriterTest {

  private static final VariableFactory<String> STRING = stringVariable("myString");

  @Test
  public void testSet() {
    DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");

    CamundaBpmData
      .writer(execution)
      .set(STRING, "value")
      .variables();
    assertThat(execution.getVariable(STRING.getName())).isEqualTo("value");
  }

  @Test
  public void testSetLocal() {
    DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
    CamundaBpmData
      .writer(execution)
      .setLocal(STRING, "value");
    assertThat(execution.getVariableLocal(STRING.getName())).isEqualTo("value");
  }

  @Test
  public void testRemove() {
    DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
    STRING.on(execution).set("value");
    CamundaBpmData.writer(execution)
                  .remove(STRING);
    assertThat(execution.getVariableNames()).isEmpty();
  }

  @Test
  public void testRemoveLocal() {
    DelegateExecutionFake execution = DelegateExecutionFake.of().withId("4711");
    STRING.on(execution).setLocal("value");
    CamundaBpmData
      .writer(execution)
      .removeLocal(STRING);
    assertThat(execution.getVariableNames()).isEmpty();
  }

}
