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
        OrderService orderService = new OrderServiceImpl(new OrderDAOImpl(), itemService);

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
                    manageOrders(orderService, itemService, customerService);
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
        System.out.println("3. Update Item");
        System.out.println("4. Delete Item");
        System.out.println("5. Go Back");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addItem(itemService);
                break;
            case 2:
                viewAllItems(itemService);
                break;
            case 3:
                updateItem(itemService);
                break;
            case 4:
                deleteItem(itemService);
                break;
            case 5:
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
        manageItems(itemService);
    }

    private static void viewAllItems(ItemService itemService) {
        System.out.println("=== All Items ===");
        List<Item> items = itemService.getAllItems();

        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            displayItems(items);
            manageItems(itemService);
        }
    }

    private static void updateItem(ItemService itemService) {
        System.out.println("=== All Items ===");
        List<Item> items = itemService.getAllItems();

        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            displayItems(items);
            updateItemForAGivenItemId(itemService);
            manageItems(itemService);
        }
    }

    private static void updateItemForAGivenItemId(ItemService itemService) {
        System.out.println("=== Update Item ===");
        int itemId = getIntInput("Enter item id to update: ");
        Item itemToUpdate = itemService.getItemById(itemId);

        System.out.println("=== Select field to update ===");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Quantity");
        System.out.println("4. Go Back");
        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                String name = getStringInput("Enter item name to update: ");
                itemToUpdate.setName(name);
                break;
            case 2:
                double price = getDoubleInput("Enter item price to update: ");
                itemToUpdate.setPrice(price);
                break;
            case 3:
                int quantity = getIntInput("Enter item quantity to update: ");
                itemToUpdate.setQuantity(quantity);
                break;
            case 4:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }

        itemService.updateItem(itemToUpdate);

        System.out.println("Item updated successfully!");
    }

    private static void deleteItem(ItemService itemService) {
        System.out.println("=== All Items ===");
        List<Item> items = itemService.getAllItems();

        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            displayItems(items);
            deleteItemForAGivenItemId(itemService);
            manageItems(itemService);
        }
    }

    private static void deleteItemForAGivenItemId(ItemService itemService) {
        System.out.println("=== Delete Item ===");
        int itemId = getIntInput("Enter item id to delete: ");

        itemService.deleteItem(itemId);

        System.out.println("Item deleted successfully!");
    }

    private static void manageCustomers(CustomerService customerService) {
        System.out.println("=== Manage Customers ===");
        System.out.println("1. Add Customer");
        System.out.println("2. View All Customer");
        System.out.println("3. Update Customer");
        System.out.println("4. Delete Customer");
        System.out.println("5. Go Back");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addCustomer(customerService);
                break;
            case 2:
                viewAllCustomers(customerService);
                break;
            case 3:
                updateCustomer(customerService);
                break;
            case 4:
                deleteCustomer(customerService);
                break;
            case 5:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private static void addCustomer(CustomerService customerService) {
        System.out.println("=== Add Customer ===");
        String name = getStringInput("Enter customer name: ");
        String email = getStringInput("Enter customer email: ");

        Customer customer = new Customer(name, email);

        customerService.addCustomer(customer);

        System.out.println("Customer added successfully!");
        manageCustomers(customerService);
    }

    private static void viewAllCustomers(CustomerService customerService) {
        System.out.println("=== All Customers ===");
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            displayCustomers(customers);
            manageCustomers(customerService);
        }
    }

    private static void updateCustomer(CustomerService customerService) {
        System.out.println("=== All Customers ===");
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            displayCustomers(customers);
            updateCustomerForAGivenCustomerId(customerService);
            manageCustomers(customerService);
        }
    }

    private static void updateCustomerForAGivenCustomerId(CustomerService customerService) {
        System.out.println("=== Update Customer ===");
        int customerId = getIntInput("Enter Customer id to update: ");
        Customer customerToUpdate = customerService.getCustomerById(customerId);

        System.out.println("=== Select field to update ===");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Go Back");
        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                String name = getStringInput("Enter customer name to update: ");
                customerToUpdate.setName(name);
                break;
            case 2:
                String email = getStringInput("Enter customer email to update: ");
                customerToUpdate.setEmail(email);
                break;
            case 4:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }

        customerService.updateCustomer(customerToUpdate);

        System.out.println("Customer updated successfully!");
    }

    private static void deleteCustomer(CustomerService customerService) {
        System.out.println("=== All Customers ===");
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            displayCustomers(customers);
            deleteCustomerForAGivenCustomerId(customerService);
            manageCustomers(customerService);
        }
    }

    private static void deleteCustomerForAGivenCustomerId(CustomerService customerService) {
        System.out.println("=== Delete Customer ===");
        int customerId = getIntInput("Enter Customer id to delete: ");

        customerService.deleteCustomer(customerId);

        System.out.println("Customer deleted successfully!");
    }

    private static void manageOrders(OrderService orderService,
                                     ItemService itemService, CustomerService customerService) {
        System.out.println("=== Manage Orders ===");
        System.out.println("1. Add Order");
        System.out.println("2. View All Orders");
        System.out.println("3. Update Order");
        System.out.println("4. Delete Order");
        System.out.println("5. Go Back");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                placeOrder(orderService, itemService, customerService);
                break;
            case 2:
                viewAllOrders(orderService, itemService, customerService);
                break;
//            case 3:
//                updateItem(itemService);
//                break;
//            case 4:
//                deleteItem(itemService);
//                break;
            case 5:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private static void placeOrder(OrderService orderService,
                                   ItemService itemService, CustomerService customerService) {
        // display all items
        System.out.println("=== All Items ===");
        List<Item> items = itemService.getAllItems();

        if (items.isEmpty()) {
            System.out.println("No items found.");
        } else {
            displayItems(items);
        }

        // display all customers
        System.out.println("=== All Customers ===");
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            displayCustomers(customers);
        }

        // Get customer information
        int customerId = getIntInput("Enter customer ID: ");

        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please make sure the customer ID is correct.");
            return;
        }

        // Get order details
        List<OrderDetail> orderDetails = getOrderDetails(itemService);

        // Create order
        Order order = new Order(customerId, LocalDateTime.now(), orderDetails);

        // Place order
        orderService.placeOrder(order);
    }

    private static void viewAllOrders(OrderService orderService, ItemService itemService, CustomerService customerService) {
        System.out.println("=== All Orders ===");
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            displayOrders(orders);
            manageOrders(orderService, itemService, customerService);
        }
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

    private static void displayCustomers(List<Customer> customers) {
        System.out.println("ID\tName\tEmail");
        for (Customer customer : customers) {
            System.out.printf("%d\t%s\t%s\n", customer.getId(), customer.getName(), customer.getEmail());
        }
    }

    private static void displayOrders(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("OrderId\tCustomerId\tOrderDate");
            System.out.printf("%d\t%d\t%s\n", order.getId(), order.getCustomerId(), order.getOrderDate());

            for (OrderDetail orderDetail : order.getOrderDetails()) {
                System.out.println("OrderDetailId\tItemId\tQuantity");
                System.out.printf("%d\t%d\t%s\n", orderDetail.getId(), orderDetail.getItemId(), orderDetail.getQuantity());
            }
            System.out.println();
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