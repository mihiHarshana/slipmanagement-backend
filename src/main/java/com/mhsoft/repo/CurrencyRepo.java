package com.mhsoft.repo;

import com.mhsoft.model.DAOCurrency;
import org.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CurrencyRepo  extends JpaRepository<DAOCurrency, Integer> {
    @Query(value = "SELECT currency from currency " , nativeQuery = true)
    String [] getAllCurrencies();

}
