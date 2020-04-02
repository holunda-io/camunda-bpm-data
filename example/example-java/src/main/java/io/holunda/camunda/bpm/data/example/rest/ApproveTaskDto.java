package io.holunda.camunda.bpm.data.example.rest;

import io.holunda.camunda.bpm.data.example.domain.Order;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApproveTaskDto that = (ApproveTaskDto) o;
        return Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return order != null ? order.hashCode() : 0;
    }
}
