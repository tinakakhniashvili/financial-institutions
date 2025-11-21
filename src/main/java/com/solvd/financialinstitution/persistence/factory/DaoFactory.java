package com.solvd.financialinstitution.persistence.factory;

import com.solvd.financialinstitution.persistence.AddressDao;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.BranchDao;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.persistence.LoanDao;

public interface DaoFactory {

    BankDao createBankDao();

    CustomerDao createCustomerDao();

    BranchDao createBranchDao();

    AddressDao createAddressDao();

    LoanDao createLoanDao();
}
