package io.holunda.camunda.bpm.data.example.rest;

import io.holunda.camunda.bpm.data.example.domain.Order;

public class ApproveTaskDto {

  private Order order;

  public ApproveTaskDto() {

  }

  public ApproveTaskDto(Order order) {this.order = order;}

  public void setOrder(Order order) {
    this.order = order;
  }

  public Order getOrder() {
    return order;
  }

  @Override
  public String toString() {
    return "ApproveTaskDto{" +
      "order=" + order +
      '}';
  }
}
