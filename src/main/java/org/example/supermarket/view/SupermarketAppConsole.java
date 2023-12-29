package org.example.supermarket.view;

import org.example.supermarket.dao.CustomerDAOImpl;
import org.example.supermarket.dao.ItemDAOImpl;
import org.example.supermarket.dao.OrderDAOImpl;
import org.example.supermarket.database.SupermarketDBSetup;
import org.example.supermarket.model.Customer;
import org.example.supermarket.model.Item;
import org.example.supermarket.model.Order;
import org.example.supermarket.model.OrderDetail;
import org.example.supermarket.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SupermarketAppConsole {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize database
        SupermarketDBSetup.main(args);

        ItemService itemService = new ItemServiceImpl(new ItemDAOImpl());
        CustomerService customerService = new CustomerServiceImpl(new CustomerDAOImpl());
        OrderService orderService = new OrderServiceImpl(new OrderDAOImpl());

        // Main menu loop
        while (true) {
            System.out.println("=== Supermarket Management System ===");
            System.out.println("1. Manage Items");
            System.out.println("2. Manage Customers");
            System.out.println("3. Place Order");
            System.out.println("4. Exit");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    manageItems(itemService);
                    break;
                case 2:
                    manageCustomers(customerService);
                    break;
                case 3:
                    placeOrder(orderService, itemService, customerService);
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void manageItems(ItemService itemService) {
        System.out.println("=== Manage Items ===");
        System.out.println("1. Add Item");
        System.out.println("2. View All Items");
        System.out.println("3. Go Back");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addItem(itemService);
                break;
            case 2:
                viewAllItems(itemService);
                break;
            case 3:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private static void addItem(ItemService itemService) {
        System.out.println("=== Add Item ===");
        String name = getStringInput("Enter item name: ");
        double price = getDoubleInput("Enter item price: ");
        int quantity = getIntInput("Enter item quantity: ");

        Item item = new Item(name, price, quantity);
        itemService.addItem(item);

        System.out.println("Item added successfully!");
    }

    private static void viewAllItems(ItemService itemService) {
        System.out.println("=== All Items ===");
        List<Item> items = itemService.getAllItems();

        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            displayItems(items);
        }
    }

    private static void manageCustomers(CustomerService customerService) {
        // Implement customer management functionality
        // Add options for adding, updating, deleting customers, etc.
    }

    private static void placeOrder(OrderService orderService,
                                   ItemService itemService, CustomerService customerService) {
        // Get customer information
        int customerId = getIntInput("Enter customer ID: ");
        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please make sure the customer ID is correct.");
            return;
        }

        // Display available items
        System.out.println("Available Items:");
        List<Item> availableItems = itemService.getAllItems();
        displayItems(availableItems);

        // Get order details
        List<OrderDetail> orderDetails = getOrderDetails(itemService);

        // Create order
        Order order = new Order(customerId, LocalDateTime.now(), orderDetails);

        // Place order
        orderService.placeOrder(order, orderDetails);

        System.out.println("Order placed successfully!");
    }

    private static List<OrderDetail> getOrderDetails(ItemService itemService) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        while (true) {
            int itemId = getIntInput("Enter item ID (0 to finish): ");

            if (itemId == 0) {
                break;
            }

            Item item = itemService.getItemById(itemId);

            if (item == null) {
                System.out.println("Item not found. Please make sure the item ID is correct.");
                continue;
            }

            int quantity = getIntInput("Enter quantity: ");

            OrderDetail orderDetail = new OrderDetail(0, itemId, quantity);
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    private static void displayItems(List<Item> items) {
        System.out.println("ID\tName\tPrice\tQuantity");
        for (Item item : items) {
            System.out.printf("%d\t%s\t%.2f\t%d\n", item.getId(), item.getName(), item.getPrice(), item.getQuantity());
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            System.out.print(prompt);
            scanner.next(); // Consume invalid input
        }
        return scanner.nextInt();
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid double.");
            System.out.print(prompt);
            scanner.next(); // Consume invalid input
        }
        return scanner.nextDouble();
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
        return scanner.nextLine();
    }
}