package com.mhsoft.controller;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.service.BankService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/api/bankTrDetails", method = RequestMethod.POST)
    public String getUserBankTransactionDetails(@RequestBody DAOBank userDetails) {
        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(userDetails.getUserid());
        //   String bankDetailOfAgent =
        JSONObject userBankDetails = new JSONObject();
        if (bankDetailsOfUser == null) {
            userBankDetails.put("bankname", bankDetailsOfUser.getBankname());
            userBankDetails.put("bankcode", bankDetailsOfUser.getBankcode());
            userBankDetails.put("branchname", bankDetailsOfUser.getBranchname());
            userBankDetails.put("bandinstructions", bankDetailsOfUser.getBankinst());
            userBankDetails.put("bankaccno", bankDetailsOfUser.getBankaccno());
/*            Utils utils = new Utils();
            return utils.JsonMessage("Bank details not availale for the user", HttpStatus.NOT_ACCEPTABLE);*/
        }
        //JSONObject userBankDetails = new JSONObject();
        userBankDetails.put("bankname", bankDetailsOfUser.getBankname());
        userBankDetails.put("bankcode", bankDetailsOfUser.getBankcode());
        userBankDetails.put("branchname", bankDetailsOfUser.getBranchname());
        userBankDetails.put("bandinstructions", bankDetailsOfUser.getBankinst());
        userBankDetails.put("bankaccno", bankDetailsOfUser.getBankaccno());


        DAOTransaction [] trOfUser =   trService.getTransactionsByUserId(userDetails.getUserid());

        DAOTransaction[] userTrDetails = trOfUser;

        JSONObject userAgentDetails = new JSONObject();

        userAgentDetails.put("testagent", "Agent0001Test");

        JSONObject allDetails = new JSONObject();

        allDetails.put("userBankDetails",userBankDetails );
        allDetails.put("userTransDetails", userTrDetails);
        allDetails.put("userAgentDetails", userAgentDetails);

        return allDetails.toString();

    }
}
