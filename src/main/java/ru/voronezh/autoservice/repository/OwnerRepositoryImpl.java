package ru.voronezh.autoservice.repository;

import ru.voronezh.autoservice.model.Owner;
import java.sql.*;

public class OwnerRepositoryImpl implements OwnerRepository {
    private static final String URL = "jdbc:h2:~/autoservice";
    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public Owner findById(int id) {
        String sql = "SELECT * FROM owners WHERE owner_id = ?";
        try (Connection conn = getConnection();
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