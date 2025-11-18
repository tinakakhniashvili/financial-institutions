package com.solvd.financialinstitution.persistence.mybatis;

import com.solvd.financialinstitution.domain.Bank;

import java.util.List;

public interface BankMapper {

    void create(Bank bank);

    Bank findById(long id);

    List<Bank> findAll();

    void update(Bank bank);

    void delete(long id);

    Bank findByIdWithBranchesAndAddresses(long id);

    List<Bank> findAllWithBranchesAndAddresses();
}
