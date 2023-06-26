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

       DAOAgentCode [] agentData = agentRepo.getAllAgentCodesById(agentCode.getAgentid());
       return agentData;
    }

    public DAOAgentCode saveAgentData(DAOAgentCode agentDetails) {
        DAOAgentCode  agentData = agentRepo.getLatestAgentCodeById(agentDetails.getAgentid());
        agentData.setAgentid(agentData.getAgentid());
        agentData.setId(agentData.getId());
        agentData.setLatest(false);
        agentRepo.save(agentData);

        DAOAgentCode agentNew = new DAOAgentCode();
        agentNew.setAgentCode(agentDetails.getAgentCode());
        agentNew.setAgentid(agentDetails.getAgentid());
        agentNew.setLatest(true);
        agentRepo.save(agentNew);
        return agentNew;
    }

    public DAOAgentCode getLatestAgentDetails(int agentId) {
        DAOAgentCode daoAgentCode = agentRepo.getLatestAgentCodeById(agentId);
        return daoAgentCode;

    }
}
