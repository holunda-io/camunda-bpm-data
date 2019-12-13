package io.holunda.camunda.bpm.data.example.domain;

import java.math.BigDecimal;

public class OrderPosition {
  private String title;
  private BigDecimal netCost;
  private Long amount;

  public OrderPosition() {

  }

  public OrderPosition(String title, BigDecimal netCost, Long amount) {
    this.title = title;
    this.netCost = netCost;
    this.amount = amount;
  }

  public String getTitle() {
    return title;
  }

  public BigDecimal getNetCost() {
    return netCost;
  }

  public Long getAmount() {
    return amount;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setNetCost(BigDecimal netCost) {
    this.netCost = netCost;
  }

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
}
