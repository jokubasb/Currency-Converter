package com.jokubas.lab1.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "currencies")

public class Currency {
    private long id;
    private String currency;
    private BigDecimal rate;

    public Currency(){

    }

    public Currency(String currency, BigDecimal rate){
        this.currency = currency;
        this.rate = rate;
    }

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    

    @Column(name = "currency", nullable = false)
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "rate", nullable = false)
    public BigDecimal getRate() {
        return rate;
    }
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

	public Currency orElseThrow(Object object) {
		return null;
	}

}
