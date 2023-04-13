package com.mhsoft.controller;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.service.BankService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.service.UserDetailService;
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


    @RequestMapping(value = "/api/bankTrDetails", method = RequestMethod.POST)
    public String getUserBankTransactionDetails(@RequestBody DAOBank userDetails) {
        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(userDetails.getUserid());
        JSONObject userBankDetails = new JSONObject();
        if (bankDetailsOfUser == null) {
            userBankDetails.put("bankName", bankDetailsOfUser.getBankname());
            userBankDetails.put("bankCode", bankDetailsOfUser.getBankcode());
            userBankDetails.put("branchName", bankDetailsOfUser.getBranchname());
            userBankDetails.put("bankIns", bankDetailsOfUser.getBankinst());
            userBankDetails.put("bankAccNo", bankDetailsOfUser.getBankaccno());
        }
        userBankDetails.put("bankName", bankDetailsOfUser.getBankname());
        userBankDetails.put("bankCode", bankDetailsOfUser.getBankcode());
        userBankDetails.put("branchName", bankDetailsOfUser.getBranchname());
        userBankDetails.put("bankIns", bankDetailsOfUser.getBankinst());
        userBankDetails.put("bankAccNo", bankDetailsOfUser.getBankaccno());

        DAOTransaction [] userTrDetails =   trService.getTransactionsByUserId(userDetails.getUserid());
        String [] tempAgentDetails = userDetailService.getAgentDetailsByUserId(userDetails.getUserid());

        JSONObject jsonAgentDetalils = new JSONObject();

        if (tempAgentDetails == null) {
            jsonAgentDetalils.put("agentBankAccNo","null");
            jsonAgentDetalils.put("agentBankCode","null");
            jsonAgentDetalils.put("agentBankIns","null");
            jsonAgentDetalils.put("agentBankName","null");
            jsonAgentDetalils.put("agentBankBranch","null");
        } else {
            jsonAgentDetalils.put("agentBankAccNo",tempAgentDetails[0]);
            jsonAgentDetalils.put("agentBankCode",tempAgentDetails[1]);
            jsonAgentDetalils.put("agentBankIns",tempAgentDetails[2]);
            jsonAgentDetalils.put("agentBankName",tempAgentDetails[3]);
            jsonAgentDetalils.put("agentBankBranch",tempAgentDetails[4]);
        }
        JSONObject allDetails = new JSONObject();
        allDetails.put("userBankDetails",userBankDetails );
        allDetails.put("userTransDetails", userTrDetails);
        allDetails.put("userAgentDetails", jsonAgentDetalils);

        return allDetails.toString();
    }
}
