package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Address;
import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.domain.Branch;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BankDaoImpl implements BankDao {

    private static final String INSERT =
            "INSERT INTO bank (NAME) VALUES (?)";

    private static final String SELECT_BY_ID =
            "SELECT ID, NAME, ACTIVE FROM bank WHERE ID = ?";

    private static final String SELECT_ALL =
            "SELECT ID, NAME, ACTIVE FROM bank";

    private static final String UPDATE =
            "UPDATE bank SET NAME = ? WHERE ID = ?";

    private static final String DELETE =
            "DELETE FROM bank WHERE ID = ?";

    private static final String SELECT_WITH_BRANCHES_BY_ID =
            "SELECT " +
                    "b.ID AS b_id, b.NAME AS b_name, b.ACTIVE AS b_active, " +
                    "br.ID AS br_id, br.CODE AS br_code, br.ADDRESS_ID AS br_address_id, " +
                    "a.ID AS addr_id, a.COUNTRY AS addr_country, a.CITY AS addr_city, " +
                    "a.LINE1 AS addr_line1, a.ZIP AS addr_zip " +
                    "FROM bank b " +
                    "LEFT JOIN branch br ON br.BANK_ID = b.ID " +
                    "LEFT JOIN address a ON a.ID = br.ADDRESS_ID " +
                    "WHERE b.ID = ?";

    private static final String SELECT_WITH_BRANCHES_ALL =
            "SELECT " +
                    "b.ID AS b_id, b.NAME AS b_name, b.ACTIVE AS b_active, " +
                    "br.ID AS br_id, br.CODE AS br_code, br.ADDRESS_ID AS br_address_id, " +
                    "a.ID AS addr_id, a.COUNTRY AS addr_country, a.CITY AS addr_city, " +
                    "a.LINE1 AS addr_line1, a.ZIP AS addr_zip " +
                    "FROM bank b " +
                    "LEFT JOIN branch br ON br.BANK_ID = b.ID " +
                    "LEFT JOIN address a ON a.ID = br.ADDRESS_ID " +
                    "ORDER BY b.ID";

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
                bank.setActive(rs.getBoolean("ACTIVE"));

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
                bank.setActive(rs.getBoolean("ACTIVE"));
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

    @Override
    public Optional<Bank> findByIdWithBranchesAndAddresses(long id) {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_WITH_BRANCHES_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Bank bank = null;
                Map<Long, Branch> branches = new LinkedHashMap<>();

                while (rs.next()) {
                    if (bank == null) {
                        bank = new Bank();
                        bank.setId(rs.getLong("b_id"));
                        bank.setName(rs.getString("b_name"));
                        bank.setActive(rs.getBoolean("b_active"));
                    }

                    long branchId = rs.getLong("br_id");
                    if (rs.wasNull()) {
                        continue;
                    }

                    Branch branch = branches.get(branchId);
                    if (branch == null) {
                        branch = new Branch();
                        branch.setId(branchId);
                        branch.setCode(rs.getString("br_code"));
                        branch.setBankId(bank.getId());

                        long addrId = rs.getLong("addr_id");
                        if (!rs.wasNull()) {
                            Address addr = new Address();
                            addr.setId(addrId);
                            addr.setCountry(rs.getString("addr_country"));
                            addr.setCity(rs.getString("addr_city"));
                            addr.setLine1(rs.getString("addr_line1"));
                            addr.setZip(rs.getString("addr_zip"));
                            branch.setAddressId(addrId);
                            branch.setAddress(addr);
                        }

                        branches.put(branchId, branch);
                    }
                }

                if (bank == null) {
                    return Optional.empty();
                }

                bank.setBranches(new ArrayList<>(branches.values()));
                return Optional.of(bank);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }

    @Override
    public List<Bank> findAllWithBranchesAndAddresses() {
        Connection c = pool.getConnection();
        try (PreparedStatement ps = c.prepareStatement(SELECT_WITH_BRANCHES_ALL);
             ResultSet rs = ps.executeQuery()) {

            Map<Long, Bank> banks = new LinkedHashMap<>();

            while (rs.next()) {
                long bankId = rs.getLong("b_id");
                Bank bank = banks.get(bankId);
                if (bank == null) {
                    bank = new Bank();
                    bank.setId(bankId);
                    bank.setName(rs.getString("b_name"));
                    bank.setActive(rs.getBoolean("b_active"));
                    bank.setBranches(new ArrayList<>());
                    banks.put(bankId, bank);
                }

                long branchId = rs.getLong("br_id");
                if (rs.wasNull()) {
                    continue;
                }

                List<Branch> branchList = bank.getBranches();
                Branch existing = null;
                for (Branch b : branchList) {
                    if (branchId == b.getId()) {
                        existing = b;
                        break;
                    }
                }

                if (existing == null) {
                    Branch branch = new Branch();
                    branch.setId(branchId);
                    branch.setCode(rs.getString("br_code"));
                    branch.setBankId(bankId);

                    long addrId = rs.getLong("addr_id");
                    if (!rs.wasNull()) {
                        Address addr = new Address();
                        addr.setId(addrId);
                        addr.setCountry(rs.getString("addr_country"));
                        addr.setCity(rs.getString("addr_city"));
                        addr.setLine1(rs.getString("addr_line1"));
                        addr.setZip(rs.getString("addr_zip"));
                        branch.setAddressId(addrId);
                        branch.setAddress(addr);
                    }

                    branchList.add(branch);
                }
            }

            return new ArrayList<>(banks.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            pool.releaseConnection(c);
        }
    }
}
