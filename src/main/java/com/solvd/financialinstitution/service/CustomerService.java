package com.solvd.financialinstitution.service;

import com.solvd.financialinstitution.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    void create(Customer customer);

    Optional<Customer> getById(long id);

    List<Customer> getAll();

    void update(Customer customer);

    void delete(long id);

    Optional<Customer> getByIdWithAccountsAndLoans(long id);

    List<Customer> getByCity(String city);
}
