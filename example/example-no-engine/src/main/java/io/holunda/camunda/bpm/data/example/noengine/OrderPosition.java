package io.holunda.camunda.bpm.data.example.noengine;

import java.math.BigDecimal;

public class OrderPosition {

  private final String title;
  private final BigDecimal price;
  private final Long amount;

  public OrderPosition(String title, BigDecimal price, Long amount) {
    this.amount = amount;
    this.price = price;
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "OrderPosition{" +
      "title='" + title + '\'' +
      ", price=" + price +
      ", amount=" + amount +
      '}';
  }
}
