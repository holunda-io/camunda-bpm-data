package io.holunda.camunda.bpm.data.example.noengine;

import java.time.Instant;
import java.util.List;

public class Order {

  private final String orderId;
  private final Instant createdOn;
  private final List<OrderPosition> positions;

  public Order(String orderId, Instant createdOn, List<OrderPosition> positions) {
    this.orderId = orderId;
    this.createdOn = createdOn;
    this.positions = positions;
  }

  public String getOrderId() {
    return orderId;
  }

  public Instant getCreatedOn() {
    return createdOn;
  }

  public List<OrderPosition> getPositions() {
    return positions;
  }

  @Override
  public String toString() {
    return "Order{" +
      "orderId='" + orderId + '\'' +
      ", createdOn=" + createdOn +
      ", positions=" + positions +
      '}';
  }
}
