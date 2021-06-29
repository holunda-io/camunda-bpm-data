package io.holunda.camunda.bpm.data.example.noengine.domain

import java.math.BigDecimal

/**
 * Order position business entity.
 */
data class OrderPosition(
  /**
   * Title.
   */
  val title: String,
  /**
   * Net cost per unit.
   */
  val netCost: BigDecimal,
  /**
   * Amount (number of units).
   */
  val amount: Long
)
