package io.holunda.camunda.bpm.data.example.noengine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SimpleVariableAccessITest {

  @Autowired
  lateinit var component: VariableAwareComponent

  @Test
  fun `should be able to use parts of it without the engine`() {
    assertThat(component.readAndWriteSomeVariables()).isTrue
  }

}
