package org.example.supermarket.service;

import org.example.supermarket.dao.OrderDAO;
import org.example.supermarket.model.Order;
import org.example.supermarket.model.OrderDetail;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void placeOrder(Order order, List<OrderDetail> orderDetails) {
        orderDAO.placeOrder(order, orderDetails);
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }
}
