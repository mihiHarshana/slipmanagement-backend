package com.mhsoft.controller;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.service.BankService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.service.UserDetailService;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailsController {
    @Autowired
    BankDao bankDao;
    @Autowired
    BankService bankService;
    @Autowired
    TransactionService trService;
    @Autowired
    UserDetailService userDetailService;


    @RequestMapping(value = "/api/customer-details", method = RequestMethod.POST)
    public String getUserBankTransactionDetails(@RequestBody DAOBank userDetails) {
        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(userDetails.getUserid());
        JSONObject userBankDetails = new JSONObject();
        Utils utils = Utils.getInstance();

        if (bankDetailsOfUser == null) {
            userBankDetails.put(Utils.BANK_NAME, "null");
            userBankDetails.put(Utils.BANK_CODE, "null");
            userBankDetails.put(Utils.BANK_BRANCH, "null");
            userBankDetails.put(Utils.BANK_INS, "null");
            userBankDetails.put(Utils.BANK_ACC_NO, "null");
        } else {
            userBankDetails.put(Utils.BANK_NAME, bankDetailsOfUser.getBankname());
            userBankDetails.put(Utils.BANK_CODE, bankDetailsOfUser.getBankcode());
            userBankDetails.put(Utils.BANK_BRANCH, bankDetailsOfUser.getBranchname());
            userBankDetails.put(Utils.BANK_INS, bankDetailsOfUser.getBankinst());
            userBankDetails.put(Utils.BANK_ACC_NO, bankDetailsOfUser.getBankaccno());
        }


        DAOTransaction [] userTrDetails =   trService.getTransactionsByUserId(userDetails.getUserid());
        String [] tempAgentDetails = userDetailService.getAgentDetailsByUserId(userDetails.getUserid());

        JSONObject jsonAgentDetalils = new JSONObject();

        if (tempAgentDetails == null) {
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_ACC_NO),"null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_CODE),"null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_INS),"null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_NAME),"null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_BRANCH),"null");
        } else {
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_ACC_NO),tempAgentDetails[0]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_CODE),tempAgentDetails[1]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_INS),tempAgentDetails[2]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_NAME),tempAgentDetails[3]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_BRANCH),tempAgentDetails[4]);
        }
        JSONObject allDetails = new JSONObject();
        allDetails.put("userBankDetails",userBankDetails );
        allDetails.put("userTransDetails", userTrDetails);
        allDetails.put("userAgentDetails", jsonAgentDetalils);
        return allDetails.toString();
    }
}
