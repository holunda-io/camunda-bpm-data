package io.holunda.camunda.bpm.data.reader;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.listVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.mapVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.setVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.uuidVariable;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.camunda.bpm.engine.impl.util.CollectionUtil.asHashSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LockedExternalTaskReaderTest {

  private static final VariableFactory<String> STRING_VAR = stringVariable("myString");
  private static final VariableFactory<UUID> UUID_VAR = uuidVariable("myUuid");
  private static final VariableFactory<List<String>> LIST_VAR = listVariable("myList", String.class);
  private static final VariableFactory<Set<String>> SET_VAR = setVariable("mySet", String.class);
  private static final VariableFactory<Map<String, String>> MAP_VAR = mapVariable("myMap", String.class, String.class);

  private final String stringValue = "value";
  private final UUID uuidValue = UUID.randomUUID();
  private final List<String> listValue = asList("foo", "bar");
  private final Set<String> setValue = asHashSet("foo", "bar");
  private final Map<String, String> mapValue = Map.of("a", "b", "c", "d");

  LockedExternalTask externalTask = mock(LockedExternalTask.class);
  private VariableReader reader = CamundaBpmData.reader(externalTask);


  @BeforeEach
  public void setUp() {
    when(externalTask.getVariables()).thenReturn(
      Variables
        .putValue(STRING_VAR.getName(), stringValue)
        .putValue(LIST_VAR.getName(), listValue)
        .putValue(SET_VAR.getName(), setValue)
        .putValue(MAP_VAR.getName(), mapValue)
        .putValue(UUID_VAR.getName(), uuidValue)
    );
  }

  @Test
  public void shouldDelegateGet() {
    assertThat(reader.get(STRING_VAR)).isEqualTo(stringValue);
    assertThat(reader.get(LIST_VAR)).isEqualTo(listValue);
    assertThat(reader.get(SET_VAR)).isEqualTo(setValue);
    assertThat(reader.get(MAP_VAR)).isEqualTo(mapValue);
    assertThat(reader.get(UUID_VAR)).isEqualTo(uuidValue);
    assertThat(reader.get(UUID_VAR)).isInstanceOf(UUID.class);
  }

  @Test
  public void shouldDelegateGetOptional() {
    assertThat(reader.getOptional(STRING_VAR)).hasValue(stringValue);
    assertThat(reader.getOptional(LIST_VAR)).hasValue(listValue);
    assertThat(reader.getOptional(SET_VAR)).hasValue(setValue);
    assertThat(reader.getOptional(MAP_VAR)).hasValue(mapValue);
    assertThat(reader.getOptional(UUID_VAR)).hasValue(uuidValue);
    assertThat(reader.getOptional(stringVariable("xxx"))).isEmpty();
  }

  @Test
  public void shouldDelegateGetLocalOptional() {
    assertThatThrownBy(() -> reader.getLocalOptional(STRING_VAR))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }

  @Test
  public void shouldDelegateGetLocal() {
    assertThatThrownBy(() -> reader.getLocal(STRING_VAR))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }

  @Test
  public void shouldDelegateGetOrNull() {
    assertThat(reader.getOrNull(STRING_VAR)).isEqualTo(stringValue);
    assertThat(reader.getOrNull(LIST_VAR)).isEqualTo(listValue);
    assertThat(reader.getOrNull(SET_VAR)).isEqualTo(setValue);
    assertThat(reader.getOrNull(MAP_VAR)).isEqualTo(mapValue);
    assertThat(reader.getOrNull(UUID_VAR)).isEqualTo(uuidValue);
    assertThat(reader.getOrNull(stringVariable("xxx"))).isNull();
  }

  @Test
  public void shouldDelegateGetLocalOrNull() {
    assertThatThrownBy(() -> reader.getLocalOrNull(STRING_VAR))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }

  @Test
  public void shouldDelegateGetOrDefault() {
    assertThat(reader.getOrDefault(STRING_VAR, "default")).isEqualTo(stringValue);
    assertThat(reader.getOrDefault(LIST_VAR, asList("a", "b"))).isEqualTo(listValue);
    assertThat(reader.getOrDefault(SET_VAR, asHashSet("a", "b"))).isEqualTo(setValue);
    assertThat(reader.getOrDefault(MAP_VAR, Map.of("a", "b", "c", "d"))).isEqualTo(mapValue);

    assertThat(reader.getOrDefault(stringVariable("xxx"), "default")).isEqualTo("default");
    assertThat(reader.getOrDefault(listVariable("xxx", String.class), asList("a", "b"))).isEqualTo(asList("a", "b"));
    assertThat(reader.getOrDefault(setVariable("xxx", String.class), asHashSet("a", "b"))).isEqualTo(asHashSet("a", "b"));
    assertThat(reader.getOrDefault(mapVariable("xxx", String.class, String.class), Map.of("a", "b", "c", "d"))).isEqualTo(Map.of("a", "b", "c", "d"));
  }

  @Test
  public void shouldDelegateGetLocalOrDefault() {
    assertThatThrownBy(() -> reader.getLocalOrDefault(STRING_VAR, stringValue))
      .isInstanceOf(UnsupportedOperationException.class)
      .hasMessage("Can't get a local variable on an external task");
  }
}
