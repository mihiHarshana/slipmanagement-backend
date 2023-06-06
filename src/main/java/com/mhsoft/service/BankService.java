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

        System.out.println("Trying to find and upate default account");
        for (int i =0; i <allBankDetails.length; i++) {
            System.out.println(allBankDetails[i].isDefaultacc());
            if (allBankDetails[i].isDefaultacc() ==true ) {
                System.out.println("OLD default account found");
                DAOBank newBankDetails = new DAOBank();
                newBankDetails.setId(allBankDetails[i].getId());
                newBankDetails.setBankaccno(allBankDetails[i].getBankaccno());
                newBankDetails.setBranchname(allBankDetails[i].getBranchname());
                newBankDetails.setBankname(allBankDetails[i].getBankname());
                newBankDetails.setBankinst(allBankDetails[i].getBankinst());
                newBankDetails.setLatest(allBankDetails[i].isLatest());
                newBankDetails.setBankcode(allBankDetails[i].getBankcode());
                newBankDetails.setUserid(allBankDetails[i].getUserid());
                newBankDetails.setDefaultacc(false);
                bankRepo.save(newBankDetails);
                isOldFefaultFound = true;
            }
            if (allBankDetails[i].getBankaccno().equals(bankAccNo) ) {
                System.out.println("New  default account found");
                newDefault.setId(allBankDetails[i].getId());
                newDefault.setBankaccno(allBankDetails[i].getBankaccno());
                newDefault.setBranchname(allBankDetails[i].getBranchname());
                newDefault.setBankname(allBankDetails[i].getBankname());
                newDefault.setBankinst(allBankDetails[i].getBankinst());
                newDefault.setLatest(allBankDetails[i].isLatest());
                newDefault.setBankcode(allBankDetails[i].getBankcode());
                newDefault.setUserid(allBankDetails[i].getUserid());
                newDefault.setDefaultacc(true);
                bankRepo.save(newDefault);
                isDefaultFound = true;
            }
        }
        System.out.println("Compelted replacing default and old account");
        return   newDefault;
    }
}
