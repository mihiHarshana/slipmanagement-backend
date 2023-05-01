package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.DAOUser;
import com.mhsoft.model.IDAOTransaction;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.service.BankService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.service.UserDetailService;
import com.mhsoft.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepo userRepo;


    @RequestMapping(value = "/api/customer-details", method = RequestMethod.POST)
    public String getUserBankTransactionDetails(@RequestHeader String Authorization ) {
        // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        int USER_ID = 0;
        String token = Authorization.substring(7);


        System.out.println("Tokent ============ : " + token);

        String username = jwtTokenUtil.getUsernameFromToken(token);
        // System.out.println(jwtTokenUtil.isTokenExpired(token));


        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        // System.out.println("user name ============ : " + username);
        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(USER_ID);
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


        DAOTransaction[] userTrDetails = trService.getTransactionsByUserId(USER_ID);
        String[] tempAgentDetails = userDetailService.getAgentDetailsByUserId(USER_ID);

        JSONObject jsonAgentDetalils = new JSONObject();

        if (tempAgentDetails == null) {
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_ACC_NO), "null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_CODE), "null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_INS), "null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_NAME), "null");
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_BRANCH), "null");
        } else {
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_ACC_NO), tempAgentDetails[0]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_CODE), tempAgentDetails[1]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_INS), tempAgentDetails[2]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_NAME), tempAgentDetails[3]);
            jsonAgentDetalils.put(utils.concatinateAgent(Utils.BANK_BRANCH), tempAgentDetails[4]);
        }
        JSONObject allDetails = new JSONObject();
        allDetails.put("userBankDetails", userBankDetails);
        allDetails.put("userTransDetails", userTrDetails);
        allDetails.put("userAgentDetails", jsonAgentDetalils);
        return allDetails.toString();
    }


    @RequestMapping(value = "/api/callcenter-agent-details", method = RequestMethod.POST)
    public String getCallCenterAgentBankTransactionDetails(@RequestHeader String Authorization) throws SQLException {
        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(USER_ID);
        JSONObject userBankDetails = new JSONObject();
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

        String[] deposits = trService.getTransactionsByType(Utils.TRTRYOEDEPOSIT);
        String  [] withdrawals = trService.getTransactionsByType(Utils.TRTYPEWIDTHDRAW);
        JSONArray array = new JSONArray();
        for (int i = 0; i < deposits.length; i++) {
            System.out.println("Deposit --- length :" + i);
            String[] temp_deposit = deposits[i].split(",");
            JSONObject jo_deposit = new JSONObject();
            jo_deposit.put("userId", temp_deposit[0]);
            jo_deposit.put("trId", temp_deposit[1]);
            jo_deposit.put("trAmount", temp_deposit[2]);
            jo_deposit.put("trDateTime", temp_deposit[3]);
            jo_deposit.put("trStatus", temp_deposit[4]);
            jo_deposit.put("trType", temp_deposit[5]);
            jo_deposit.put("userName", temp_deposit[6]);
            array.put(i, jo_deposit);
        }

        JSONArray array_withdrawal = new JSONArray();
        for (int i = 0; i < withdrawals.length; i++) {
            System.out.println("withdrawals --- length :" + i);
            String[] temp_withdrawal = deposits[i].split(",");
            JSONObject jo_withdrawal = new JSONObject();
            jo_withdrawal.put("userId", temp_withdrawal[0]);
            jo_withdrawal.put("trId", temp_withdrawal[1]);
            jo_withdrawal.put("trAmount", temp_withdrawal[2]);
            jo_withdrawal.put("trDateTime", temp_withdrawal[3]);
            jo_withdrawal.put("trStatus", temp_withdrawal[4]);
            jo_withdrawal.put("trType", temp_withdrawal[5]);
            jo_withdrawal.put("userName", temp_withdrawal[6]);
            array_withdrawal.put(i, jo_withdrawal);
        }

        JSONObject jo = new JSONObject();
            jo.put("callCenterAgentBanKDetails", userBankDetails);
            jo.put("depositData", array);
            jo.put("withdrawalData", array_withdrawal);

        return jo.toString();
    }

    @RequestMapping(value = "/api/agent-details-per-customer", method = RequestMethod.POST)
    public String getAgentBankTransactionDetails(@RequestHeader String Authorization)  {
        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(USER_ID);
        JSONObject userBankDetails = new JSONObject();
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

        String[] deposits = trService.getTransactionsByAgentId(USER_ID);
        String  [] withdrawals = trService.getTransactionsByAgentId(USER_ID);

        JSONArray array = new JSONArray();
        for (int i = 0; i < deposits.length; i++) {
            System.out.println("Deposit --- length :" + i);
            String[] temp_deposit = deposits[i].split(",");
            JSONObject jo_deposit = new JSONObject();
            jo_deposit.put("userId", temp_deposit[0]);
            jo_deposit.put("trId", temp_deposit[1]);
            jo_deposit.put("trAmount", temp_deposit[2]);
            jo_deposit.put("trDateTime", temp_deposit[3]);
            jo_deposit.put("trStatus", temp_deposit[4]);
            jo_deposit.put("trType", temp_deposit[5]);
            jo_deposit.put("userName", temp_deposit[6]);
            array.put(i, jo_deposit);
        }

        JSONArray array_withdrawal = new JSONArray();
        for (int i = 0; i < withdrawals.length; i++) {
            System.out.println("withdrawals --- length :" + i);
            String[] temp_withdrawal = deposits[i].split(",");
            JSONObject jo_withdrawal = new JSONObject();
            jo_withdrawal.put("userId", temp_withdrawal[0]);
            jo_withdrawal.put("trId", temp_withdrawal[1]);
            jo_withdrawal.put("trAmount", temp_withdrawal[2]);
            jo_withdrawal.put("trDateTime", temp_withdrawal[3]);
            jo_withdrawal.put("trStatus", temp_withdrawal[4]);
            jo_withdrawal.put("trType", temp_withdrawal[5]);
            jo_withdrawal.put("userName", temp_withdrawal[6]);
            array_withdrawal.put(i, jo_withdrawal);
        }

        JSONObject jo = new JSONObject();
        jo.put("agentBanKDetails", userBankDetails);
        jo.put("depositData", array);
        jo.put("withdrawalData", array_withdrawal);
        return jo.toString();
    }


}
