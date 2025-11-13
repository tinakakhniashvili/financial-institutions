package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.persistence.ConnectionPool;
import com.solvd.financialinstitution.persistence.dao.LoanDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanDaoImpl implements LoanDao {

    private static final String INSERT =
            "INSERT INTO loan (CUSTOMER_ID, BRANCH_ID, PRINCIPAL, RATE, START_DATE, END_DATE) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT ID, CUSTOMER_ID, BRANCH_ID, PRINCIPAL, RATE, START_DATE, END_DATE FROM loan WHERE ID = ?";

    private static final String SELECT_ALL =
            "SELECT ID, CUSTOMER_ID, BRANCH_ID, PRINCIPAL, RATE, START_DATE, END_DATE FROM loan";

    private static final String UPDATE =
            "UPDATE loan SET CUSTOMER_ID=?, BRANCH_ID=?, PRINCIPAL=?, RATE=?, START_DATE=?, END_DATE=? " +
                    "WHERE ID = ?";

    private static final String DELETE =
            "DELETE FROM loan WHERE ID = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Loan l) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, 0); // no customerId in model
            ps.setLong(2, 0); // no branchId in model
            ps.setBigDecimal(3, l.getPrincipal());
            ps.setBigDecimal(4, l.getRate());

            LocalDate start = l.getStart();
            if (start != null) ps.setDate(5, Date.valueOf(start));
            else ps.setNull(5, Types.DATE);

            LocalDate end = l.getEnd();
            if (end != null) ps.setDate(6, Date.valueOf(end));
            else ps.setNull(6, Types.DATE);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) l.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public Optional<Loan> findById(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                Loan l = new Loan();
                l.setId(rs.getLong("ID"));
                l.setPrincipal(rs.getBigDecimal("PRINCIPAL"));
                l.setRate(rs.getBigDecimal("RATE"));

                Date d1 = rs.getDate("START_DATE");
                if (d1 != null) l.setStart(d1.toLocalDate());

                Date d2 = rs.getDate("END_DATE");
                if (d2 != null) l.setEnd(d2.toLocalDate());

                return Optional.of(l);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Loan> findAll() {
        List<Loan> list = new ArrayList<>();
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loan l = new Loan();
                l.setId(rs.getLong("ID"));
                l.setPrincipal(rs.getBigDecimal("PRINCIPAL"));
                l.setRate(rs.getBigDecimal("RATE"));

                Date d1 = rs.getDate("START_DATE");
                if (d1 != null) l.setStart(d1.toLocalDate());

                Date d2 = rs.getDate("END_DATE");
                if (d2 != null) l.setEnd(d2.toLocalDate());

                list.add(l);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }

        return list;
    }

    @Override
    public void update(Loan l) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(UPDATE)) {

            ps.setLong(1, 0);
            ps.setLong(2, 0);
            ps.setBigDecimal(3, l.getPrincipal());
            ps.setBigDecimal(4, l.getRate());

            LocalDate start = l.getStart();
            if (start != null) ps.setDate(5, Date.valueOf(start));
            else ps.setNull(5, Types.DATE);

            LocalDate end = l.getEnd();
            if (end != null) ps.setDate(6, Date.valueOf(end));
            else ps.setNull(6, Types.DATE);

            ps.setLong(7, l.getId());

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
