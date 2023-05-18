package com.mhsoft.service;
import com.mhsoft.model.DAOCurrency;
import com.mhsoft.repo.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepo currencyRepo;

    public DAOCurrency [] getAllCurrency() {
        DAOCurrency allCurrency [] = currencyRepo.getAllCurrencies();
        if (allCurrency == null) {
            return null;
        }
        return allCurrency;
    }
}
