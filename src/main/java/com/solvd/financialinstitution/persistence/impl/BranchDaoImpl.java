package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Branch;
import com.solvd.financialinstitution.persistence.ConnectionPool;
import com.solvd.financialinstitution.persistence.BranchDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BranchDaoImpl implements BranchDao {

    private static final String INSERT =
            "INSERT INTO branch (BANK_ID, ADDRESS_ID, CODE) VALUES (?, ?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT ID, BANK_ID, ADDRESS_ID, CODE FROM branch WHERE ID = ?";

    private static final String SELECT_ALL =
            "SELECT ID, BANK_ID, ADDRESS_ID, CODE FROM branch";

    private static final String UPDATE =
            "UPDATE branch SET BANK_ID = ?, ADDRESS_ID = ?, CODE = ? WHERE ID = ?";

    private static final String DELETE =
            "DELETE FROM branch WHERE ID = ?";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Branch branch) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, branch.getBankId());
            ps.setLong(2, branch.getAddressId());
            ps.setString(3, branch.getCode());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) branch.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public Optional<Branch> findById(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                Branch br = new Branch();
                br.setId(rs.getLong("ID"));
                br.setBankId(rs.getLong("BANK_ID"));
                br.setAddressId(rs.getLong("ADDRESS_ID"));
                br.setCode(rs.getString("CODE"));

                return Optional.of(br);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Branch> findAll() {
        List<Branch> list = new ArrayList<>();
        Connection c = pool.getConnection();

        try (PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Branch br = new Branch();
                br.setId(rs.getLong("ID"));
                br.setBankId(rs.getLong("BANK_ID"));
                br.setAddressId(rs.getLong("ADDRESS_ID"));
                br.setCode(rs.getString("CODE"));
                list.add(br);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }

        return list;
    }

    @Override
    public void update(Branch branch) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setLong(1, branch.getBankId());
            ps.setLong(2, branch.getAddressId());
            ps.setString(3, branch.getCode());
            ps.setLong(4, branch.getId());
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
