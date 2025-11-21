package com.solvd.financialinstitution.listener;

import com.solvd.financialinstitution.domain.Loan;

public class LoggingLoanCreatedListener implements LoanCreatedListener {

    @Override
    public void onLoanCreated(Loan loan) {
        System.out.println("Loan created event: id=" + loan.getId()
                + ", principal=" + loan.getPrincipal());
    }
}
