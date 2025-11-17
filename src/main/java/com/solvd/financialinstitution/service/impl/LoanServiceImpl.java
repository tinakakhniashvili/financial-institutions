package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.persistence.LoanDao;
import com.solvd.financialinstitution.service.LoanService;

import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao;

    public LoanServiceImpl(LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    @Override
    public void create(Loan loan) {
        loanDao.create(loan);
    }

    @Override
    public Optional<Loan> getById(long id) {
        return loanDao.findById(id);
    }

    @Override
    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    @Override
    public void update(Loan loan) {
        loanDao.update(loan);
    }

    @Override
    public void delete(long id) {
        loanDao.delete(id);
    }

    @Override
    public List<Loan> getByCustomerId(long customerId) {
        return loanDao.findByCustomerId(customerId);
    }
}
