package org.example.supermarket.service;

import org.example.supermarket.dao.OrderDAO;
import org.example.supermarket.model.Item;
import org.example.supermarket.model.Order;
import org.example.supermarket.model.OrderDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private final static String ORDERED_QTY_EXCEEDS_AVAILABLE_QTY = "Ordered Quantity Exceeds Available Quantity";
    private final OrderDAO orderDAO;
    private final ItemService itemService;

    public OrderServiceImpl(OrderDAO orderDAO, ItemService itemService) {
        this.orderDAO = orderDAO;
        this.itemService = itemService;
    }

    @Override
    public void placeOrder(Order order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        Map<Integer, String> diagnosticMap = checkItemAvailability(orderDetails);

        if (!diagnosticMap.isEmpty()) {
            // Iterate through each entry in the map
            for (Map.Entry<Integer, String> entry : diagnosticMap.entrySet()) {
                int key = entry.getKey();
                String value = entry.getValue();
                // Perform actions based on key and value
                System.out.println("Item id: " + key + " - " + value);
                orderDetails.removeIf(orderDetail -> orderDetail.getItemId() == key);
            }
        }

        if (orderDetails.isEmpty()) {
            return;
        }
        orderDAO.placeOrder(order, orderDetails);
        System.out.println("Order placed successfully!");
    }

    private Map<Integer, String> checkItemAvailability(List<OrderDetail> orderDetails) {
        Map<Integer, String> diagnosticMap = new HashMap<>();
        for (OrderDetail orderDetail : orderDetails) {
            Item item = itemService.getItemById(orderDetail.getItemId());

            if (item != null) {
                int availableQty = item.getQuantity();
                int orderedQty = orderDetail.getQuantity();
                if (orderedQty > availableQty) {
                    diagnosticMap.put(orderDetail.getItemId(), ORDERED_QTY_EXCEEDS_AVAILABLE_QTY);
                }
            }
        }
        return diagnosticMap;
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
