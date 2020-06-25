package io.holunda.camunda.bpm.data.example.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Order business entity.
 */
public class Order {
  /**
   * Id of the order.
   */
  private String orderId;
  /**
   * Order create date.
   */
  private Date created;
  /**
   * List of order positions.
   */
  private List<OrderPosition> positions;

  /**
   * Constructor.
   */
  public Order() {
    this.positions = new ArrayList<>();
  }

  /**
   * Constructor to pass all member attributes.
   * @param orderId order id
   * @param created creation date
   * @param positions list of positions.
   */
  public Order(String orderId, Date created, List<OrderPosition> positions) {
    this.orderId = orderId;
    this.created = created;
    this.positions = positions;
  }

  /**
   * Sets the order id.
   * @param orderId order id to set.
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * Sets the created date.
   * @param created created date to set.
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * Sets the order positions.
   * @param positions list of positions to set.
   */
  public void setPositions(List<OrderPosition> positions) {
    this.positions = positions;
  }

  /**
   * Retrieves the order id.
   * @return order id.
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * Retrieves the created date.
   * @return date of create.
   */
  public Date getCreated() {
    return created;
  }

  /**
   * Retrieves the list of positions.
   * @return list of positions.
   */
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

  @Override
  public String toString() {
    return "Order{" +
        "orderId='" + orderId + '\'' +
        ", created=" + created +
        ", positions=" + positions +
        '}';
  }
}
