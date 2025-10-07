package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepositoryImpl implements CarRepository {
    private static final String URL = "jdbc:h2:~/autoservice";
    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Car c = new Car();
                c.setCarId(rs.getInt("car_id"));
                c.setOwnerId(rs.getInt("owner_id"));
                c.setPlateNumber(rs.getString("plate_number"));
                c.setBrand(rs.getString("brand"));
                cars.add(c);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка: " + ex.getMessage());
        }
        return cars;
    }
}