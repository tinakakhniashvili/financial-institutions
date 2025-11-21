package com.solvd.financialinstitution.strategy;

import com.solvd.financialinstitution.domain.Loan;

import java.math.BigDecimal;

public interface InterestCalculationStrategy {

    BigDecimal calculateInterest(Loan loan);
}
