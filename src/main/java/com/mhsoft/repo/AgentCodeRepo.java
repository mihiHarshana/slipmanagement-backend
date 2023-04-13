package com.mhsoft.repo;

import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.model.DAOBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentCodeRepo extends JpaRepository<DAOAgentCode, Integer> {
    @Query(value = "SELECT * from agentcode WHERE agentid = ?1 " , nativeQuery = true)
    DAOBank getAgentCodeByAgentId(int agentid);
}
