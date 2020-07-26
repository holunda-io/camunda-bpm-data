package io.holunda.camunda.bpm.data.example.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository to load orders.
 */
@Component
public class OrderRepository {

  /**
   * Internal representation for storage.
   */
  private final Map<String, Order> orders = new HashMap<>();

  public OrderRepository() {
    final List<OrderPosition> positions = new ArrayList<>();
    positions.add(new OrderPosition("Pencil", BigDecimal.valueOf(1.50), 2L));
    positions.add(new OrderPosition("Pen", BigDecimal.valueOf(2.10), 2L));
    orders.put("1", new Order("1", Date.from(Instant.now()), positions));
  }


  /**
   * Loads order by id.
   *
   * @param orderId order id to load the order for.
   * @return order or null.
   */
  public Order loadOrder(String orderId) {
    return orders.get(orderId);
  }

}
