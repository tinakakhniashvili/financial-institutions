package com.solvd.financialinstitution.persistence.factory;

import com.solvd.financialinstitution.persistence.AddressDao;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.BranchDao;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.persistence.LoanDao;
import com.solvd.financialinstitution.persistence.impl.AddressDaoImpl;
import com.solvd.financialinstitution.persistence.impl.BankDaoImpl;
import com.solvd.financialinstitution.persistence.impl.BranchDaoImpl;
import com.solvd.financialinstitution.persistence.impl.CustomerDaoImpl;
import com.solvd.financialinstitution.persistence.impl.LoanDaoImpl;

public class JdbcDaoFactory implements DaoFactory {

    @Override
    public BankDao createBankDao() {
        return new BankDaoImpl();
    }

    @Override
    public CustomerDao createCustomerDao() {
        return new CustomerDaoImpl();
    }

    @Override
    public BranchDao createBranchDao() {
        return new BranchDaoImpl();
    }

    @Override
    public AddressDao createAddressDao() {
        return new AddressDaoImpl();
    }

    @Override
    public LoanDao createLoanDao() {
        return new LoanDaoImpl();
    }
}
