package com.mhsoft.repo;

import com.mhsoft.model.DAOAgentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentSystemRepo extends JpaRepository<DAOAgentSystem, Integer> {
    @Query(value = "SELECT * from agentsystem WHERE agentid = ?1 " , nativeQuery = true)
    DAOAgentSystem [] getAgentSystemsByAgentId(int agentid);
}
