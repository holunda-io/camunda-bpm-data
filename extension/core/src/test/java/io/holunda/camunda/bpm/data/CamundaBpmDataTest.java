package io.holunda.camunda.bpm.data;


import io.holunda.camunda.bpm.data.builder.VariableMapBuilder;
import io.holunda.camunda.bpm.data.factory.BasicVariableFactory;
import io.holunda.camunda.bpm.data.factory.ListVariableFactory;
import io.holunda.camunda.bpm.data.factory.MapVariableFactory;
import io.holunda.camunda.bpm.data.factory.SetVariableFactory;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.bpm.data.reader.RuntimeServiceVariableReader;
import io.holunda.camunda.bpm.data.reader.TaskServiceVariableReader;
import io.holunda.camunda.bpm.data.reader.VariableMapReader;
import io.holunda.camunda.bpm.data.reader.VariableReader;
import io.holunda.camunda.bpm.data.reader.VariableScopeReader;
import io.holunda.camunda.bpm.data.writer.CaseServiceVariableWriter;
import io.holunda.camunda.bpm.data.writer.GlobalVariableWriter;
import io.holunda.camunda.bpm.data.writer.RuntimeServiceVariableWriter;
import io.holunda.camunda.bpm.data.writer.TaskServiceVariableWriter;
import io.holunda.camunda.bpm.data.writer.VariableMapWriter;
import io.holunda.camunda.bpm.data.writer.VariableScopeWriter;
import io.holunda.camunda.bpm.data.writer.VariableWriter;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.community.mockito.delegate.DelegateExecutionFake;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.builder;
import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.dateVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.doubleVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.intVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.listVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.longVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.mapVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.setVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.shortVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.uuidVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.variable.Variables.createVariables;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CamundaBpmDataTest {

  private final String VAR_NAME = "foo";
  private final RuntimeService runtimeService = mock(RuntimeService.class);
  private final TaskService taskService = mock(TaskService.class);
  private final CaseService caseService = mock(CaseService.class);


  @Before
  public void setupStubs() {
    when(runtimeService.getVariablesLocalTyped(any())).thenReturn(createVariables());
    when(runtimeService.getVariablesTyped(any())).thenReturn(createVariables());

    when(taskService.getVariablesLocalTyped(any())).thenReturn(createVariables());
    when(taskService.getVariablesTyped(any())).thenReturn(createVariables());

    when(caseService.getVariablesLocalTyped(any())).thenReturn(createVariables());
    when(caseService.getVariablesTyped(any())).thenReturn(createVariables());

  }

  @Test
  public void shouldCreateStringVariableFactory() {

    VariableFactory<?> factory = stringVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(String.class);
  }

  @Test
  public void shouldCreateDateVariableFactory() {

    VariableFactory<?> factory = dateVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(Date.class);
  }

  @Test
  public void shouldCreateIntVariableFactory() {

    VariableFactory<?> factory = intVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(Integer.class);
  }


  @Test
  public void shouldCreateLongVariableFactory() {

    VariableFactory<?> factory = longVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(Long.class);
  }

  @Test
  public void shouldCreateShortVariableFactory() {

    VariableFactory<?> factory = shortVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(Short.class);
  }

  @Test
  public void shouldCreateDoubleVariableFactory() {

    VariableFactory<?> factory = doubleVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(Double.class);
  }

  @Test
  public void shouldCreateBooleanVariableFactory() {

    VariableFactory<?> factory = booleanVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(Boolean.class);
  }

  @Test
  public void shouldCreateUuidVariableFactory() {

    VariableFactory<?> factory = uuidVariable(VAR_NAME);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(UUID.class);
  }

  @Test
  public void shouldCreateCustomVariableFactory() {

    VariableFactory<?> factory = customVariable(VAR_NAME, MyCustomType.class);
    assertThat(factory).isInstanceOf(BasicVariableFactory.class);

    BasicVariableFactory<?> basicFactory = (BasicVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getVariableClass()).isEqualTo(MyCustomType.class);
  }

  @Test
  public void shouldCreateListVariableFactory() {

    VariableFactory<?> factory = listVariable(VAR_NAME, MyCustomType.class);
    assertThat(factory).isInstanceOf(ListVariableFactory.class);

    ListVariableFactory<?> basicFactory = (ListVariableFactory<?>) factory;
    assertThat(basicFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(basicFactory.getMemberClass()).isEqualTo(MyCustomType.class);
  }

  @Test
  public void shouldCreateSetVariableFactory() {

    VariableFactory<?> factory = setVariable(VAR_NAME, MyCustomType.class);
    assertThat(factory).isInstanceOf(SetVariableFactory.class);

    SetVariableFactory<?> setFactory = (SetVariableFactory<?>) factory;
    assertThat(setFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(setFactory.getMemberClass()).isEqualTo(MyCustomType.class);
  }

  @Test
  public void shouldCreateMapVariableFactory() {

    VariableFactory<?> factory = mapVariable(VAR_NAME, String.class, MyCustomType.class);
    assertThat(factory).isInstanceOf(MapVariableFactory.class);

    MapVariableFactory<?, ?> mapFactory = (MapVariableFactory<?, ?>) factory;
    assertThat(mapFactory.getName()).isEqualTo(VAR_NAME);
    assertThat(mapFactory.getKeyClass()).isEqualTo(String.class);
    assertThat(mapFactory.getValueClass()).isEqualTo(MyCustomType.class);
  }


  @Test
  public void shouldCreateBuilder() {

    VariableMapBuilder builder = builder();
    VariableMapBuilder builder2 = builder();
    assertThat(builder).isEqualTo(builder2);
    assertThat(builder).isNotSameAs(builder2);
  }

  @Test
  public void shouldCreateWriterRuntimeService() {

    VariableWriter<?> writer = writer(runtimeService, "4711");
    VariableWriter<?> writer2 = writer(runtimeService, "4711");
    assertThat(writer).isNotSameAs(writer2);
    assertThat(writer).isEqualTo(writer2);
    assertThat(writer).isInstanceOf(RuntimeServiceVariableWriter.class);
    assertThat(writer.variablesLocal()).isNotNull();
    assertThat(writer.variables()).isNotNull();
  }

  @Test
  public void shouldCreateWriterTaskService() {

    VariableWriter<?> writer = writer(taskService, "4711");
    VariableWriter<?> writer2 = writer(taskService, "4711");
    assertThat(writer).isNotSameAs(writer2);
    assertThat(writer).isEqualTo(writer2);
    assertThat(writer).isInstanceOf(TaskServiceVariableWriter.class);
    assertThat(writer.variablesLocal()).isNotNull();
    assertThat(writer.variables()).isNotNull();
  }


  @Test
  public void shouldCreateWriterCaseService() {

    VariableWriter<?> writer = writer(caseService, "4711");
    VariableWriter<?> writer2 = writer(caseService, "4711");
    assertThat(writer).isNotSameAs(writer2);
    assertThat(writer).isEqualTo(writer2);
    assertThat(writer).isInstanceOf(CaseServiceVariableWriter.class);
    assertThat(writer.variablesLocal()).isNotNull();
    assertThat(writer.variables()).isNotNull();
  }

  @Test
  public void shouldCreateWriterVariableScope() {

    DelegateExecutionFake execution = new DelegateExecutionFake();

    VariableWriter<?> writer = writer(execution);
    VariableWriter<?> writer2 = writer(execution);
    assertThat(writer).isNotSameAs(writer2).isEqualTo(writer2);
    assertThat(writer).isInstanceOf(VariableScopeWriter.class);
    assertThat(writer.variablesLocal()).isNotNull();
    assertThat(writer.variables()).isNotNull();
  }

  @Test
  public void shouldCreateWriterVariableMap() {
    GlobalVariableWriter<?> writer = writer(createVariables());
    GlobalVariableWriter<?> writer2 = writer(createVariables());
    assertThat(writer).isNotSameAs(writer2).isEqualTo(writer2);
    assertThat(writer).isInstanceOf(VariableMapWriter.class);
    assertThat(writer.variables()).isNotNull();
  }

  @Test
  public void shouldCreateReaderRuntimeService() {

    VariableReader reader = reader(runtimeService, "4711");
    VariableReader reader2 = reader(runtimeService, "4711");
    assertThat(reader).isNotSameAs(reader2);
    assertThat(reader).isEqualTo(reader2);
    assertThat(reader).isInstanceOf(RuntimeServiceVariableReader.class);
  }

  @Test
  public void shouldCreateReaderTaskService() {

    VariableReader reader = reader(taskService, "4711");
    VariableReader reader2 = reader(taskService, "4711");
    assertThat(reader).isNotSameAs(reader2);
    assertThat(reader).isEqualTo(reader2);
    assertThat(reader).isInstanceOf(TaskServiceVariableReader.class);

  }

  @Test
  public void shouldCreateReaderVariableScope() {

    DelegateExecutionFake execution = new DelegateExecutionFake();
    VariableReader reader = reader(execution);
    VariableReader reader2 = reader(execution);
    assertThat(reader).isNotSameAs(reader2);
    assertThat(reader).isEqualTo(reader2);
    assertThat(reader).isInstanceOf(VariableScopeReader.class);
  }

  @Test
  public void shouldCreateReaderVariableMap() {
    VariableReader reader = reader(createVariables());
    VariableReader reader2 = reader(createVariables());
    assertThat(reader).isNotSameAs(reader2);
    assertThat(reader).isEqualTo(reader2);
    assertThat(reader).isInstanceOf(VariableMapReader.class);
  }

  @Test
  public void shouldCreateReaderProcessInstanceWithVariables() {
    var processInstance = mock(ProcessInstanceWithVariables.class);
    when(processInstance.getVariables()).thenReturn(createVariables());
    VariableReader readerFromInstance = reader(processInstance);
    VariableReader readerFromMap = reader(createVariables());

    assertThat(readerFromInstance).isNotSameAs(readerFromMap);
    assertThat(readerFromInstance).isEqualTo(readerFromMap);
    assertThat(readerFromInstance).isInstanceOf(VariableMapReader.class);
  }


  static class MyCustomType {

  }
}
