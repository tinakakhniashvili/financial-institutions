package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Account;
import com.solvd.financialinstitution.domain.AccountType;
import com.solvd.financialinstitution.persistence.AccountDao;
import com.solvd.financialinstitution.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private static final String INSERT =
            "INSERT INTO account (CUSTOMER_ID, BRANCH_ID, ACCOUNT_TYPE_ID, IBAN, BALANCE, OPENED_ON) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT ID, CUSTOMER_ID, BRANCH_ID, ACCOUNT_TYPE_ID, IBAN, BALANCE, OPENED_ON " +
                    "FROM account WHERE ID = ?";

    private static final String SELECT_ALL =
            "SELECT ID, CUSTOMER_ID, BRANCH_ID, ACCOUNT_TYPE_ID, IBAN, BALANCE, OPENED_ON FROM account";

    private static final String UPDATE =
            "UPDATE account SET CUSTOMER_ID=?, BRANCH_ID=?, ACCOUNT_TYPE_ID=?, IBAN=?, BALANCE=?, OPENED_ON=? " +
                    "WHERE ID = ?";

    private static final String DELETE =
            "DELETE FROM account WHERE ID = ?";

    private static final String SELECT_BY_CUSTOMER_ID =
            "SELECT ID, CUSTOMER_ID, BRANCH_ID, ACCOUNT_TYPE_ID, IBAN, BALANCE, OPENED_ON " +
                    "FROM account WHERE CUSTOMER_ID = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Account a) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, 0);
            ps.setLong(2, 0);
            ps.setLong(3, a.getType() != null ? a.getType().getId() : 0);
            ps.setString(4, a.getIban());
            ps.setBigDecimal(5, a.getBalance());
            if (a.getOpenedOn() != null)
                ps.setDate(6, Date.valueOf(a.getOpenedOn()));
            else
                ps.setNull(6, Types.DATE);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public Optional<Account> findById(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                Account a = new Account();
                a.setId(rs.getLong("ID"));
                a.setIban(rs.getString("IBAN"));
                a.setBalance(rs.getBigDecimal("BALANCE"));

                Date date = rs.getDate("OPENED_ON");
                if (date != null) a.setOpenedOn(date.toLocalDate());

                AccountType type = new AccountType();
                type.setId(rs.getLong("ACCOUNT_TYPE_ID"));
                a.setType(type);

                return Optional.of(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Account> findAll() {
        List<Account> list = new ArrayList<>();
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account a = new Account();
                a.setId(rs.getLong("ID"));
                a.setIban(rs.getString("IBAN"));
                a.setBalance(rs.getBigDecimal("BALANCE"));

                Date date = rs.getDate("OPENED_ON");
                if (date != null) a.setOpenedOn(date.toLocalDate());

                AccountType type = new AccountType();
                type.setId(rs.getLong("ACCOUNT_TYPE_ID"));
                a.setType(type);

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
    public void update(Account a) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(UPDATE)) {

            ps.setLong(1, 0);
            ps.setLong(2, 0);
            ps.setLong(3, a.getType() != null ? a.getType().getId() : 0);
            ps.setString(4, a.getIban());
            ps.setBigDecimal(5, a.getBalance());
            if (a.getOpenedOn() != null)
                ps.setDate(6, Date.valueOf(a.getOpenedOn()));
            else
                ps.setNull(6, Types.DATE);

            ps.setLong(7, a.getId());

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
    public List<Account> findByCustomerId(long customerId) {
        List<Account> list = new ArrayList<>();
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_CUSTOMER_ID)) {
            ps.setLong(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account a = new Account();
                    a.setId(rs.getLong("ID"));
                    a.setIban(rs.getString("IBAN"));
                    a.setBalance(rs.getBigDecimal("BALANCE"));

                    Date date = rs.getDate("OPENED_ON");
                    if (date != null) a.setOpenedOn(date.toLocalDate());

                    AccountType type = new AccountType();
                    type.setId(rs.getLong("ACCOUNT_TYPE_ID"));
                    a.setType(type);

                    list.add(a);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }

        return list;
    }
}
