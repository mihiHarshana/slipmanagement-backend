package com.mhsoft.repo;
import com.mhsoft.model.DAOAgentCode;
import com.mhsoft.model.DAOTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CallCenterAgentRepo  extends JpaRepository<DAOAgentCode, Integer>{


}
