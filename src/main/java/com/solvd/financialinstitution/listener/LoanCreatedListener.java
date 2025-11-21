package com.solvd.financialinstitution.listener;

import com.solvd.financialinstitution.domain.Loan;

public interface LoanCreatedListener {

    void onLoanCreated(Loan loan);
}
