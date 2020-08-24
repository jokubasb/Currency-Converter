package com.jokubas.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jokubas.lab1.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long>{


    @Query("SELECT c FROM Currency c WHERE c.currency = ?1")
    Currency findCurrencyByName(String currency);
}

