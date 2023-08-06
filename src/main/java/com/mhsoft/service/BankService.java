package com.mhsoft.service;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.repo.BankRepo;
import com.mysql.cj.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class BankService {

    @Autowired
    private BankDao bankDao;

    @Autowired
    private BankRepo bankRepo;

    public DAOBank [] getBankDetailsByIUserId(int userid) {
        DAOBank bankdetails [] = bankRepo.getBankDetailsByUserId(userid);
        if (bankdetails == null) {
            return null;
        }
        return bankdetails;
    }

    public DAOBank updateBankDetailsByUserID(int userId, String bankAccNo) {

        DAOBank [] allBankDetails = getBankDetailsByIUserId(userId);
        DAOBank newDefault = new DAOBank();
        boolean isDefaultFound =false;
        boolean isOldFefaultFound = false;

        for (int i =0; i <allBankDetails.length; i++) {
            if (allBankDetails[i].isDefaultacc() ==true ) {
                DAOBank newBankDetails = new DAOBank();
                newBankDetails.setId(allBankDetails[i].getId());
                newBankDetails.setAccountNo(allBankDetails[i].getAccountNo());
                newBankDetails.setBranchname(allBankDetails[i].getBranchname());
                newBankDetails.setBankName(allBankDetails[i].getBankName());
                newBankDetails.setInstructions(allBankDetails[i].getInstructions());
                newBankDetails.setLatest(allBankDetails[i].isLatest());
                newBankDetails.setBankCode(allBankDetails[i].getBankCode());
                newBankDetails.setUserid(allBankDetails[i].getUserid());
                newBankDetails.setValidTo(allBankDetails[i].getValidTo());
                newBankDetails.setLastUpdatedTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)); //TODO :
                newBankDetails.setDefaultacc(false);
                bankRepo.save(newBankDetails);
                isOldFefaultFound = true;
            }
            if (allBankDetails[i].getAccountNo().equals(bankAccNo) ) {
                newDefault.setId(allBankDetails[i].getId());
                newDefault.setAccountNo(allBankDetails[i].getAccountNo());
                newDefault.setBranchname(allBankDetails[i].getBranchname());
                newDefault.setBankName(allBankDetails[i].getBankName());
                newDefault.setInstructions(allBankDetails[i].getInstructions());
                newDefault.setLatest(allBankDetails[i].isLatest());
                newDefault.setBankCode(allBankDetails[i].getBankCode());
                newDefault.setUserid(allBankDetails[i].getUserid());
                newDefault.setValidTo(allBankDetails[i].getValidTo());
                newDefault.setLastUpdatedTime(allBankDetails[i].getLastUpdatedTime());
                newDefault.setDefaultacc(true);
                bankRepo.save(newDefault);
                isDefaultFound = true;
            }
        }
        return   newDefault;
    }
}
