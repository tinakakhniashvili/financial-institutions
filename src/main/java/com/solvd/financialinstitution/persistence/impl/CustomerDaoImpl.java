package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Account;
import com.solvd.financialinstitution.domain.AccountType;
import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.persistence.ConnectionPool;
import com.solvd.financialinstitution.persistence.CustomerDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    private static final String SELECT_WITH_ACCOUNTS_AND_LOANS_BY_ID =
            "SELECT " +
                    "c.ID AS c_id, c.FULL_NAME AS c_full_name, c.BIRTH_DATE AS c_birth_date, " +
                    "a.ID AS acc_id, a.IBAN AS acc_iban, a.BALANCE AS acc_balance, a.OPENED_ON AS acc_opened_on, " +
                    "atp.ID AS at_id, atp.CODE AS at_code, atp.NAME AS at_name, " +
                    "l.ID AS l_id, l.PRINCIPAL AS l_principal, l.RATE AS l_rate, " +
                    "l.START_DATE AS l_start_date, l.END_DATE AS l_end_date " +
                    "FROM customer c " +
                    "LEFT JOIN account a ON a.CUSTOMER_ID = c.ID " +
                    "LEFT JOIN account_type atp ON atp.ID = a.ACCOUNT_TYPE_ID " +
                    "LEFT JOIN loan l ON l.CUSTOMER_ID = c.ID " +
                    "WHERE c.ID = ?";

    private static final String SELECT_BY_CITY =
            "SELECT c.ID, c.FULL_NAME, c.BIRTH_DATE " +
                    "FROM customer c " +
                    "JOIN address addr ON addr.ID = c.ADDRESS_ID " +
                    "WHERE addr.CITY = ?";

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
    public Optional<Customer> findByIdWithAccountsAndLoans(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_WITH_ACCOUNTS_AND_LOANS_BY_ID)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                Customer customer = null;
                Map<Long, Account> accounts = new LinkedHashMap<>();
                Map<Long, Loan> loans = new LinkedHashMap<>();

                while (rs.next()) {
                    if (customer == null) {
                        customer = new Customer();
                        customer.setId(rs.getLong("c_id"));
                        customer.setFullName(rs.getString("c_full_name"));
                        Date date = rs.getDate("c_birth_date");
                        if (date != null) {
                            customer.setBirthDate(date.toLocalDate());
                        }
                    }

                    long accId = rs.getLong("acc_id");
                    if (!rs.wasNull()) {
                        Account acc = accounts.get(accId);
                        if (acc == null) {
                            acc = new Account();
                            acc.setId(accId);
                            acc.setIban(rs.getString("acc_iban"));
                            acc.setBalance(rs.getBigDecimal("acc_balance"));
                            Date opened = rs.getDate("acc_opened_on");
                            if (opened != null) {
                                acc.setOpenedOn(opened.toLocalDate());
                            }

                            long atId = rs.getLong("at_id");
                            if (!rs.wasNull()) {
                                AccountType type = new AccountType();
                                type.setId(atId);
                                type.setCode(rs.getString("at_code"));
                                type.setName(rs.getString("at_name"));
                                acc.setType(type);
                            }

                            accounts.put(accId, acc);
                        }
                    }

                    long loanId = rs.getLong("l_id");
                    if (!rs.wasNull()) {
                        Loan l = loans.get(loanId);
                        if (l == null) {
                            l = new Loan();
                            l.setId(loanId);
                            l.setPrincipal(rs.getBigDecimal("l_principal"));
                            l.setRate(rs.getBigDecimal("l_rate"));
                            Date s = rs.getDate("l_start_date");
                            if (s != null) {
                                l.setStart(s.toLocalDate());
                            }
                            Date e = rs.getDate("l_end_date");
                            if (e != null) {
                                l.setEnd(e.toLocalDate());
                            }
                            loans.put(loanId, l);
                        }
                    }
                }

                if (customer == null) {
                    return Optional.empty();
                }

                customer.setAccounts(new ArrayList<>(accounts.values()));
                customer.setLoans(new ArrayList<>(loans.values()));

                return Optional.of(customer);
            }

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
                    customer.setId(rs.getLong("ID"));
                    customer.setFullName(rs.getString("FULL_NAME"));
                    Date date = rs.getDate("BIRTH_DATE");
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
