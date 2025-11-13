package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Address;
import com.solvd.financialinstitution.persistence.ConnectionPool;
import com.solvd.financialinstitution.persistence.dao.AddressDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDaoImpl implements AddressDao {

    private static final String INSERT =
            "INSERT INTO address (COUNTRY, CITY, LINE1, ZIP) VALUES (?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT ID, COUNTRY, CITY, LINE1, ZIP FROM address WHERE ID = ?";

    private static final String SELECT_ALL =
            "SELECT ID, COUNTRY, CITY, LINE1, ZIP FROM address";

    private static final String UPDATE =
            "UPDATE address SET COUNTRY = ?, CITY = ?, LINE1 = ?, ZIP = ? WHERE ID = ?";

    private static final String DELETE =
            "DELETE FROM address WHERE ID = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Address address) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getLine1());
            ps.setString(4, address.getZip());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) address.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public Optional<Address> findById(long id) {
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                Address a = new Address();
                a.setId(rs.getLong("ID"));
                a.setCountry(rs.getString("COUNTRY"));
                a.setCity(rs.getString("CITY"));
                a.setLine1(rs.getString("LINE1"));
                a.setZip(rs.getString("ZIP"));
                return Optional.of(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Address> findAll() {
        List<Address> list = new ArrayList<>();
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Address a = new Address();
                a.setId(rs.getLong("ID"));
                a.setCountry(rs.getString("COUNTRY"));
                a.setCity(rs.getString("CITY"));
                a.setLine1(rs.getString("LINE1"));
                a.setZip(rs.getString("ZIP"));
                list.add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }

        return list;
    }

    @Override
    public void update(Address address) {
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getLine1());
            ps.setString(4, address.getZip());
            ps.setLong(5, address.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public void delete(long id) {
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }
}
