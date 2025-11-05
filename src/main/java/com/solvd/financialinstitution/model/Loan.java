package com.solvd.financialinstitution.model;

import com.solvd.financialinstitution.model.FinancialNetwork.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Loan {
    private Long id;
    private BigDecimal principal;
    private BigDecimal rate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate start;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate end;

    public Loan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
