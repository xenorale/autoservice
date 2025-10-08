package ru.voronezh.autoservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM employees");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO employees (first_name, last_name, position, salary, experience) " +
                        "VALUES ('Алексей', 'Механиков', 'Механик', 50000, 5), " +
                        "('Дмитрий', 'Ремонтов', 'Мастер', 65000, 8)");
                System.out.println("✓ Добавлены тестовые сотрудники");
            }

            ResultSet rsCars = stmt.executeQuery("SELECT COUNT(*) FROM cars");
            if (rsCars.next() && rsCars.getInt(1) == 0) {
                stmt.execute("INSERT INTO owners (first_name, last_name, phone) " +
                        "VALUES ('Иван', 'Петров', '+7-900-123-45-67'), " +
                        "('Мария', 'Сидорова', '+7-900-765-43-21')");

                stmt.execute("INSERT INTO cars (owner_id, plate_number, brand) " +
                        "VALUES (1, 'А123БВ', 'Lada Granta'), " +
                        "(2, 'В456ГД', 'Toyota Camry')");

                System.out.println("✓ Добавлены тестовые владельцы и автомобили");
            }

            System.out.println("Database initialized successfully!");

        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
