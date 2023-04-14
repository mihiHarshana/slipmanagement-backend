package com.mhsoft.service;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.repo.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    private BankDao bankDao;

    @Autowired
    private BankRepo bankRepo;

    public DAOBank [] getBankDetailsByIUserId(int userid) {
        DAOBank [] bankdetails = bankRepo.getBankDetailsByUserId(userid);
        if (bankdetails == null) {
            return null;
        }
        return bankdetails;
    }
}
