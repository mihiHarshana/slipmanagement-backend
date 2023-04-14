package com.mhsoft.controller;

import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.service.BankService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.service.UserDetailService;
import org.json.JSONArray;
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

        JSONObject joMain = new JSONObject();



        DAOBank [] bankdetails = bankService.getBankDetailsByIUserId(userDetails.getUserid());
JSONArray   jsonArray = new JSONArray();
JSONObject jo_temp = new JSONObject();

        for (int i = 0; i < bankdetails.length; i++) {
            jo_temp.put("", bankdetails[i].getBankname());
        }



/*        JSONObject jo = new JSONObject();
        jo.put("bank details", bankdetails);*/

        JSONObject jo_cbd = new JSONObject();
        jo_cbd.put("header", "Customer Bank Details");
        jo_cbd.put("body",jsonArray.getString(0));



        joMain.put("customerBanKDetails",jo_cbd);


        return joMain.toString();
    }

    private String generateJsonObj(String key, String value) {
        JSONObject temp = new JSONObject();
       return  temp.put(key, value).toString();
    }
/*        DAOBank [] bankDetailsOfUser = bankService.getBankDetailsByIUserId(userDetails.getUserid());
        JSONObject userBankDetails = new JSONObject();
        if (bankDetailsOfUser == null) {
            userBankDetails.put("bankName", "null");
            userBankDetails.put("bankCode", "null");
            userBankDetails.put("branchName", "null");
            userBankDetails.put("bankIns", "null");
            userBankDetails.put("bankAccNo", "null");
        }

        System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOO is : " + bankDetailsOfUser.length);
*//*        userBankDetails.put("bankName", bankDetailsOfUser.getBankname());
        userBankDetails.put("bankCode", bankDetailsOfUser.getBankcode());
        userBankDetails.put("branchName", bankDetailsOfUser.getBranchname());
        userBankDetails.put("bankIns", bankDetailsOfUser.getBankinst());
        userBankDetails.put("bankAccNo", bankDetailsOfUser.getBankaccno());*//*

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
*//*        allDetails.put("userBankDetails",userBankDetails );
        allDetails.put("userTransDetails", userTrDetails);
        allDetails.put("userAgentDetails", jsonAgentDetalils);*//*


        JSONObject j_customerBankDetails = new JSONObject();
        j_customerBankDetails.put("header","Customer Bank Details");
        j_customerBankDetails.put("body", bankDetailsOfUser);


allDetails.put("customerBanKDetails ",j_customerBankDetails);


        return allDetails.toString();
    }*/
}
