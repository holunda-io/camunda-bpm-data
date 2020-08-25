package io.holunda.camunda.bpm.data.example.domain;

import java.math.BigDecimal;

/**
 * Represents order position business entity.
 */
public class OrderPosition {
  /**
   * Position title.
   */
  private String title;
  /**
   * Position net cost per unit.
   */
  private BigDecimal netCost;
  /**
   * amount of units.
   */
  private Long amount;

  /**
   * Empty constructor.
   */
  public OrderPosition() {

  }

  /**
   * Constructor setting fields.
   *
   * @param title   title of position.
   * @param netCost net cost per unit.
   * @param amount  amount of units.
   */
  public OrderPosition(String title, BigDecimal netCost, Long amount) {
    this.title = title;
    this.netCost = netCost;
    this.amount = amount;
  }

  /**
   * Retrieves title.
   *
   * @return position title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Retrieves net cost per unit.
   *
   * @return net cost per unit.
   */
  public BigDecimal getNetCost() {
    return netCost;
  }

  /**
   * Retrieves amount of units.
   *
   * @return amount of units.
   */
  public Long getAmount() {
    return amount;
  }

  /**
   * Sets the title.
   *
   * @param title title to set.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Sets net cost per unit.
   *
   * @param netCost net cost to set.
   */
  public void setNetCost(BigDecimal netCost) {
    this.netCost = netCost;
  }

  /**
   * Sets amount of units.
   *
   * @param amount amount to set.
   */
  public void setAmount(Long amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    OrderPosition that = (OrderPosition) o;

    if (!title.equals(that.title)) {
      return false;
    }
    if (!netCost.equals(that.netCost)) {
      return false;
    }
    return amount.equals(that.amount);
  }

  @Override
  public int hashCode() {
    int result = title.hashCode();
    result = 31 * result + netCost.hashCode();
    result = 31 * result + amount.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "OrderPosition{" +
      "title='" + title + '\'' +
      ", netCost=" + netCost +
      ", amount=" + amount +
      '}';
  }
}
