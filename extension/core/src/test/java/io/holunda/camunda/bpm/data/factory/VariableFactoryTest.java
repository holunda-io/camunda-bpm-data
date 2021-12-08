package io.holunda.camunda.bpm.data.factory;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VariableFactoryTest {

  @Test
  public void testFactoryVariableName() {
    VariableFactory<List<String>> factoryForStrings = listVariable("listOfStrings", String.class);
    assertThat(factoryForStrings.getName()).isEqualTo("listOfStrings");
  }

  @Test
  public void testBasicEqualsHashCode() {
    VariableFactory<String> stringVar = stringVariable("string");
    VariableFactory<String> stringVar2 = stringVariable("string");
    VariableFactory<String> stringVar3 = stringVariable("string2");
    VariableFactory<Boolean> boolVar = booleanVariable("string");
    VariableFactory<UUID> uuidVar = uuidVariable("uuid");

    assertThat(stringVar.equals(stringVar2)).isTrue();
    assertThat(stringVar.equals(stringVar3)).isFalse();
    assertThat(stringVar.equals(boolVar)).isFalse();
    assertThat(stringVar.equals(uuidVar)).isFalse();


    assertThat(stringVar.hashCode()).isEqualTo(stringVar2.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(stringVar3.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(boolVar.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(uuidVar.hashCode());
  }

  @Test
  public void testListEqualsHashCode() {
    VariableFactory<List<String>> stringVar = listVariable("string", String.class);
    VariableFactory<List<String>> stringVar2 = listVariable("string", String.class);
    VariableFactory<List<String>> stringVar3 = listVariable("string2", String.class);
    VariableFactory<List<Boolean>> boolVar = listVariable("string", Boolean.class);

    assertThat(stringVar.equals(stringVar2)).isTrue();
    assertThat(stringVar.equals(stringVar3)).isFalse();
    assertThat(stringVar.equals(boolVar)).isFalse();

    assertThat(stringVar.hashCode()).isEqualTo(stringVar2.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(stringVar3.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(boolVar.hashCode());
  }

  @Test
  public void testSetEqualsHashCode() {
    VariableFactory<Set<String>> stringVar = setVariable("string", String.class);
    VariableFactory<Set<String>> stringVar2 = setVariable("string", String.class);
    VariableFactory<Set<String>> stringVar3 = setVariable("string2", String.class);
    VariableFactory<Set<Boolean>> boolVar = setVariable("string", Boolean.class);

    assertThat(stringVar.equals(stringVar2)).isTrue();
    assertThat(stringVar.equals(stringVar3)).isFalse();
    assertThat(stringVar.equals(boolVar)).isFalse();

    assertThat(stringVar.hashCode()).isEqualTo(stringVar2.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(stringVar3.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(boolVar.hashCode());
  }

  @Test
  public void testMapEqualsHashCode() {
    VariableFactory<Map<String, Boolean>> stringVar = mapVariable("string", String.class, Boolean.class);
    VariableFactory<Map<String, Boolean>> stringVar2 = mapVariable("string", String.class, Boolean.class);
    VariableFactory<Map<String, Boolean>> stringVar3 = mapVariable("string2", String.class, Boolean.class);
    VariableFactory<Map<String, Integer>> stringVar4 = mapVariable("string2", String.class, Integer.class);
    VariableFactory<Map<Boolean, Boolean>> boolVar = mapVariable("string", Boolean.class, Boolean.class);

    assertThat(stringVar.equals(stringVar2)).isTrue();
    assertThat(stringVar.equals(stringVar3)).isFalse();
    assertThat(stringVar.equals(stringVar4)).isFalse();
    assertThat(stringVar.equals(boolVar)).isFalse();

    assertThat(stringVar.hashCode()).isEqualTo(stringVar2.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(stringVar3.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(stringVar4.hashCode());
    assertThat(stringVar.hashCode()).isNotEqualTo(boolVar.hashCode());
  }

}
