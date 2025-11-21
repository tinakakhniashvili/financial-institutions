package com.solvd.financialinstitution.service.proxy;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.service.BankService;

import java.util.List;
import java.util.Optional;

public class SecurityBankServiceProxy implements BankService {

    private final BankService delegate;
    private final boolean deletionAllowed;

    public SecurityBankServiceProxy(BankService delegate, boolean deletionAllowed) {
        this.delegate = delegate;
        this.deletionAllowed = deletionAllowed;
    }

    @Override
    public void create(Bank bank) {
        delegate.create(bank);
    }

    @Override
    public Optional<Bank> getById(long id) {
        return delegate.getById(id);
    }

    @Override
    public List<Bank> getAll() {
        return delegate.getAll();
    }

    @Override
    public void update(Bank bank) {
        delegate.update(bank);
    }

    @Override
    public void delete(long id) {
        if (!deletionAllowed) {
            System.out.println("Bank deletion is blocked by security policy");
            return;
        }
        delegate.delete(id);
    }

    @Override
    public Optional<Bank> getByIdWithBranchesAndAddresses(long id) {
        return delegate.getByIdWithBranchesAndAddresses(id);
    }

    @Override
    public List<Bank> getAllWithBranchesAndAddresses() {
        return delegate.getAllWithBranchesAndAddresses();
    }
}
