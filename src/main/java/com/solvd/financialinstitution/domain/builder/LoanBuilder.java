package com.solvd.financialinstitution.domain.builder;

import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.strategy.InterestCalculationStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanBuilder {

    private Long id;
    private BigDecimal principal;
    private BigDecimal rate;
    private LocalDate start;
    private LocalDate end;
    private InterestCalculationStrategy interestStrategy;

    public LoanBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public LoanBuilder withPrincipal(BigDecimal principal) {
        this.principal = principal;
        return this;
    }

    public LoanBuilder withRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public LoanBuilder withStart(LocalDate start) {
        this.start = start;
        return this;
    }

    public LoanBuilder withEnd(LocalDate end) {
        this.end = end;
        return this;
    }

    public LoanBuilder withInterestStrategy(InterestCalculationStrategy interestStrategy) {
        this.interestStrategy = interestStrategy;
        return this;
    }

    public Loan build() {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setPrincipal(principal);
        loan.setRate(rate);
        loan.setStart(start);
        loan.setEnd(end);
        loan.setInterestStrategy(interestStrategy);
        return loan;
    }
}
