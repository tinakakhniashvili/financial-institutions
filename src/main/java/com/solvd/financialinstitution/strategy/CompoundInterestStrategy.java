package com.solvd.financialinstitution.strategy;

import com.solvd.financialinstitution.domain.Loan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

public class CompoundInterestStrategy implements InterestCalculationStrategy {

    private final int compoundsPerYear;

    public CompoundInterestStrategy(int compoundsPerYear) {
        this.compoundsPerYear = compoundsPerYear;
    }

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

        BigDecimal n = BigDecimal.valueOf(compoundsPerYear).multiply(years);
        BigDecimal onePlusRdivN = BigDecimal.ONE.add(
                loan.getRate().divide(BigDecimal.valueOf(compoundsPerYear), 8, RoundingMode.HALF_UP)
        );
        BigDecimal factor = onePlusRdivN.pow(n.intValue());

        return loan.getPrincipal()
                .multiply(factor.subtract(BigDecimal.ONE))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
