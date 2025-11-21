package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.domain.Branch;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.impl.BankMyBatisDaoImpl;
import com.solvd.financialinstitution.service.BankService;
import com.solvd.financialinstitution.service.BranchService;

import java.util.List;
import java.util.Optional;

public class BankServiceImpl implements BankService {

    private final BankDao bankDao;
    private final BranchService branchService;

    public BankServiceImpl() {
        this(new BankMyBatisDaoImpl(), new BranchServiceImpl());
    }

    public BankServiceImpl(BankDao bankDao) {
        this(bankDao, new BranchServiceImpl());
    }

    public BankServiceImpl(BankDao bankDao, BranchService branchService) {
        this.bankDao = bankDao;
        this.branchService = branchService;
    }

    @Override
    public void create(Bank bank) {
        if (bank == null) {
            throw new IllegalArgumentException("bank must not be null");
        }

        bankDao.create(bank);

        List<Branch> branches = bank.getBranches();
        if (branches == null || branches.isEmpty()) {
            return;
        }

        for (Branch branch : branches) {
            if (branch == null) {
                continue;
            }
            branch.setBankId(bank.getId());
            branchService.create(branch);
        }
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
    public Optional<Bank> getByIdWithBranchesAndAddresses(long id) {
        return bankDao.findByIdWithBranchesAndAddresses(id);
    }

    @Override
    public List<Bank> getAllWithBranchesAndAddresses() {
        return bankDao.findAllWithBranchesAndAddresses();
    }
}
