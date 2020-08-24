package com.jokubas.lab1.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class CurrencyResponse {
    long id;
    String currency1;
    String currency2;
    BigDecimal amount;
    BigDecimal exchangeRate;
    BigDecimal result;

    CurrencyResponse(){}

    public CurrencyResponse(String ccy1, String ccy2, BigDecimal amount, BigDecimal exRate, BigDecimal result){
        this.currency1 = ccy1;
        this.currency2 = ccy2;
        this.amount = amount;
        this.exchangeRate = exRate;
        this.result = result;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setCurrency1(String name){
        this.currency1 = name;
    }

    public String getCurrency1(){
        return currency1;
    }

    public void setCurrency2(String name){
        this.currency2 = name;
    }

    public String getCurrency2(){
        return currency2;
    }

    public void setExchangeRate(BigDecimal rate){
        this.exchangeRate = rate;
    }

    public BigDecimal getExchangeRate(){
        return exchangeRate;
    }

    public void setResult(BigDecimal result){
        this.result = result;
    }

    public BigDecimal getResult(){
        return result;
    }


}