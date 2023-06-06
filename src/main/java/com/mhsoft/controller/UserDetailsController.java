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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
    public String getUserBankTransactionDetails(@RequestHeader String Authorization ,
                                                @RequestBody String defaultAccount) {
        // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        String[] bankacounts = null;

        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        System.out.println("Default Account printing :  " + defaultAccount);
        //Processing Default account
        String [] temp1 = defaultAccount.split(":");
        String [] temp2 = temp1[1].split(" ");
        System.out.println("1nd split" + temp1[1]);
        System.out.println("2nd split" + temp2[0]);
        String temp3 = temp2[0].replace("\"","");
        System.out.println(temp3);
       // if (default_account.length > 1) {

            bankService.updateBankDetailsByUserID(USER_ID, temp3);
        //}
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
            bankacounts = new String[bankDetailsOfUser.length ];

            for (int i = 0; i < bankacounts.length; i++) {
                bankacounts[i] = bankDetailsOfUser[i].getBankaccno().concat(" ").
                        concat(bankDetailsOfUser[i].getBankname());
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

       // JSONArray tr_can_com = new JSONArray();
        ArrayList<DAOTransaction> arralList_com_can = new ArrayList<>();
        ArrayList<DAOTransaction> arralList_other = new ArrayList<>();
        DAOTransaction [] userTrDetails_com_can; // completed and cancelled
        if (userTrDetails != null) {
            for (int i=0; i<userTrDetails.length; i++) {
                if (userTrDetails[i].getStatus().equals(Utils.TR_STATUS_Cancelled) ||
                        userTrDetails[i].getStatus().equals(Utils.TR_STATUS_UserConfirmed ) )  {
                    arralList_com_can.add(userTrDetails[i]);
                } else {
                    arralList_other.add(userTrDetails[i]);
                }

            }
        }

        JSONArray tr_array_com_can = setTrData(arralList_com_can);
        JSONArray tr_array_other = setTrData(arralList_other);

       // JSONArray tr_array = setTrData(userTrDetails);


        DAOAgentUser agentdetails = agentUserService.getAgentIdByUserID(USER_ID);

        DAOBank[] tempAgentDetails = bankService.getBankDetailsByIUserId(agentdetails.getAgentid());
        String[] bankacountsagent = null;
        JSONObject jsonAgentDetalils = new JSONObject();

        if (tempAgentDetails == null) {
            jsonAgentDetalils.put(Utils.BANK_NAME, "null");
            jsonAgentDetalils.put(Utils.BANK_CODE, "null");
            jsonAgentDetalils.put(Utils.BANK_BRANCH, "null");
            jsonAgentDetalils.put(Utils.BANK_INS, "null");
            jsonAgentDetalils.put(Utils.BANK_ACC_NO, "null");
        } else {
            bankacountsagent = new String[tempAgentDetails.length];

            for (int i = 0; i < bankacountsagent.length; i++) {
                bankacountsagent[i] = tempAgentDetails[i].getBankaccno();
            }

            for (int i = 0; i < bankacountsagent.length; i++) {
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
        String ar_currency[] = currencyService.getAllCurrency();
        DAOAgentSystem ar_agentSystem[] = agentSystemService.getAgentSystemByAgentId(agentdetails.getAgentid());

        JSONObject jo_userDetails = new JSONObject();
        jo_userDetails.put("name", username);
        // jo_userDetails.put("userId",USER_ID);
        jo_userDetails.put("playerUser", "not required");

        JSONObject allDetails = new JSONObject();
        allDetails.put("user", jo_userDetails);
        allDetails.put("customerBankAccounts", bankacounts);
        allDetails.put("customerBankDetails", userBankDetails);
        allDetails.put("customerTransactionDataOther", tr_array_com_can); // this may need to change
        allDetails.put("customerTransactionDataMajorStatus", tr_array_other); // this may need to change
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


/*    @PostMapping("/change-status")
    public String changeStatus(@RequestHeader String Authorization, @RequestBody String obj ) {
        // JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);

        String username = jwtTokenUtil.getUsernameFromToken(token);

        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();
        System.out.println(obj);

        return "done";

    }*/

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
            tr_json.put(Utils.TR_AMOUNT, userTrDetails[i].getAmount());
            tr_json.put(Utils.TR_TYPE, userTrDetails[i].getTrtype());
            tr_json.put(Utils.TR_DATE, userTrDetails[i].getTrdatetime());
            tr_json.put(Utils.TR_USERID, userTrDetails[i].getUserid());
            tr_json.put(Utils.TR_ID, userTrDetails[i].getid());
            tr_json.put(Utils.TR_STATUS, userTrDetails[i].getStatus());
            tr_json.put(Utils.TR_AMOUNT, userTrDetails[i].getAmount());
            tr_json.put(Utils.TR_SLIP, userTrDetails[i].getSlip());
            tr_json.put(Utils.TR_SLIP_DATe, userTrDetails[i].getSlipdate());
            tr_json.put(Utils.TR_CUS_REMARKS, userTrDetails[i].getCustomerremarks());
            tr_json.put(Utils.TR_CCAGENT_REMARKS, userTrDetails[i].getCcagentremarks());
            tr_json.put(Utils.TR_AGENT_REMARKS, userTrDetails[i].getAgentremarks());
            tr_array.put(i, tr_json);
        }
        return tr_array;
    }

    private JSONArray setTrData(ArrayList<DAOTransaction> userTrDetails) {
        System.out.println("Printing recieved data ====== " + userTrDetails);
        JSONArray tr_array = new JSONArray();

        for (int i = 0; i < userTrDetails.size(); i++) {
            JSONObject tr_json = new JSONObject();
            System.out.println("Priting trid ----------- " + userTrDetails.get(i).getid());
            tr_json.put(Utils.TR_AMOUNT, userTrDetails.get(i).getAmount());
            tr_json.put(Utils.TR_TYPE, userTrDetails.get(i).getTrtype());
            tr_json.put(Utils.TR_DATE, userTrDetails.get(i).getTrdatetime());
            tr_json.put(Utils.TR_USERID, userTrDetails.get(i).getUserid());
            tr_json.put(Utils.TR_ID, userTrDetails.get(i).getid());
            tr_json.put(Utils.TR_STATUS, userTrDetails.get(i).getStatus());
            tr_json.put(Utils.TR_AMOUNT, userTrDetails.get(i).getAmount());
           // tr_json.put(Utils.TR_SLIP, userTrDetails.get(i).getSlip());
            tr_json.put(Utils.TR_SLIP_DATe, userTrDetails.get(i).getSlipdate());
            tr_json.put(Utils.TR_CUS_REMARKS, userTrDetails.get(i).getCustomerremarks());
            tr_json.put(Utils.TR_CCAGENT_REMARKS, userTrDetails.get(i).getCcagentremarks());
            tr_json.put(Utils.TR_AGENT_REMARKS, userTrDetails.get(i).getAgentremarks());

            //File file = new File(userTrDetails.get(i).getSlip().concat(userTrDetails.get(i).getFilename() ));

            Path path = Paths.get(userTrDetails.get(i).getSlip()
                    .concat(userTrDetails.get(i).getFilename()));
            try {
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                tr_json.put(Utils.TR_SLIP,resource);
            }
          catch (Exception e ) {
              System.out.println(e);
          }

/*            try {
                Path fiePath = Paths.get(userTrDetails.get(i).getSlip()
                        .concat(userTrDetails.get(i).getFilename()));
                Resource resource = new UrlResource(fiePath.toUri());
                File multipartFile = new File(fiePath.toUri());
                System.out.println(multipartFile);
                System.out.println("is this a file " + multipartFile.isFile());

                if(resource.exists()) {
                    tr_json.put(Utils.TR_SLIP,multipartFile);
                } else {;
                    tr_json.put(Utils.TR_SLIP,"no file" );
                }

            }catch (Exception e ) {
                System.err.println(e);;
            }*/
            // Adding the related file
            tr_array.put(i, tr_json);
        }

        for (int i =0; i <=tr_array.length(); i++) {
            System.out.println("Priting after wards " + tr_array);
        }

        return tr_array;
    }
}
