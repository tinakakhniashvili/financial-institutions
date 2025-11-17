package com.solvd.financialinstitution.service;

import com.solvd.financialinstitution.domain.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    void create(Loan loan);

    Optional<Loan> getById(long id);

    List<Loan> getAll();

    void update(Loan loan);

    void delete(long id);

    List<Loan> getByCustomerId(long customerId);
}
