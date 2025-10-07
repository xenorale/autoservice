package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Repair;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairRepositoryImpl implements RepairRepository {
    private static final String URL = "jdbc:h2:~/autoservice";
    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public void create(Repair repair) {
        String sql = "INSERT INTO repairs (car_id, employee_id, appeal_date, malfunction_description) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, repair.getCarId());
            ps.setInt(2, repair.getEmployeeId());
            ps.setDate(3, Date.valueOf(repair.getAppealDate()));
            ps.setString(4, repair.getMalfunctionDescription());
            ps.executeUpdate();
            System.out.println("✓ Ремонт добавлен");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public List<Repair> findAll() {
        String sql = "SELECT * FROM repairs LIMIT 50";
        List<Repair> repairs = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Repair r = new Repair();
                r.setRepairId(rs.getInt("repair_id"));
                r.setCarId(rs.getInt("car_id"));
                r.setEmployeeId(rs.getInt("employee_id"));
                r.setAppealDate(rs.getDate("appeal_date").toLocalDate());
                r.setMalfunctionDescription(rs.getString("malfunction_description"));
                repairs.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
        return repairs;
    }
}