package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Car;
import ru.voronezh.autoservice.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepositoryImpl implements CarRepository {

    private static final CarRepositoryImpl INSTANCE = new CarRepositoryImpl();

    private CarRepositoryImpl() {
    }

    public static CarRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
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