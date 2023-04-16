package com.mhsoft.service;

import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.repo.AgentCodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AgentCodeService {
    @Autowired
    private AgentCodeRepo agentRepo;


    public DAOAgentCode [] generateAgentCode(DAOAgentCode agentCode) {

       DAOAgentCode [] agentData = agentRepo.getAllAgentCodesById(agentCode.getAgentid());
       return agentData;

    }



}
