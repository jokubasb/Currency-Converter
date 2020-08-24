package com.jokubas.lab1.repository;

import com.jokubas.lab1.model.CurrencyResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<CurrencyResponse, Long>{

}