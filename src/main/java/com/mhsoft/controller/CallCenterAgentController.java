package com.mhsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.service.CallCenterAgentService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.utils.CcAgentChangeRemarks;
import com.mhsoft.utils.CcAgentChangeStatus;
import com.mhsoft.utils.DownloadFile;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CallCenterAgentController {
    @Autowired
    CallCenterAgentService ccaTrService;

    @Autowired
    TransactionService trService;

  /*  @RequestMapping("/api/call-center-agent-details")
    public String getCallCenterAgentDetails (@RequestHeader String Authorization) {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ArrayList<JSONObject> trDeposits = new ArrayList<>();
        ArrayList<JSONObject> trWithdrawals = new ArrayList<>();
        CcAgentTransResponse[] tempTrans = ccaTrService.getAllTransactionDetails();

        for (int i=0; i<tempTrans.length; i++) {
            //   String [] s=tempTrans[i].split(",");
            JSONObject jo = new JSONObject();
            String [] a = new String[2];

                jo.put("userid", tempTrans[i].getId());
                jo.put("transactionId", tempTrans[i].getTrid());

               //if ( ! s[3].equals("null")   ) {
                    jo.put("time", tempTrans[i].getTrdatetime());
                //}
                jo.put("status", tempTrans[i].getStatus());
                jo.put("statusList", Utils.getInstance().getTransStatusForCCA(tempTrans[i].getStatus(),tempTrans[i].getTrtype()));
                jo.put("amount",tempTrans[i].getCurrency().concat(" ").concat(Double.toString(tempTrans[i].getAmount()))) ;
                jo.put("agentName", tempTrans[i].getAgentusername());
                jo.put("customerMobile", tempTrans[i].getUsername());
                jo.put("agentSystem", tempTrans[i].getSystem());
                jo.put("playerUser", tempTrans[i].getPlayeruser());
                jo.put("utrNumber", tempTrans[i].getUtrnumber());

               // if (! s[10].equals("null")) {
                    jo.put("slipDate", tempTrans[i].getSlipdate());
                //}

                jo.put("ccAgentRemarks", tempTrans[i].getCcagentremarks());
                jo.put("customerRemarks", tempTrans[i].getCutomerremarks());
                jo.put("slipLink", tempTrans[i].getSlip());
              //TODO:  jo.put("slipName",tempTrans[i].);
                jo.put("trtype", tempTrans[i].getTrtype());



            if (jo.get("trtype").equals(Utils.TRTYPEWIDTHDRAW) ) {
                trWithdrawals.add(jo);
            } else {
                trDeposits.add(jo);
            }
        }


        JSONObject jo = new JSONObject();
        jo.put("depositData",trDeposits );
        jo.put("withdrawalData",trWithdrawals );
        return jo.toString();

    }
*/

    @RequestMapping("/api/call-center-agent-details")
    public String getCallCenterAgentDetails (@RequestHeader String Authorization) {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ArrayList<JSONObject> trDeposits = new ArrayList<>();
        ArrayList<JSONObject> trWithdrawals = new ArrayList<>();
        String [] tempTrans = ccaTrService.getAllTransactionDetails();

        for (int i=0; i<tempTrans.length; i++) {
            String [] s=tempTrans[i].split(",");
            JSONObject jo = new JSONObject();
            String [] a = new String[2];

            jo.put("userid", s[0]);
            jo.put("transactionId", s[2]);

            if ( ! s[3].equals("null")   ) {
                jo.put("time",Long.parseLong( s[3]));
            }
            jo.put("status", s[4]);
            jo.put("statusList", Utils.getInstance().getTransStatusForCCA(s[4],s[5]));
            jo.put("amount",s[7].concat(" ").concat(s[6]) );
            jo.put("agentName", s[16]);
            jo.put("customerMobile", s[1]);
            jo.put("agentSystem", s[17]);
            jo.put("playerUser", s[18]);
            jo.put("utrNumber", s[8]);

            if (! s[10].equals("null")) {
                jo.put("slipDate", Long.parseLong(s[10]));
            }
            if (s[13].equals("null")) {
                jo.put("ccAgentRemarks", "");
            } else {
                jo.put("ccAgentRemarks", s[13]);
            }

            jo.put("customerRemarks", s[14]);
            jo.put("slipLink", s[9]);
            jo.put("slipName", s[11]);
            jo.put("trtype", s[5]);



            if (jo.get("trtype").equals(Utils.TRTYPEWIDTHDRAW) ) {
                trWithdrawals.add(jo);
            } else {
                trDeposits.add(jo);
            }
        }


        JSONObject jo = new JSONObject();
        jo.put("depositData",trDeposits );
        jo.put("withdrawalData",trWithdrawals );
        return jo.toString();

    }

    @RequestMapping(value = "/api/cca/change-status" , method = RequestMethod.POST)

    public String ccAgentChangeStatus (@RequestHeader String Authorization, @RequestBody String ccaStatus) throws JsonProcessingException {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ObjectMapper objectMapper = new ObjectMapper();
        CcAgentChangeStatus customerData = objectMapper.readValue( ccaStatus, CcAgentChangeStatus.class);
        System.out.println(customerData.getId());
        System.out.println(customerData.getStatus());
        DAOTransaction oldtrans = trService.getTransactionByTrId(customerData.getId());
        oldtrans.setStatus(customerData.getStatus());
        DAOTransaction savedTrans =  trService.setTransactionData(oldtrans);
        if (savedTrans != null) {
            return Utils.getInstance().JsonMessage("Status updated", HttpStatus.ACCEPTED);
        }

        return  Utils.getInstance().JsonMessage("Status not updated", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/api/change-call-center-agent-remarks" , method = RequestMethod.POST)

    public String ccAgentChangeRemarks (@RequestHeader String Authorization, @RequestBody String ccaStatus) throws JsonProcessingException {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ObjectMapper objectMapper = new ObjectMapper();
        CcAgentChangeRemarks customerData = objectMapper.readValue( ccaStatus, CcAgentChangeRemarks.class);
        DAOTransaction oldtrans = trService.getTransactionByTrId(customerData.getId());
        oldtrans.setCcagentremarks(customerData.getRemarks());
        DAOTransaction savedTrans =  trService.setTransactionData(oldtrans);
        if (savedTrans != null) {
            return Utils.getInstance().JsonMessage("Remarks updated", HttpStatus.ACCEPTED);
        }

        return  Utils.getInstance().JsonMessage("Remarks not updated", HttpStatus.NOT_ACCEPTABLE);
    }

/*    @RequestMapping (value = "api/download" , method = RequestMethod.POST)
    public String downloadFile (@RequestHeader  String Authorization, @RequestBody String downloadFile) throws JsonProcessingException {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ObjectMapper objectMapper = new ObjectMapper();
        DownloadFile customerData = objectMapper.readValue( downloadFile, DownloadFile.class);


        return "";

    }*/
}
