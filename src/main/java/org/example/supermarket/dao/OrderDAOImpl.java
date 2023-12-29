package org.example.supermarket.dao;

import org.example.supermarket.database.DatabaseManager;
import org.example.supermarket.model.Order;
import org.example.supermarket.model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void placeOrder(Order order, List<OrderDetail> orderDetails) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement orderStatement = connection.prepareStatement(
                     "INSERT INTO orders (customerId, orderDate) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderDetailStatement = connection.prepareStatement(
                     "INSERT INTO order_details (orderId, itemId, quantity) VALUES (?, ?, ?)")) {

            // Insert order
            orderStatement.setInt(1, order.getCustomerId());
            orderStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            orderStatement.executeUpdate();

            try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    order.setId(orderId);

                    // Insert order details
                    for (OrderDetail orderDetail : orderDetails) {
                        orderDetailStatement.setInt(1, orderId);
                        orderDetailStatement.setInt(2, orderDetail.getItemId());
                        orderDetailStatement.setInt(3, orderDetail.getQuantity());
                        orderDetailStatement.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement orderStatement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");
             PreparedStatement orderDetailStatement = connection.prepareStatement("SELECT * FROM order_details WHERE orderId = ?")) {

            orderStatement.setInt(1, orderId);

            try (ResultSet orderResultSet = orderStatement.executeQuery()) {
                if (orderResultSet.next()) {
                    Order order = mapResultSetToOrder(orderResultSet);

                    // Get order details
                    orderDetailStatement.setInt(1, orderId);
                    try (ResultSet orderDetailResultSet = orderDetailStatement.executeQuery()) {
                        List<OrderDetail> orderDetails = mapResultSetToOrderDetails(orderDetailResultSet);
                        order.setOrderDetails(orderDetails);
                    }

                    return order;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {

            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);

                // Get order details
                int orderId = order.getId();
                List<OrderDetail> orderDetails = getOrderDetailsByOrderId(orderId);
                order.setOrderDetails(orderDetails);

                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    // Additional order-related methods can be added here

    private Order mapResultSetToOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setCustomerId(resultSet.getInt("customerId"));
        order.setOrderDate(resultSet.getTimestamp("orderDate").toLocalDateTime());
        // Additional mapping as needed
        return order;
    }

    private List<OrderDetail> mapResultSetToOrderDetails(ResultSet resultSet) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();

        while (resultSet.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(resultSet.getInt("id"));
            orderDetail.setOrderId(resultSet.getInt("orderId"));
            orderDetail.setItemId(resultSet.getInt("itemId"));
            orderDetail.setQuantity(resultSet.getInt("quantity"));
            // Additional mapping as needed
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    private List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement orderDetailStatement = connection.prepareStatement("SELECT * FROM order_details WHERE orderId = ?")) {

            orderDetailStatement.setInt(1, orderId);

            try (ResultSet resultSet = orderDetailStatement.executeQuery()) {
                orderDetails = mapResultSetToOrderDetails(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDetails;
    }
}
