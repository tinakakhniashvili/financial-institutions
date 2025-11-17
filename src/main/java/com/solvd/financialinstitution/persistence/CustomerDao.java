package com.solvd.financialinstitution.persistence;

import com.solvd.financialinstitution.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    void create(Customer customer);

    Optional<Customer> findById(long id);

    List<Customer> findAll();

    void update(Customer customer);

    void delete(long id);

    Optional<Customer> findByIdWithAccountsAndLoans(long id);

    List<Customer> findByCity(String city);
}
