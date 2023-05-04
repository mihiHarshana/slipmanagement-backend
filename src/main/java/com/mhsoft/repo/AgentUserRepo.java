package com.mhsoft.repo;

import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.model.DAOAgentUser;
import com.mhsoft.model.DAOBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentUserRepo extends JpaRepository<DAOAgentUser, Integer> {
    @Query(value = "SELECT * from agentuser WHERE userid = ?1 " , nativeQuery = true)
    DAOAgentUser getAgentIdForUser(int userId);




}
