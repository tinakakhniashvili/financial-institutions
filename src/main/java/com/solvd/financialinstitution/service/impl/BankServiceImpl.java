package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.persistence.dao.BankDao;
import com.solvd.financialinstitution.service.BankService;

import java.util.List;
import java.util.Optional;

public class BankServiceImpl implements BankService {

    private final BankDao bankDao;

    public BankServiceImpl(BankDao bankDao) {
        this.bankDao = bankDao;
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
}
