package io.holunda.camunda.bpm.data.jackson;

import org.assertj.core.util.Lists;
import org.camunda.spin.json.SpinJsonNode;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.spin.Spin.JSON;

public class SpinDeserializationTest {

  private static final MyComplexType value1 = new MyComplexType("value", 17);
  private static final MyComplexType value2 = new MyComplexType("value2", 11);

  @Test
  public void deserializeList() {

    String json = "[{\"foo\":\"value\",\"bar\":17},{\"foo\":\"value2\",\"bar\":11}]";
    SpinJsonNode spinNode = JSON(json);
    List<MyComplexType> deserialized = spinNode.mapTo("java.util.ArrayList<" + MyComplexType.class.getName() + ">");
    assertThat(deserialized).containsExactlyInAnyOrder(value1, value2);
  }

  @Test
  public void serializeList() {
    List<MyComplexType> list = Lists.list(value1, value2);
    String json = JSON(list).toString();
    assertThat(json).isEqualTo("[{\"foo\":\"value\",\"bar\":17},{\"foo\":\"value2\",\"bar\":11}]");
  }

}
