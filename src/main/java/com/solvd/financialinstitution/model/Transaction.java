package com.solvd.financialinstitution.model;

import com.solvd.financialinstitution.model.FinancialNetwork.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
    private String id;
    private BigDecimal amount;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime timestamp;
    private TransactionType kind;

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getKind() {
        return kind;
    }

    public void setKind(TransactionType kind) {
        this.kind = kind;
    }
}
