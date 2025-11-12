package com.solvd.financialinstitution.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionType {

    private Long id;
    private String code;
    private String name;

    public TransactionType() {
    }

    public TransactionType(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionType)) return false;
        TransactionType that = (TransactionType) o;
        if (this.id != null && that.id != null) return Objects.equals(this.id, that.id);
        return Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : Objects.hash(code);
    }

    @Override
    public String toString() {
        return "TransactionType{id=" + id + ", code='" + code + "', name='" + name + "'}";
    }
}
