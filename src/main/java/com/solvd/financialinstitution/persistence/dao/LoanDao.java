package com.solvd.financialinstitution.persistence.dao;

import com.solvd.financialinstitution.domain.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDao {

    void create(Loan loan);

    Optional<Loan> findById(long id);

    List<Loan> findAll();

    void update(Loan loan);

    void delete(long id);
}
