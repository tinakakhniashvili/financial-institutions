package com.solvd.financialinstitution.strategy;

import com.solvd.financialinstitution.domain.Loan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

public class SimpleInterestStrategy implements InterestCalculationStrategy {

    @Override
    public BigDecimal calculateInterest(Loan loan) {
        if (loan.getPrincipal() == null
                || loan.getRate() == null
                || loan.getStart() == null
                || loan.getEnd() == null) {
            return BigDecimal.ZERO;
        }

        long days = ChronoUnit.DAYS.between(loan.getStart(), loan.getEnd());
        BigDecimal years = BigDecimal.valueOf(days)
                .divide(BigDecimal.valueOf(365), 8, RoundingMode.HALF_UP);

        return loan.getPrincipal()
                .multiply(loan.getRate())
                .multiply(years)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
