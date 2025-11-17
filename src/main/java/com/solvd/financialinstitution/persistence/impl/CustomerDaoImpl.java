package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.persistence.ConnectionPool;
import com.solvd.financialinstitution.persistence.CustomerDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    private static final String INSERT =
            "INSERT INTO customer (full_name, birth_date) VALUES (?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT id, full_name, birth_date FROM customer WHERE id = ?";

    private static final String SELECT_ALL =
            "SELECT id, full_name, birth_date FROM customer";

    private static final String UPDATE =
            "UPDATE customer SET full_name = ?, birth_date = ? WHERE id = ?";

    private static final String DELETE =
            "DELETE FROM customer WHERE id = ?";

    private static final String SELECT_BY_CITY =
            "SELECT c.id, c.full_name, c.birth_date " +
                    "FROM customer c " +
                    "JOIN address a ON c.ADDRESS_ID = a.ID " +
                    "WHERE a.CITY = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Customer customer) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getFullName());

            LocalDate birthDate = customer.getBirthDate();
            if (birthDate != null) {
                ps.setDate(2, Date.valueOf(birthDate));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public Optional<Customer> findById(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setFullName(rs.getString("full_name"));

                Date date = rs.getDate("birth_date");
                if (date != null) {
                    customer.setBirthDate(date.toLocalDate());
                }

                return Optional.of(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setFullName(rs.getString("full_name"));

                Date date = rs.getDate("birth_date");
                if (date != null) {
                    customer.setBirthDate(date.toLocalDate());
                }

                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, customer.getFullName());

            LocalDate birthDate = customer.getBirthDate();
            if (birthDate != null) {
                ps.setDate(2, Date.valueOf(birthDate));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setLong(3, customer.getId());
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

    @Override
    public List<Customer> findByCity(String city) {
        List<Customer> customers = new ArrayList<>();
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_CITY)) {
            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getLong("id"));
                    customer.setFullName(rs.getString("full_name"));

                    Date date = rs.getDate("birth_date");
                    if (date != null) {
                        customer.setBirthDate(date.toLocalDate());
                    }

                    customers.add(customer);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }

        return customers;
    }
}
