package com.mhsoft.repo;

import com.mhsoft.model.DAOAgentUser;
import com.mhsoft.model.DAOBank;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDetailsRepo   extends JpaRepository<DAOAgentUser, Integer> {

    @Query(value = "SELECT bankdetails.bankaccno, bankdetails.bankcode, bankdetails.bankinst, " +
            "bankdetails.bankname, bankdetails.branchname FROM bankdetails\n" +
            "INNER JOIN agentuser ON bankdetails.userid = agentuser.userid WHERE" +
            " agentuser.userid =?1  " , nativeQuery = true)
    String  getBankDetialsOfAgentByuserId(int userId);

}
