package com.solvd.financialinstitution.model;

import com.solvd.financialinstitution.model.FinancialNetwork.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    private String panMasked;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate expiry;
    private boolean contactless;

    public Card() {
    }

    public String getPanMasked() {
        return panMasked;
    }

    public void setPanMasked(String panMasked) {
        this.panMasked = panMasked;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    public boolean isContactless() {
        return contactless;
    }

    public void setContactless(boolean contactless) {
        this.contactless = contactless;
    }
}
