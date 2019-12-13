package io.holunda.camunda.bpm.data.example.domain;

import java.util.Date;
import java.util.List;

public class Order {
  private String orderId;
  private Date created;
  private List<OrderPosition> positions;

  public Order() {

  }

  public Order(String orderId, Date created, List<OrderPosition> positions) {
    this.orderId = orderId;
    this.created = created;
    this.positions = positions;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public void setPositions(List<OrderPosition> positions) {
    this.positions = positions;
  }

  public String getOrderId() {
    return orderId;
  }

  public Date getCreated() {
    return created;
  }

  public List<OrderPosition> getPositions() {
    return positions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Order order = (Order) o;

    if (!orderId.equals(order.orderId)) {
      return false;
    }
    if (!created.equals(order.created)) {
      return false;
    }
    return positions.equals(order.positions);
  }

  @Override
  public int hashCode() {
    int result = orderId.hashCode();
    result = 31 * result + created.hashCode();
    result = 31 * result + positions.hashCode();
    return result;
  }
}
