package io.holunda.camunda.bpm.data

import org.mockito.kotlin.mock
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin.stringVariable
import io.holunda.camunda.bpm.data.reader.CaseServiceVariableReader
import io.holunda.camunda.bpm.data.reader.RuntimeServiceVariableReader
import io.holunda.camunda.bpm.data.reader.TaskServiceVariableReader
import io.holunda.camunda.bpm.data.writer.CaseServiceVariableWriter
import io.holunda.camunda.bpm.data.writer.RuntimeServiceVariableWriter
import io.holunda.camunda.bpm.data.writer.TaskServiceVariableWriter
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.CaseService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.camunda.bpm.engine.variable.Variables.stringValue
import org.junit.jupiter.api.Test
import java.util.*

class FluentApiTest {

  companion object {
    const val NAME = "myString"
    const val VALUE = "some random value"
    const val LOCAL_VALUE = "other local value"
    val MY_VAR = stringVariable(NAME)
  }

  @Test
  fun `should read and write from variable map and variable scope`() {
    val vars = createVariables().putValueTyped(NAME, stringValue(VALUE))
    val fake = DelegateExecutionFake("executionId").withVariables(vars)

    @Suppress("ReplaceGetOrSet")
    assertThat(vars.get(MY_VAR)).isEqualTo(VALUE)
    assertThat(vars[MY_VAR]).isEqualTo(VALUE)
    assertThat(vars.getOptional(MY_VAR)).isEqualTo(Optional.of(VALUE))

    @Suppress("ReplaceGetOrSet")
    assertThat(fake.get(MY_VAR)).isEqualTo(VALUE)
    assertThat(fake[MY_VAR]).isEqualTo(VALUE)
    assertThat(fake.getOptional(MY_VAR)).isEqualTo(Optional.of(VALUE))

    fake.set(MY_VAR, "new val")
    assertThat(fake[MY_VAR]).isEqualTo("new val")

    fake.update(MY_VAR, { v -> v.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }, false)
    assertThat(fake[MY_VAR]).isEqualTo("New val")

    fake.remove(MY_VAR)
    assertThat(fake.getOptional(MY_VAR)).isEqualTo(Optional.empty<String>())
  }

  @Test
  fun `should read local and write local from variable map and variable scope`() {
    val localVars = createVariables().putValueTyped(NAME, stringValue(LOCAL_VALUE))
    val fake = DelegateExecutionFake("executionId").withVariablesLocal(localVars)

    // global reads local
    @Suppress("ReplaceGetOrSet")
    assertThat(fake.get(MY_VAR)).isEqualTo(LOCAL_VALUE)
    assertThat(fake[MY_VAR]).isEqualTo(LOCAL_VALUE)
    assertThat(fake.getOptional(MY_VAR)).isEqualTo(Optional.of(LOCAL_VALUE))

    assertThat(fake.getLocal(MY_VAR)).isEqualTo(LOCAL_VALUE)
    assertThat(fake.getLocalOptional(MY_VAR)).isEqualTo(Optional.of(LOCAL_VALUE))

    fake.set(MY_VAR, "new val")
    assertThat(fake[MY_VAR]).isEqualTo("new val")

    fake.update(MY_VAR, { v -> v.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }, false)
    assertThat(fake[MY_VAR]).isEqualTo("New val")

    fake.setLocal(MY_VAR, "another new local val")
    assertThat(fake.getLocal(MY_VAR)).isEqualTo("another new local val")

    fake.updateLocal(MY_VAR, { v -> v.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }, false)
    assertThat(fake[MY_VAR]).isEqualTo("Another new local val")

    fake.removeLocal(MY_VAR)
    assertThat(fake.getLocalOptional(MY_VAR)).isEqualTo(Optional.empty<String>())

    localVars.set(MY_VAR, "new val")
    assertThat(localVars[MY_VAR]).isEqualTo("new val")

    localVars.update(MY_VAR, { v -> v.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }, false)
    assertThat(localVars[MY_VAR]).isEqualTo("New val")

    localVars.remove(MY_VAR)
    assertThat(localVars.getOptional(MY_VAR)).isEqualTo(Optional.empty<String>())
  }

  @Test
  fun writer_and_reader() {
    val case: CaseService = mock()
    val runtime: RuntimeService = mock()
    val task: TaskService = mock()

    assertThat(case.writer("case-id")).isEqualTo(
      CaseServiceVariableWriter(
        case,
        "case-id"
      )
    )
    assertThat(runtime.writer("id")).isEqualTo(
      RuntimeServiceVariableWriter(
        runtime,
        "id"
      )
    )
    assertThat(task.writer("task-id")).isEqualTo(
      TaskServiceVariableWriter(
        task,
        "task-id"
      )
    )

    assertThat(case.reader("case-id")).isEqualTo(
      CaseServiceVariableReader(
        case,
        "case-id"
      )
    )
    assertThat(runtime.reader("id")).isEqualTo(
      RuntimeServiceVariableReader(
        runtime,
        "id"
      )
    )
    assertThat(task.reader("task-id")).isEqualTo(
      TaskServiceVariableReader(
        task,
        "task-id"
      )
    )

  }
}
