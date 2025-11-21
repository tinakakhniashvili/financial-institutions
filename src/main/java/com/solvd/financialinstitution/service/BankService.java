package com.solvd.financialinstitution.service;

import com.solvd.financialinstitution.domain.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {

    void create(Bank bank);

    Optional<Bank> getById(long id);

    List<Bank> getAll();

    void update(Bank bank);

    void delete(long id);

    Optional<Bank> getByIdWithBranchesAndAddresses(long id);

    List<Bank> getAllWithBranchesAndAddresses();
}
