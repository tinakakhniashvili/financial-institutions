package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void create(Customer customer) {
        customerDao.create(customer);
    }

    @Override
    public Optional<Customer> getById(long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerDao.findAll();
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(long id) {
        customerDao.delete(id);
    }

    @Override
    public Optional<Customer> getByIdWithAccountsAndLoans(long id) {
        return customerDao.findByIdWithAccountsAndLoans(id);
    }

    @Override
    public List<Customer> getByCity(String city) {
        return customerDao.findByCity(city);
    }
}
