package ru.voronezh.autoservice.util;

import java.sql.*;

public class DatabaseInitializer {

    public static void init() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS owners (" +
                    "owner_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "first_name VARCHAR(100), " +
                    "last_name VARCHAR(100), " +
                    "phone VARCHAR(20))");

            stmt.execute("CREATE TABLE IF NOT EXISTS cars (" +
                    "car_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "owner_id INT, " +
                    "plate_number VARCHAR(20), " +
                    "brand VARCHAR(100), " +
                    "FOREIGN KEY (owner_id) REFERENCES owners(owner_id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                    "employee_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "first_name VARCHAR(100), " +
                    "last_name VARCHAR(100), " +
                    "position VARCHAR(100), " +
                    "salary DECIMAL(10, 2), " +
                    "experience INT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS repairs (" +
                    "repair_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "car_id INT, " +
                    "employee_id INT, " +
                    "appeal_date DATE, " +
                    "malfunction_description TEXT, " +
                    "FOREIGN KEY (car_id) REFERENCES cars(car_id), " +
                    "FOREIGN KEY (employee_id) REFERENCES employees(employee_id))");

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM owners");
            rs.next();

            if (rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO owners (first_name, last_name, phone) VALUES " +
                        "('Иван', 'Иванов', '+79001234567'), " +
                        "('Пётр', 'Петров', '+79007654321'), " +
                        "('Алексей', 'Сидоров', '+79005556677')");

                stmt.execute("INSERT INTO cars (owner_id, plate_number, brand) VALUES " +
                        "(1, 'А123БВ36', 'Lada Granta'), " +
                        "(2, 'В456ГД36', 'Toyota Camry'), " +
                        "(3, 'Е789ЖЗ36', 'BMW X5')");

                stmt.execute("INSERT INTO employees (first_name, last_name, position, salary, experience) VALUES " +
                        "('Алексей', 'Механиков', 'Механик', 50000, 5), " +
                        "('Дмитрий', 'Ремонтов', 'Мастер', 65000, 8), " +
                        "('Николай', 'Мастеров', 'Мастер-приёмщик', 55000, 7)");

                stmt.execute("INSERT INTO repairs (car_id, employee_id, appeal_date, malfunction_description) VALUES " +
                        "(1, 1, '2025-11-20', 'Замена масла и фильтров'), " +
                        "(2, 2, '2025-11-21', 'Проблема с электрикой'), " +
                        "(3, 1, '2025-11-22', 'Диагностика двигателя')");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}