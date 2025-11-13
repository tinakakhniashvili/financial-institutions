package com.solvd.financialinstitution.persistence.dao;

import com.solvd.financialinstitution.domain.Bank;

import java.util.List;
import java.util.Optional;

public interface BankDao {

    void create(Bank bank);

    Optional<Bank> findById(long id);

    List<Bank> findAll();

    void update(Bank bank);

    void delete(long id);
}
