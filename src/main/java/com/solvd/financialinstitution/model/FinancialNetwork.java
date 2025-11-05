package com.solvd.financialinstitution.model;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "financialNetwork")
@XmlAccessorType(XmlAccessType.FIELD)
public class FinancialNetwork {
    private Long id;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate generatedOn;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime lastUpdated;
    @XmlElementWrapper(name = "banks")
    @XmlElement(name = "bank")
    private List<Bank> banks;

    public FinancialNetwork() {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public LocalDate getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDate generatedOn) {
        this.generatedOn = generatedOn;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public static class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
        public LocalDate unmarshal(String v) {
            return v == null ? null : LocalDate.parse(v);
        }

        public String marshal(LocalDate v) {
            return v == null ? null : v.toString();
        }
    }

    public static class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
        public LocalDateTime unmarshal(String v) {
            return v == null ? null : LocalDateTime.parse(v);
        }

        public String marshal(LocalDateTime v) {
            return v == null ? null : v.toString();
        }
    }
}
