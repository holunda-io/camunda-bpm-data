package io.holunda.camunda.bpm.data.example.kotlin.process

import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.example.kotlin.domain.OrderPosition
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_POSITION
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_TOTAL
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.extension.mockito.CamundaMockito.delegateExecutionFake
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * Class collecting delegate tests of the approval process.
 */
class OrderApprovalDelegateTests {

  /**
   * Start with empty total and add position total from one position stored locally.
   */
  @Test
  fun `should calculate order position for empty total`() {

    // GIVEN
    val execution = delegateExecutionFake()
      .withVariables(
        builder()
          .set(ORDER_TOTAL, BigDecimal.valueOf(0.0))
          .build()
      ).withVariablesLocal(
        builder()
          .set(ORDER_POSITION, OrderPosition("position 1", BigDecimal.valueOf(10.10), 1))
          .build()
      )

    // WHEN
    OrderApproval().calculateOrderPositions().execute(execution)

    // THEN
    val newTotal = reader(execution).get(ORDER_TOTAL)
    assertThat(newTotal).isEqualTo(BigDecimal.valueOf(10.10))
  }

  /**
   * Start with non-empty total and add several position totals from two positions stored locally.
   */
  @Test
  fun `should calculate order positions for existing total`() {

    // GIVEN
    val execution = delegateExecutionFake()
      .withVariables(
        builder()
          .set(ORDER_TOTAL, BigDecimal.valueOf(12.99))
          .build()
      ).withVariablesLocal(
        builder()
          .set(ORDER_POSITION, OrderPosition("position 1", BigDecimal.valueOf(10.10), 1))
          .build()
      )

    // WHEN
    // position 1
    OrderApproval().calculateOrderPositions().execute(execution)
    // position 2
    OrderApproval().calculateOrderPositions().execute(
      execution.withVariablesLocal(
        builder().set(ORDER_POSITION, OrderPosition("position 2", BigDecimal.valueOf(5.05), 2)).build()
      )
    )

    // THEN
    assertThat(ORDER_TOTAL.from(execution).get()).isEqualTo(BigDecimal.valueOf(33.19))
  }

}

