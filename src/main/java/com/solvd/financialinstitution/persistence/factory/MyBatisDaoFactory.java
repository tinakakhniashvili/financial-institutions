package com.solvd.financialinstitution.persistence.factory;

import com.solvd.financialinstitution.persistence.AddressDao;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.BranchDao;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.persistence.LoanDao;
import com.solvd.financialinstitution.persistence.impl.AddressMyBatisDaoImpl;
import com.solvd.financialinstitution.persistence.impl.BankMyBatisDaoImpl;
import com.solvd.financialinstitution.persistence.impl.BranchMyBatisDaoImpl;
import com.solvd.financialinstitution.persistence.impl.CustomerMyBatisDaoImpl;
import com.solvd.financialinstitution.persistence.impl.LoanMyBatisDaoImpl;

public class MyBatisDaoFactory implements DaoFactory {

    @Override
    public BankDao createBankDao() {
        return new BankMyBatisDaoImpl();
    }

    @Override
    public CustomerDao createCustomerDao() {
        return new CustomerMyBatisDaoImpl();
    }

    @Override
    public BranchDao createBranchDao() {
        return new BranchMyBatisDaoImpl();
    }

    @Override
    public AddressDao createAddressDao() {
        return new AddressMyBatisDaoImpl();
    }

    @Override
    public LoanDao createLoanDao() {
        return new LoanMyBatisDaoImpl();
    }
}
