package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.bpm.data.CamundaBpmData.customVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.DelegateExecutionFake
import io.holunda.camunda.bpm.data.DelegateTaskFake
import io.holunda.camunda.bpm.data.acl.transform.IdentityVariableMapTransformer
import io.holunda.camunda.bpm.data.acl.transform.VariableMapTransformer
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.matches
import io.holunda.camunda.bpm.data.guard.integration.GuardViolationException
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.ProcessEngineConfiguration
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.impl.context.Context
import org.camunda.bpm.engine.test.mock.MockExpressionManager
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.value.ObjectValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AntiCorruptionLayerTest {

  val FOO = stringVariable("foo")
  val BAZ = stringVariable("baz")

  val TRANSIENT = customVariable("__transient", VariableMap::class.java)

  val MY_ACL = CamundaBpmDataACL.guardTransformingLocalReplace(
    TRANSIENT.name,
    VariablesGuard(listOf(exists(FOO), BAZ.matches { it.length > 2 })),
    IdentityVariableMapTransformer
  )

  val mapper = object : VariableMapTransformer {
    override fun transform(variableMap: VariableMap): VariableMap = builder()
      .set(FOO, FOO.from(variableMap).get())
      .set(BAZ, BAZ.from(variableMap).get().substring(1))
      .build()

  }

  val MY_ACL2 = CamundaBpmDataACL.guardTransformingLocalReplace(
    TRANSIENT.name,
    VariablesGuard(listOf(exists(FOO), BAZ.matches { it.length > 2 })), mapper
  )

  @Test
  fun `should wrap variables directly`() {

    val vars = builder().set(FOO, "foo1").set(BAZ, "baz2").build()

    val wrapped = AntiCorruptionLayer.wrapAsTypedTransientVariable(TRANSIENT.name, vars)
    assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
    assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue
    assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(vars)
  }

  @Test
  fun `should wrap variables using ACL`() {

    val vars = builder().set(FOO, "foo1").set(BAZ, "baz2").build()

    val wrapped = MY_ACL.wrap(vars)
    assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
    assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue
    assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(vars)
  }

  @Test
  fun `should check and wrap variables`() {
    val vars = builder().set(FOO, "foo1").set(BAZ, "baz2").build()
    val wrapped = MY_ACL.checkAndWrap(vars)

    assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
    assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue
    assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(vars)
  }

  @Test
  fun `should check transform and wrap variables`() {
    val vars = builder().set(FOO, "foo1").set(BAZ, "baz2").build()

    val wrapped = MY_ACL2.checkAndTransformAndWrap(vars)
    assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
    assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue

    assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(mapper.transform(vars))
  }


  @Test
  fun `should fail checking and wrapping variables`() {
    val vars = builder().set(FOO, "foo1").set(BAZ, "ba").build()
    assertThrows<GuardViolationException>("ACL Guard Error:\n\tExpecting variable 'baz' to match the condition, but its value 'ba' has not.") {
      MY_ACL.checkAndWrap(vars)
    }
  }

  @Test
  fun `should act as execution listener`() {

    setupEngineConfiguration()

    val listener = MY_ACL.getExecutionListener()
    val wrappedVars = MY_ACL.checkAndWrap(
      builder()
        .set(FOO, "foo1")
        .set(BAZ, "baz2")
        .build()
    )

    val fake = DelegateExecutionFake().withVariables(wrappedVars)
    listener.notify(fake)

    assertThat(fake.hasVariableLocal(FOO.name)).isTrue
    assertThat(fake.hasVariableLocal(BAZ.name)).isTrue

  }

  @Test
  fun `should act as task listener`() {

    setupEngineConfiguration()

    val listener = MY_ACL.getTaskListener()
    val wrappedVars = MY_ACL.checkAndWrap(
      builder()
        .set(FOO, "foo1")
        .set(BAZ, "baz2")
        .build()
    )

    val fake = DelegateTaskFake().withVariables(wrappedVars)
    listener.notify(fake)

    assertThat(fake.hasVariableLocal(FOO.name)).isTrue
    assertThat(fake.hasVariableLocal(BAZ.name)).isTrue

  }


  private fun setupEngineConfiguration() {
    val config = object : StandaloneInMemProcessEngineConfiguration() {
      init {
        history = ProcessEngineConfiguration.HISTORY_FULL
        databaseSchemaUpdate = ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE
        jobExecutorActivate = false
        expressionManager = MockExpressionManager()
        javaSerializationFormatEnabled = true
      }
    }

    Context.setProcessEngineConfiguration(config)
  }
}
