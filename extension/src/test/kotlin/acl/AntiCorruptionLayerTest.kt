package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.CamundaBpmData.customVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.acl.transform.IdentityVariableMapTransformer
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import io.holunda.camunda.bpm.data.guard.condition.matches
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.ProcessEngineConfiguration
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.impl.context.Context
import org.camunda.bpm.engine.test.mock.MockExpressionManager
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.value.ObjectValue
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake
import org.camunda.bpm.extension.mockito.delegate.DelegateTaskFake
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class AntiCorruptionLayerTest {

    val FOO = stringVariable("foo")
    val BAZ = stringVariable("baz")

    val TRANSIENT = customVariable("__transient", VariableMap::class.java)

    val MY_ACL = CamundaBpmDataACL.guardTransformingLocalReplace(
        TRANSIENT.name,
        VariablesGuard(listOf(exists(FOO), BAZ.matches { it.length > 2 })),
        IdentityVariableMapTransformer
    )

    @get: Rule
    val expectedException: ExpectedException = ExpectedException.none()


    @Test
    fun `should wrap variables directly`() {

        val vars = CamundaBpmData.builder().set(FOO, "foo1").set(BAZ, "baz2").build()

        val wrapped = AntiCorruptionLayer.wrapAsTypedTransientVariable(TRANSIENT.name, vars)
        assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
        assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue()
        assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(vars)
    }

    @Test
    fun `should wrap variables using ACL`() {

        val vars = CamundaBpmData.builder().set(FOO, "foo1").set(BAZ, "baz2").build()

        val wrapped = MY_ACL.wrap(vars)
        assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
        assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue()
        assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(vars)
    }

    @Test
    fun `should check and wrap variables`() {
        val vars = CamundaBpmData.builder().set(FOO, "foo1").set(BAZ, "baz2").build()
        val wrapped = MY_ACL.checkAndWrap(vars)

        assertThat(wrapped).containsOnlyKeys(TRANSIENT.name)
        assertThat(wrapped.getValueTyped<ObjectValue>(TRANSIENT.name).isTransient).isTrue()
        assertThat(TRANSIENT.from(wrapped).get()).isEqualTo(vars)
    }

    @Test
    fun `should fail checking and wrapping variables`() {
        expectedException.expectMessage("ACL Guard Error\n" +
            "\tExpecting variable 'baz' to match the condition, but its value 'ba' has not.\n")

        val vars = CamundaBpmData.builder().set(FOO, "foo1").set(BAZ, "ba").build()
        MY_ACL.checkAndWrap(vars)
    }

    @Test
    fun `should act as execution listener`() {

        setupEngineConfiguration()

        val listener = MY_ACL.getExecutionListener()
        val wrappedVars = MY_ACL.checkAndWrap(
            CamundaBpmData
                .builder()
                .set(FOO, "foo1")
                .set(BAZ, "baz2")
                .build()
        )

        val fake = DelegateExecutionFake().withVariables(wrappedVars)
        listener.notify(fake)

        assertThat(fake.hasVariableLocal(FOO.name)).isTrue()
        assertThat(fake.hasVariableLocal(BAZ.name)).isTrue()

    }

    @Test
    fun `should act as task listener`() {

        setupEngineConfiguration()

        val listener = MY_ACL.getTaskListener()
        val wrappedVars = MY_ACL.checkAndWrap(
            CamundaBpmData
                .builder()
                .set(FOO, "foo1")
                .set(BAZ, "baz2")
                .build()
        )

        val fake = DelegateTaskFake().withVariables(wrappedVars)
        listener.notify(fake)

        assertThat(fake.hasVariableLocal(FOO.name)).isTrue()
        assertThat(fake.hasVariableLocal(BAZ.name)).isTrue()

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