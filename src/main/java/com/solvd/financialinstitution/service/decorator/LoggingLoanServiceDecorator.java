package com.solvd.financialinstitution.service.decorator;

import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.listener.LoanCreatedListener;
import com.solvd.financialinstitution.service.LoanService;

import java.util.List;
import java.util.Optional;

public class LoggingLoanServiceDecorator implements LoanService {

    private final LoanService delegate;

    public LoggingLoanServiceDecorator(LoanService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void create(Loan loan) {
        System.out.println("Creating loan: " + loan);
        delegate.create(loan);
    }

    @Override
    public Optional<Loan> getById(long id) {
        System.out.println("Fetching loan by id=" + id);
        return delegate.getById(id);
    }

    @Override
    public List<Loan> getAll() {
        System.out.println("Fetching all loans");
        return delegate.getAll();
    }

    @Override
    public void update(Loan loan) {
        System.out.println("Updating loan id=" + loan.getId());
        delegate.update(loan);
    }

    @Override
    public void delete(long id) {
        System.out.println("Deleting loan id=" + id);
        delegate.delete(id);
    }

    @Override
    public List<Loan> getByCustomerId(long customerId) {
        System.out.println("Fetching loans for customerId=" + customerId);
        return delegate.getByCustomerId(customerId);
    }

    @Override
    public void addLoanCreatedListener(LoanCreatedListener listener) {
        delegate.addLoanCreatedListener(listener);
    }

    @Override
    public void removeLoanCreatedListener(LoanCreatedListener listener) {
        delegate.removeLoanCreatedListener(listener);
    }
}
