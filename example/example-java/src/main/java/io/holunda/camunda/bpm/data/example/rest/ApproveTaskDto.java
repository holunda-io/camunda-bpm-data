package io.holunda.camunda.bpm.data.example.rest;

import io.holunda.camunda.bpm.data.example.domain.Order;

import java.math.BigDecimal;

/**
 * Simple DTO carrying the order and the total.
 */
public class ApproveTaskDto {

    /**
     * Order to carry.
     */
    private Order order;

    /**
     * Order total.
     */
    private BigDecimal orderTotal;

    /**
     * Empty constructor.
     */
    public ApproveTaskDto() { }

    /**
     * Constructs the DTO with order.
     *
     * @param order order to store.
     */
    public ApproveTaskDto(Order order, BigDecimal orderTotal) {
        this.order = order;
        this.orderTotal = orderTotal;
    }

    /**
     * Sets order.
     *
     * @param order order to set.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Sets order total.
     * @param orderTotal order total.
     */
    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    /**
     * Get order.
     *
     * @return order to get.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Retrieves the total.
     * @return order total.
     */
    public BigDecimal getOrderTotal() {
        return orderTotal;
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

        if (order != null ? !order.equals(that.order) : that.order != null) {
            return false;
        }
        return orderTotal != null ? orderTotal.equals(that.orderTotal) : that.orderTotal == null;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (orderTotal != null ? orderTotal.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApproveTaskDto{" +
            "order=" + order +
            ", orderTotal=" + orderTotal +
            '}';
    }
}

