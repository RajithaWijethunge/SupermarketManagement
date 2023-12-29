package org.example.supermarket.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private LocalDateTime orderDate;
    private List<OrderDetail> orderDetails;

    public Order(int id, int customerId, LocalDateTime orderDate, List<OrderDetail> orderDetails) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
    }

    public Order(int customerId, LocalDateTime orderDate, List<OrderDetail> orderDetails) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}