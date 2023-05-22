package com.mhsoft.repo;

import com.mhsoft.model.DAOCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CurrencyRepo  extends JpaRepository<DAOCurrency, Integer> {
    @Query(value = "SELECT * from currency " , nativeQuery = true)
    DAOCurrency[] getAllCurrencies();

}