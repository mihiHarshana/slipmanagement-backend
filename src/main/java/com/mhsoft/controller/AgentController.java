package com.mhsoft.controller;

import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.service.AgentCodeService;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class AgentController {
    @Autowired
    AgentCodeService agentCodeService;

    @RequestMapping(value = "/api/generateAgentCode", method = RequestMethod.POST)
    public String generateCodeForAgent(@RequestBody DAOAgentCode agentCode) {
        DAOAgentCode [] agentData = agentCodeService.generateAgentCode(agentCode);
        List agentCodesList = new ArrayList();
        for (int i=0; i< agentData.length; i++ ){
            agentCodesList.add(agentData[i].getAgentCode());
        }
        String generatedCode = null;

        Random ran = new Random();
        int ranNum; ran.nextInt(1000,9999);
        for (int i=0; i<agentCodesList.size(); i++) {
            if (i==0) {
                ranNum =ran.nextInt(1000,9999);
                generatedCode = agentCode.getAgentid() + Integer.toString(ranNum);
            }
            if (generatedCode.equals(agentCodesList.get(i))) {
                i=0;
            }
        }

        JSONObject jo = new JSONObject();
        jo.put("agentCode",generatedCode);
        jo.put("agentid",agentCode.getAgentid() );

        return jo.toString();

    }

    @RequestMapping(value = "/api/saveAgentCode", method = RequestMethod.POST)
    public String saveAgentCode(@RequestBody DAOAgentCode agentCode) {
        agentCodeService.saveAgentData(agentCode);

        return Utils.getInstance().JsonMessage("Agent Code Saved", HttpStatus.ACCEPTED).toString();
    }
}
