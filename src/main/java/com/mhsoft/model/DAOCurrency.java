package com.mhsoft.model;

import javax.persistence.*;
@Entity
@Table(name="currency")
public class DAOCurrency {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;
}
