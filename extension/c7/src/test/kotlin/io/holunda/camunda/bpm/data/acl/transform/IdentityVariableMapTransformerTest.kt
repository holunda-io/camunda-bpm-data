package io.holunda.camunda.bpm.data.acl.transform

import io.holunda.camunda.bpm.data.CamundaBpmData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IdentityVariableMapTransformerTest {

  @Test
  fun `should pass the input to output`() {

    val vars = CamundaBpmData.builder().build()

    assertThat(IdentityVariableMapTransformer.transform(vars))
      .isEqualTo(vars) // equals comparison
      .isSameAs(vars) // == comparison
  }
}
