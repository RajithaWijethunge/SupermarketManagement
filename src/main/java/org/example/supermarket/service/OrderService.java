package org.example.supermarket.service;

import org.example.supermarket.model.Order;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getAllOrders();
}
