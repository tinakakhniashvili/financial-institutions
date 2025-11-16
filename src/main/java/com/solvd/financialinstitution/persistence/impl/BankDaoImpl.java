package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.persistence.ConnectionPool;
import com.solvd.financialinstitution.persistence.BankDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankDaoImpl implements BankDao {

    private static final String INSERT =
            "INSERT INTO bank (NAME) VALUES (?)";

    private static final String SELECT_BY_ID =
            "SELECT ID, NAME FROM bank WHERE ID = ?";

    private static final String SELECT_ALL =
            "SELECT ID, NAME FROM bank";

    private static final String UPDATE =
            "UPDATE bank SET NAME = ? WHERE ID = ?";

    private static final String DELETE =
            "DELETE FROM bank WHERE ID = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Bank bank) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bank.getName());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    bank.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public Optional<Bank> findById(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                Bank bank = new Bank();
                bank.setId(rs.getLong("ID"));
                bank.setName(rs.getString("NAME"));

                return Optional.of(bank);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bank bank = new Bank();
                bank.setId(rs.getLong("ID"));
                bank.setName(rs.getString("NAME"));
                banks.add(bank);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
        return banks;
    }

    @Override
    public void update(Bank bank) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, bank.getName());
            ps.setLong(2, bank.getId());
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
