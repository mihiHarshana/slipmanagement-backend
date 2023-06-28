package com.mhsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.repo.AgentCodeRepo;
import com.mhsoft.service.AgentCodeService;
import com.mhsoft.utils.AgentCode;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AgentController {
    @Autowired
    AgentCodeService agentCodeService;
    @Autowired
    AgentCodeRepo agentCodeRepo;

    @RequestMapping(value = "/api/generateAgentCode", method = RequestMethod.POST)
    public String generateCodeForAgent(@RequestBody DAOAgentCode agentCode) {
        DAOAgentCode [] agentData = agentCodeService.generateAgentCode(agentCode);
        List<String> agentCodesList = new ArrayList<>();

        for (DAOAgentCode tempCode: agentData) {
            agentCodesList.add(tempCode.getAgentCode());
        }
        String generatedCode = null;

        Random ran = new Random();
        int ranNum=0;
        for (int i=0; i<agentCodesList.size(); i++) {
            if (i==0) {
                  ranNum = ran.nextInt(9999);
                generatedCode = agentCode.getAgentId() + Integer.toString(ranNum);
            }
            if (generatedCode.equals(agentCodesList.get(i))) {
                i=0;
            }
        }

        JSONObject jo = new JSONObject();
        jo.put("agentCode",generatedCode);
        jo.put("agentid",agentCode.getAgentId() );

        return jo.toString();

    }

    @RequestMapping(value = "/api/saveAgentCode", method = RequestMethod.POST)
    public String saveAgentCode(@RequestBody DAOAgentCode agentCode) {
        agentCodeService.saveAgentData(agentCode);

        return Utils.getInstance().JsonMessage("Agent Code Saved", HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/api/new-agnet-code", method = RequestMethod.POST)
    public String saveNewAgentCode(@RequestBody String newAgentCode) throws JsonProcessingException {

        System.out.println("AGentCode data " + newAgentCode +  " * ********************** * ");

        ObjectMapper objectMapper = new ObjectMapper();
        AgentCode agentCode = objectMapper.readValue(newAgentCode, AgentCode.class);
        System.out.println(agentCode.getAgentId());
        System.out.println(agentCode.getAgentCode());


        // TODO: Get old agent data and update the table

 /*       DAOAgentCode oldAgentDetail = agentCodeService.getLatestAgentDetails(agentCode.getAgentid());
        oldAgentDetail.setId(oldAgentDetail.getId());
        oldAgentDetail.setAgentCode(oldAgentDetail.getAgentCode());
        oldAgentDetail.setLatest(false);
        agentCodeRepo.save(oldAgentDetail);

        // TODO: Add new agent data to the table.
        DAOAgentCode newAgentDetail = new DAOAgentCode();
        newAgentDetail.setLatest(true);
        newAgentDetail.setAgentCode(agentCode.getAgent_code());
        newAgentDetail.setAgentid(agentCode.getAgentid());
        agentCodeRepo.save(newAgentDetail);*/

        //Create agentDao with new details and send for saving. Saving part check and do the needfull.
        DAOAgentCode agentDetails = new DAOAgentCode();
        agentDetails.setAgentCode(agentCode.getAgentCode());
        agentDetails.setAgentId(agentCode.getAgentId());
        agentDetails.setLatest(agentDetails.isLatest());

        agentCodeService.saveAgentData(agentDetails);

        return Utils.getInstance().JsonMessage("Agent Code Saved", HttpStatus.ACCEPTED);
    }
}
