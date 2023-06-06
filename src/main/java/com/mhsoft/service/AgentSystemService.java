package com.mhsoft.service;

import com.mhsoft.model.DAOAgentSystem;
import com.mhsoft.repo.AgentSystemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentSystemService {
    @Autowired
    private AgentSystemRepo agentSystemRepo;

    public DAOAgentSystem[] getAgentSystemByAgentId(int  agentId) {
        DAOAgentSystem [] agentData = agentSystemRepo.getAgentSystemsByAgentId( agentId);
       return agentData;
    }
}
