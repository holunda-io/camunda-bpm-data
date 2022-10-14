package io.holunda.camunda.bpm.data.example.kotlin.domain

import java.math.BigDecimal
import java.sql.Date
import java.time.Instant
import org.springframework.stereotype.Component

/** Repository. */
@Component
class OrderRepository {

  val orders =
    mapOf(
      "1" to
        Order(
          orderId = "1",
          created = Date.from(Instant.now()),
          positions =
            listOf(
              OrderPosition(title = "Pencil", netCost = BigDecimal.valueOf(1.50), amount = 2),
              OrderPosition(title = "Pen", netCost = BigDecimal.valueOf(2.10), amount = 2)
            )
        )
    )

  fun loadOrder(orderId: String): Order {
    return orders.getValue(orderId)
  }
}
