package io.holunda.camunda.bpm.data.acl

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.acl.apply.GlobalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.apply.LocalScopeReplaceStrategy
import io.holunda.camunda.bpm.data.acl.transform.IdentityVariableMapTransformer
import io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists
import io.holunda.camunda.bpm.data.guard.VariablesGuard
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.variable.VariableMap
import org.junit.Test

class CamundaBpmDataACLTest {

  val TRANSIENT_VAR = CamundaBpmData.customVariable("__transient__", VariableMap::class.java)
  val FOO = CamundaBpmData.stringVariable("foo")

  @Test
  fun `should create guardTransformingLocalReplace`() {
    val acl = CamundaBpmDataACL.guardTransformingLocalReplace(
      TRANSIENT_VAR.name,
      VariablesGuard(exists(FOO)),
      IdentityVariableMapTransformer
    )
    assertThat(acl.precondition).isEqualTo(VariablesGuard(exists(FOO)))
    assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
    assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
    assertThat(acl.valueApplicationStrategy).isEqualTo(LocalScopeReplaceStrategy)
  }

  @Test
  fun `should create guardTransformingGlobalReplace`() {
    val acl = CamundaBpmDataACL.guardTransformingGlobalReplace(
      TRANSIENT_VAR.name,
      VariablesGuard(exists(FOO)),
      IdentityVariableMapTransformer
    )
    assertThat(acl.precondition).isEqualTo(VariablesGuard(exists(FOO)))
    assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
    assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
    assertThat(acl.valueApplicationStrategy).isEqualTo(GlobalScopeReplaceStrategy)
  }

  @Test
  fun `should create guardTransformingReplace global`() {
    val acl = CamundaBpmDataACL.guardTransformingReplace(
      TRANSIENT_VAR.name,
      false,
      VariablesGuard(exists(FOO)),
      IdentityVariableMapTransformer
    )
    assertThat(acl.precondition).isEqualTo(VariablesGuard(exists(FOO)))
    assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
    assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
    assertThat(acl.valueApplicationStrategy).isEqualTo(GlobalScopeReplaceStrategy)
  }

  @Test
  fun `should create guardTransformingReplace local`() {
    val acl = CamundaBpmDataACL.guardTransformingReplace(
      TRANSIENT_VAR.name,
      true,
      VariablesGuard(exists(FOO)),
      IdentityVariableMapTransformer
    )
    assertThat(acl.precondition).isEqualTo(VariablesGuard(exists(FOO)))
    assertThat(acl.variableMapTransformer).isEqualTo(IdentityVariableMapTransformer)
    assertThat(acl.factory).isEqualTo(TRANSIENT_VAR)
    assertThat(acl.valueApplicationStrategy).isEqualTo(LocalScopeReplaceStrategy)
  }

}
