package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Address;
import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.domain.Branch;
import com.solvd.financialinstitution.persistence.AddressDao;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.BranchDao;
import com.solvd.financialinstitution.persistence.impl.AddressDaoImpl;
import com.solvd.financialinstitution.persistence.impl.BankMyBatisDaoImpl;
import com.solvd.financialinstitution.persistence.impl.BranchDaoImpl;
import com.solvd.financialinstitution.service.BankService;

import java.util.List;
import java.util.Optional;

public class BankServiceImpl implements BankService {

    private final BankDao bankDao;
    private final BranchDao branchDao;
    private final AddressDao addressDao;

    public BankServiceImpl() {
        this(new BankMyBatisDaoImpl());
    }

    public BankServiceImpl(BankDao bankDao) {
        this(bankDao, new BranchDaoImpl(), new AddressDaoImpl());
    }

    public BankServiceImpl(BankDao bankDao, BranchDao branchDao, AddressDao addressDao) {
        this.bankDao = bankDao;
        this.branchDao = branchDao;
        this.addressDao = addressDao;
    }

    @Override
    public void create(Bank bank) {
        bankDao.create(bank);
    }

    @Override
    public Optional<Bank> getById(long id) {
        return bankDao.findById(id);
    }

    @Override
    public List<Bank> getAll() {
        return bankDao.findAll();
    }

    @Override
    public void update(Bank bank) {
        bankDao.update(bank);
    }

    @Override
    public void delete(long id) {
        bankDao.delete(id);
    }

    @Override
    public Bank createWithBranchesAndAddresses(Bank bank) {
        if (bank == null) {
            throw new IllegalArgumentException("bank must not be null");
        }

        bankDao.create(bank);

        List<Branch> branches = bank.getBranches();
        if (branches == null || branches.isEmpty()) {
            return bank;
        }

        for (Branch branch : branches) {
            if (branch == null) {
                continue;
            }

            branch.setBankId(bank.getId());

            Address branchAddress = branch.getAddress();
            if (branchAddress != null) {
                addressDao.create(branchAddress);
                branch.setAddressId(branchAddress.getId());
            }

            branchDao.create(branch);
        }

        return bank;
    }

    @Override
    public Optional<Bank> getByIdWithBranchesAndAddresses(long id) {
        return bankDao.findByIdWithBranchesAndAddresses(id);
    }

    @Override
    public List<Bank> getAllWithBranchesAndAddresses() {
        return bankDao.findAllWithBranchesAndAddresses();
    }
}
