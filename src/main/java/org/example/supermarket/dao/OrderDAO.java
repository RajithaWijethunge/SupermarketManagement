package org.example.supermarket.dao;
import org.example.supermarket.model.Order;
import org.example.supermarket.model.OrderDetail;

import java.util.List;

public interface OrderDAO {
    void placeOrder(Order order, List<OrderDetail> orderDetails);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
}
