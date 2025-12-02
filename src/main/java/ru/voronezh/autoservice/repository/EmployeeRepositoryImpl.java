package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Employee;
import ru.voronezh.autoservice.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final EmployeeRepositoryImpl INSTANCE = new EmployeeRepositoryImpl();

    private EmployeeRepositoryImpl() {
    }

    public static EmployeeRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeId(rs.getInt("employee_id"));
                e.setFirstName(rs.getString("first_name"));
                e.setLastName(rs.getString("last_name"));
                e.setPosition(rs.getString("position"));
                e.setSalary(rs.getBigDecimal("salary"));
                e.setExperience(rs.getInt("experience"));
                employees.add(e);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка: " + ex.getMessage());
        }

        return employees;
    }

    @Override
    public void create(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, position, salary, experience) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getPosition());
            ps.setBigDecimal(4, employee.getSalary());
            ps.setInt(5, employee.getExperience());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка: " + ex.getMessage());
        }
    }
}