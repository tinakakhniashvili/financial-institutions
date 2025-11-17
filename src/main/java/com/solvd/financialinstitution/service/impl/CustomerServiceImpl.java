package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Account;
import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.persistence.AccountDao;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.persistence.LoanDao;
import com.solvd.financialinstitution.persistence.impl.AccountDaoImpl;
import com.solvd.financialinstitution.persistence.impl.LoanDaoImpl;
import com.solvd.financialinstitution.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final AccountDao accountDao;
    private final LoanDao loanDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this(customerDao, new AccountDaoImpl(), new LoanDaoImpl());
    }

    public CustomerServiceImpl(CustomerDao customerDao,
                               AccountDao accountDao,
                               LoanDao loanDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
        this.loanDao = loanDao;
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
        Optional<Customer> customerOpt = customerDao.findById(id);
        if (customerOpt.isEmpty()) {
            return customerOpt;
        }

        Customer customer = customerOpt.get();

        // load accounts and loans via DAOs
        List<Account> accounts = accountDao.findByCustomerId(id);
        List<Loan> loans = loanDao.findByCustomerId(id);

        customer.setAccounts(accounts);
        customer.setLoans(loans);

        return Optional.of(customer);
    }

    @Override
    public List<Customer> getByCity(String city) {
        return customerDao.findByCity(city);
    }
}
