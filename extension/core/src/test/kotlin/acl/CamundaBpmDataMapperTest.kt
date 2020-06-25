package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.acl.apply.GlobalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.apply.GlobalScopeReplaceStrategyTest
import io.holunda.camunda.bpm.data.acl.apply.LocalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.transform.IdentityVariableMapTransformer
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.variable.VariableMap
import org.junit.Test

class CamundaBpmDataMapperTest {

    val TRANSIENT_VAR = CamundaBpmData.customVariable("__transient__", VariableMap::class.java)

    @Test
    fun `should create transformingLocalReplace`() {
        val acl = CamundaBpmDataMapper.transformingLocalReplace(
            TRANSIENT_VAR.name,
            IdentityVariableMapTransformer
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(LocalScopeReplaceStrategy)
    }

    @Test
    fun `should create transformingGlobalReplace`() {
        val acl = CamundaBpmDataMapper.transformingGlobalReplace(
            TRANSIENT_VAR.name,
            IdentityVariableMapTransformer
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(GlobalScopeReplaceStrategy)
    }

    @Test
    fun `should create transformingReplace local`() {
        val acl = CamundaBpmDataMapper.transformingReplace(
            TRANSIENT_VAR.name,
            true,
            IdentityVariableMapTransformer
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(LocalScopeReplaceStrategy)
    }

    @Test
    fun `should create transformingReplace global`() {
        val acl = CamundaBpmDataMapper.transformingReplace(
            TRANSIENT_VAR.name,
            false,
            IdentityVariableMapTransformer
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(GlobalScopeReplaceStrategy)
    }


    @Test
    fun `should create identityLocalReplace`() {
        val acl = CamundaBpmDataMapper.identityLocalReplace(
            TRANSIENT_VAR.name
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(LocalScopeReplaceStrategy)
    }

    @Test
    fun `should create identityGlobalReplace`() {
        val acl = CamundaBpmDataMapper.identityGlobalReplace(
            TRANSIENT_VAR.name
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(GlobalScopeReplaceStrategy)
    }

    @Test
    fun `should create identityGlobalReplace local`() {
        val acl = CamundaBpmDataMapper.identityReplace(
            TRANSIENT_VAR.name,
            true
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(LocalScopeReplaceStrategy)
    }

    @Test
    fun `should create identityGlobalReplace global`() {
        val acl = CamundaBpmDataMapper.identityReplace(
            TRANSIENT_VAR.name,
            false
        )
        assertThat(acl.precondition).isEqualTo(VariablesGuard.EMPTY)
        assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
        assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
        assertThat(acl.valueApplicationStrategy).isEqualTo(GlobalScopeReplaceStrategy)
    }

}