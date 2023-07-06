package com.mhsoft.service;

import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.model.DAOAgentUser;
import com.mhsoft.repo.AgentUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentUserService {

    @Autowired
    AgentUserRepo agentUserRepo;

    public DAOAgentUser getAgentIdByUserID(int userId) {
        DAOAgentUser  agentData = agentUserRepo.getAgentIdForUser(userId);

        return agentData;
    }

    public DAOAgentUser saveAgentUser(DAOAgentUser agentUser) {
        return agentUserRepo.save(agentUser);
    }



}
