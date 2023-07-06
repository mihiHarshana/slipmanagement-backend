package com.mhsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.service.CallCenterAgentService;
import com.mhsoft.service.TransactionService;
import com.mhsoft.utils.CcAgentChangeStatus;
import com.mhsoft.utils.CustomerStatus;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CallCenterAgentController {
    @Autowired
    CallCenterAgentService ccaTrService;


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


            System.out.println("What happens to s[10] " + s[10]);
                if (! s[10].equals("null")) {
                    jo.put("slipDate", Long.parseLong(s[10]));
                }

                jo.put("ccAgentRemarks", s[13]);
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

    @RequestMapping(value = "/api/cca/change-status" , method = RequestMethod.PUT)

    public String ccAgentChangeStatus (@RequestHeader String Authorization, @RequestBody String ccaStatus) throws JsonProcessingException {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ObjectMapper objectMapper = new ObjectMapper();
        CcAgentChangeStatus customerData = objectMapper.readValue( ccaStatus, CcAgentChangeStatus.class);

        return "";

    }
}
