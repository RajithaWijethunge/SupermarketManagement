package org.example.supermarket.service;

import org.example.supermarket.model.Order;
import org.example.supermarket.model.OrderDetail;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order, List<OrderDetail> orderDetails);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
}
