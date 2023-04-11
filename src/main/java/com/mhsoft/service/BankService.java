package com.mhsoft.service;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.repo.BankRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    private BankDao bankDao;

    @Autowired
    private BankRepo bankRepo;

    public String getBankDetailsByIUserId(Integer userid) {

        DAOBank bank = bankRepo.getBankDetailsByUserId(userid);
        if (bank == null) {
            System.out.println("bank" + bank.toString());
            return "No bank details available";
        }

        return bank.toString();
    }

}
