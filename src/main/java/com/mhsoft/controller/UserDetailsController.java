package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.dao.BankDao;
import com.mhsoft.model.*;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.service.*;
import com.mhsoft.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
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

    @Autowired
    AgentUserService agentUserService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    AgentSystemService agentSystemService;

    @PostMapping("/customer-details")
    // @RequestMapping(value = "/api/customer-details", method = RequestMethod.POST)
    public String getUserBankTransactionDetails(@RequestHeader String Authorization) {
        // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);

        String username = jwtTokenUtil.getUsernameFromToken(token);
        // System.out.println(jwtTokenUtil.isTokenExpired(token));


        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();


        // System.out.println("user name ============ : " + username);
        DAOBank bankDetailsOfUser[] = bankService.getBankDetailsByIUserId(USER_ID);
        JSONObject userBankDetails = new JSONObject();

        if (bankDetailsOfUser == null) {
            userBankDetails.put(Utils.BANK_NAME, "null");
            userBankDetails.put(Utils.BANK_CODE, "null");
            userBankDetails.put(Utils.BANK_BRANCH, "null");
            userBankDetails.put(Utils.BANK_INS, "null");
            userBankDetails.put(Utils.BANK_ACC_NO, "null");
        } else {
            String[] bankacounts = new String[bankDetailsOfUser.length];

            for (int i = 0; i < bankacounts.length; i++) {
                bankacounts[i] = bankDetailsOfUser[i].getBankaccno();
            }

            for (int i = 0; i < bankacounts.length; i++) {
                if (bankDetailsOfUser[i].isDefaultacc()) {
                    userBankDetails.put(Utils.BANK_NAME, bankDetailsOfUser[i].getBankname());
                    userBankDetails.put(Utils.BANK_CODE, bankDetailsOfUser[i].getBankcode());
                    userBankDetails.put(Utils.BANK_BRANCH, bankDetailsOfUser[i].getBranchname());
                    userBankDetails.put(Utils.BANK_INS, bankDetailsOfUser[i].getBankinst());
                    userBankDetails.put(Utils.BANK_ACC_NO, bankDetailsOfUser[i].getBankaccno());
                }
            }
        }




/*
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
*/


        //    DAOTransaction[] userTrDetails = trService.getTransactionsByUserId(USER_ID);

        DAOTransaction[] userTrDetails = trService.getTransactionsByUserId(USER_ID);

        JSONArray tr_array = setTrData(userTrDetails);


        DAOAgentUser agentdetails = agentUserService.getAgentIdByUserID(USER_ID);

        DAOBank[] tempAgentDetails = bankService.getBankDetailsByIUserId(agentdetails.getAgentid());
        String[] bankacounts = null;
        JSONObject jsonAgentDetalils = new JSONObject();

        if (tempAgentDetails == null) {
            jsonAgentDetalils.put(Utils.BANK_NAME, "null");
            jsonAgentDetalils.put(Utils.BANK_CODE, "null");
            jsonAgentDetalils.put(Utils.BANK_BRANCH, "null");
            jsonAgentDetalils.put(Utils.BANK_INS, "null");
            jsonAgentDetalils.put(Utils.BANK_ACC_NO, "null");
        } else {
            bankacounts = new String[tempAgentDetails.length];

            for (int i = 0; i < bankacounts.length; i++) {
                bankacounts[i] = tempAgentDetails[i].getBankaccno();
            }

            for (int i = 0; i < bankacounts.length; i++) {
                if (tempAgentDetails[i].isDefaultacc()) {
                    jsonAgentDetalils.put(Utils.BANK_NAME, tempAgentDetails[i].getBankname());
                    jsonAgentDetalils.put(Utils.BANK_CODE, tempAgentDetails[i].getBankcode());
                    jsonAgentDetalils.put(Utils.BANK_BRANCH, tempAgentDetails[i].getBranchname());
                    jsonAgentDetalils.put(Utils.BANK_INS, tempAgentDetails[i].getBankinst());
                    jsonAgentDetalils.put(Utils.BANK_ACC_NO, tempAgentDetails[i].getBankaccno());
                }
            }
        }

        //Load Currencies
        DAOCurrency ar_currency[] = currencyService.getAllCurrency();
        DAOAgentSystem ar_agentSystem[] = agentSystemService.getAgentSystemByAgentId(agentdetails.getAgentid());

        JSONObject jo_userDetails = new JSONObject();
        jo_userDetails.put("name", username);
        // jo_userDetails.put("userId",USER_ID);
        jo_userDetails.put("playerUser", "not required");

        JSONObject allDetails = new JSONObject();
        allDetails.put("user", jo_userDetails);
        allDetails.put("customerBankAccounts", bankacounts);
        allDetails.put("customerBankDetails", userBankDetails);
        allDetails.put("customerTransactionDataOther", tr_array); // this may need to change
        allDetails.put("customerTransactionDataMajorStatus", tr_array); // this may need to change
        allDetails.put("agentBankDetails", jsonAgentDetalils);
        allDetails.put("currencies", ar_currency);
        allDetails.put("agentSystems", ar_agentSystem);
        return allDetails.toString();
    }

  /*  @PostMapping("/call-center-agent-details")
    public String getCallCenterAgentBankTransactionDetails(@RequestHeader String Authorization) throws SQLException {
        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        DAOBank [] bankDetailsOfUser = bankService.getBankDetailsByIUserId(USER_ID);
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
            jo_deposit.put(Utils.TR_USERID, temp_deposit[0]);
            jo_deposit.put(Utils.TR_ID, temp_deposit[1]);
            jo_deposit.put(Utils.TR_AMOUNT, temp_deposit[2]);
            jo_deposit.put(Utils.TR_DATE, temp_deposit[3]); // Date to Be Fixed
            jo_deposit.put(Utils.TR_STATUS, temp_deposit[4]);
            jo_deposit.put(Utils.TR_TYPE, temp_deposit[5]);
           // jo_deposit.put("userName", temp_deposit[6]);
            array.put(i, jo_deposit);
        }

        JSONArray array_withdrawal = new JSONArray();
        for (int i = 0; i < withdrawals.length; i++) {
            System.out.println("withdrawals --- length :" + i);
            String[] temp_withdrawal = withdrawals[i].split(",");
            JSONObject jo_withdrawal = new JSONObject();
            jo_withdrawal.put(Utils.TR_USERID, temp_withdrawal[0]);
            jo_withdrawal.put(Utils.TR_ID, temp_withdrawal[1]);
            jo_withdrawal.put(Utils.TR_AMOUNT, temp_withdrawal[2]);
            jo_withdrawal.put(Utils.TR_DATE, temp_withdrawal[3]);
            jo_withdrawal.put(Utils.TR_STATUS, temp_withdrawal[4]);
            jo_withdrawal.put(Utils.TR_TYPE, temp_withdrawal[5]);
            jo_withdrawal.put("userName", temp_withdrawal[6]);
            array_withdrawal.put(i, jo_withdrawal);
        }

        JSONObject jo = new JSONObject();
          //  jo.put("callCenterAgentBanKDetails", userBankDetails);
            jo.put("depositData", array);
            jo.put("withdrawalData", array_withdrawal);

        return jo.toString();
    }*/
    /*@PostMapping( "/agent-details")
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
        DAOTransaction [] transactions = trService.getTransactionsByAgentId(USER_ID);

        JSONArray tr_array = new JSONArray();

        for (int i =0; i<transactions.length; i++) {
            JSONObject tr_json = new JSONObject();
            tr_json.put(Utils.TR_AMOUNT,transactions[i].getTramount());
            tr_json.put(Utils.TR_TYPE,transactions[i].getTrtype());
            tr_json.put(Utils.TR_DATE,transactions[i].getTrdatetime());
            tr_json.put(Utils.TR_USERID,transactions[i].getUserid());
            tr_json.put(Utils.TR_ID,transactions[i].getTrid());
            tr_array.put(i,tr_json);
        }*/


/*        String[] deposits = trService.getTransactionsByAgentId(USER_ID);
        String  [] withdrawals = trService.getTransactionsByAgentId(USER_ID);

        JSONArray array = new JSONArray();
        for (int i = 0; i < deposits.length; i++) {
            System.out.println("Deposit --- length :" + i);
            String[] temp_deposit = deposits[i].split(",");
            JSONObject jo_deposit = new JSONObject();
            jo_deposit.put(Utils.TR_USERID, temp_deposit[0]);
            jo_deposit.put(Utils.TR_ID, temp_deposit[1]);
            jo_deposit.put(Utils.TR_AMOUNT, temp_deposit[2]);
            jo_deposit.put(Utils.TR_DATE, temp_deposit[3]); // Date to Be Fixed
            jo_deposit.put(Utils.TR_STATUS, temp_deposit[4]);
            jo_deposit.put(Utils.TR_TYPE, temp_deposit[5]);
            jo_deposit.put("userName", temp_deposit[6]);
            array.put(i, jo_deposit);
        }

        JSONArray array_withdrawal = new JSONArray();
        for (int i = 0; i < withdrawals.length; i++) {
            System.out.println("withdrawals --- length :" + i);
            String[] temp_withdrawal = withdrawals[i].split(",");
            JSONObject jo_withdrawal = new JSONObject();
            jo_withdrawal.put(Utils.TR_USERID, temp_withdrawal[0]);
            jo_withdrawal.put(Utils.TR_ID, temp_withdrawal[1]);
            jo_withdrawal.put(Utils.TR_AMOUNT, temp_withdrawal[2]);
            jo_withdrawal.put(Utils.TR_DATE, temp_withdrawal[3]);
            jo_withdrawal.put(Utils.TR_STATUS, temp_withdrawal[4]);
            jo_withdrawal.put(Utils.TR_TYPE, temp_withdrawal[5]);
            jo_withdrawal.put("userName", temp_withdrawal[6]);
            array_withdrawal.put(i, jo_withdrawal);
        }

        JSONObject jo = new JSONObject();
        jo.put("agentBanKDetails", userBankDetails);
        jo.put("userTransDetails", tr_array);
        //jo.put("withdrawalData", array_withdrawal);
        return jo.toString();
    }*/


    @PostMapping("/change-status")
    public String changeStatus(@RequestHeader String Authorization, @RequestBody String obj ) {
        // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);

        String username = jwtTokenUtil.getUsernameFromToken(token);

        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();
        System.out.println(obj);

        return "done";

    }

    @PostMapping("/change-remarks")
    public String changeRemarks(@RequestHeader String Authorization, @RequestBody String obj ) {
        // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();
        System.out.println(obj);

        return "done";

    }
        private JSONArray setTrData(DAOTransaction[] userTrDetails) {
        JSONArray tr_array = new JSONArray();
        JSONObject tr_json = new JSONObject();
        for (int i = 0; i < userTrDetails.length; i++) {
            tr_json.put(Utils.TR_AMOUNT, userTrDetails[i].getTramount());
            tr_json.put(Utils.TR_TYPE, userTrDetails[i].getTrtype());
            tr_json.put(Utils.TR_DATE, userTrDetails[i].getTrdatetime());
            tr_json.put(Utils.TR_USERID, userTrDetails[i].getUserid());
            tr_json.put(Utils.TR_ID, userTrDetails[i].getTrid());
            tr_json.put(Utils.TR_STATUS, userTrDetails[i].getTrstatus());
            tr_json.put(Utils.TR_AMOUNT, userTrDetails[i].getTramount());
            tr_json.put(Utils.TR_SLIP, userTrDetails[i].getSlip());
            tr_json.put(Utils.TR_SLIP_DATe, userTrDetails[i].getSlipdate());
            tr_json.put(Utils.TR_REMARKS, userTrDetails[i].getRemarks());
            tr_array.put(i, tr_json);
        }
        return tr_array;
    }

}
