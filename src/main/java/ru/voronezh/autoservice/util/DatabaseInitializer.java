package ru.voronezh.autoservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String URL = "jdbc:h2:~/autoservice";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(URL, "sa", "");
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS owners (" +
                    "owner_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "first_name VARCHAR(50), " +
                    "last_name VARCHAR(50), " +
                    "phone VARCHAR(20))");

            stmt.execute("CREATE TABLE IF NOT EXISTS cars (" +
                    "car_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "owner_id INT, " +
                    "plate_number VARCHAR(20), " +
                    "brand VARCHAR(50))");

            stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                    "employee_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "first_name VARCHAR(50), " +
                    "last_name VARCHAR(50), " +
                    "position VARCHAR(50), " +
                    "salary DECIMAL(10,2), " +
                    "experience INT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS repairs (" +
                    "repair_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "car_id INT, " +
                    "employee_id INT, " +
                    "appeal_date DATE, " +
                    "malfunction_description TEXT)");

            System.out.println("Database initialized successfully!");

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}