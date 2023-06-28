package com.mhsoft.service;

import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.repo.AgentCodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentCodeService {
    @Autowired
    private AgentCodeRepo agentRepo;

    public DAOAgentCode [] generateAgentCode(DAOAgentCode agentCode) {

       DAOAgentCode [] agentData = agentRepo.getAllAgentCodesById(agentCode.getAgentId());
       return agentData;
    }

    public DAOAgentCode saveAgentData(DAOAgentCode agentDetails) {
        DAOAgentCode  agentData = agentRepo.getLatestAgentCodeById(agentDetails.getAgentId());
        agentData.setAgentId(agentData.getAgentId());
        agentData.setId(agentData.getId());
        agentData.setLatest(false);
        agentRepo.save(agentData);

        DAOAgentCode agentNew = new DAOAgentCode();
        agentNew.setAgentCode(agentDetails.getAgentCode());
        agentNew.setAgentId(agentDetails.getAgentId());
        agentNew.setLatest(true);
        agentRepo.save(agentNew);
        return agentNew;
    }

    public DAOAgentCode getLatestAgentDetails(int agentId) {
        DAOAgentCode daoAgentCode = agentRepo.getLatestAgentCodeById(agentId);
        return daoAgentCode;

    }
}
