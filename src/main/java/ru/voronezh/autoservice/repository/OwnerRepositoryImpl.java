package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Owner;
import ru.voronezh.autoservice.util.DatabaseConnection;

import java.sql.*;

public class OwnerRepositoryImpl implements OwnerRepository {

    private static final OwnerRepositoryImpl INSTANCE = new OwnerRepositoryImpl();

    private OwnerRepositoryImpl() {
    }

    public static OwnerRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Owner findById(int id) {
        String sql = "SELECT * FROM owners WHERE owner_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Owner o = new Owner();
                o.setOwnerId(rs.getInt("owner_id"));
                o.setFirstName(rs.getString("first_name"));
                o.setLastName(rs.getString("last_name"));
                o.setPhone(rs.getString("phone"));
                return o;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка: " + ex.getMessage());
        }

        return null;
    }
}