package org.example.supermarket.database;

import java.sql.Connection;
import java.sql.Statement;

public class SupermarketDBSetup {
    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS items (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "price DOUBLE NOT NULL," +
                    "quantity INT NOT NULL)");

            statement.execute("CREATE TABLE IF NOT EXISTS customers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL)");

            statement.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "customerId INT NOT NULL," +
                    "orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (customerId) REFERENCES customers(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS order_details (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "orderId INT NOT NULL," +
                    "itemId INT NOT NULL," +
                    "quantity INT NOT NULL," +
                    "FOREIGN KEY (orderId) REFERENCES orders(id)," +
                    "FOREIGN KEY (itemId) REFERENCES items(id))");

            System.out.println("Database setup successful.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
