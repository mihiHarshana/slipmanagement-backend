package com.mhsoft.repo;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.IDAOTransaction;
import com.mhsoft.utils.AgentUserTransDetails;
import org.hibernate.mapping.Array;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface TransactionRepo extends JpaRepository<DAOTransaction, Integer> {
    @Query(value = "SELECT * from transaction WHERE userid = ?1 " , nativeQuery = true)
    DAOTransaction[] getBankDetailsByUserId(int userId);


 /*   @Query(value= "INSERT INTO `transaction` (`tramount`, `trdatetime`, `trstatus`, `trtype`, `userid`)" +
            " VALUES (, '22:22:57', 'SUBMITTED', 'DEPOSIT', '1')" , nativeQuery = true)
    String setDepositTransaction(DAOTransaction trans);*/

    @Query(value = "SELECT tr.userid, tr.id, tr.amount, tr.trdatetime, tr.status, tr.trtype, " +
            "user.username FROM transaction AS tr   JOIN user ON tr.userid = user.id where tr.trType = ?1" , nativeQuery = true)
    String [] getTransactionsByType(String trType);


    // TODO: to be used
    @Query(value = "SELECT tr.userid, tr.id, tr.amount, tr.trdatetime, tr.status, tr.trtype, tr.agentremarks," +
            "u.username, tr.agentremarks, tr.ccagentremarks, tr.customerremarks, tr.agentsystem, tr.filename, " +
            "tr.trdisputeamount, tr.utrnumber, tr.slipdate, tr.currency, tr.playeruser, tr.slip " +
            "FROM transaction AS tr " +
            "INNER JOIN agentuser au ON tr.userid = au.userid " +
            "INNER JOIN user u ON au.userid = u.id WHERE au.agentid =?1 " , nativeQuery = true)
    DAOTransaction [] getTransactionsByUserAgentId(int agentId);

    @Query(value = "SELECT tr.id, tr.userid, u.username, u.userfname, u.userstatus, ags.system ,tr.playeruser FROM transaction AS tr \n" +
            "INNER JOIN agentuser au ON tr.userid = au.userid\n" +
            "INNER JOIN agentsystem ags  ON tr.agentsystem = ags.id \n" +
            "INNER JOIN user u ON au.userid = u.id WHERE au.agentid =?1 " , nativeQuery = true)
    String [] getTransactionsByUserTransAgentId(int agentId);


    @Query(value = "SELECT tr.userid, tr.id, tr.amount, tr.trdatetime, tr.status, tr.trtype, " +
            "c.username," +
            "tr.agentremarks, tr.ccagentremarks, tr.customerremarks, tr.filename, tr.trdisputeamount, tr.utrnumber," +
            "tr.slipdate" +
            "FROM transaction tr" +
            "INNER JOIN agentuser us ON tr.userid = us.userid" +
            "INNER JOIN user c ON us.userid = c.id" +
            "WHERE us.agentid =?1" , nativeQuery = true)
    DAOTransaction [] getTransactionsByUserAgentId_problametic(int agentId);

    @Query(value = "SELECT tr.userid, tr.id, tr.amount, tr.trdatetime, tr.status, tr.trtype, c.username, " +
            "tr.agentremarks, tr.ccagentremarks, tr.customerremarks, tr.filename, tr.trdisputeamount, " +
            "tr.utrnumber, tr.slipdate, tr.slip, tr.currency, tr.agentsystem, tr.playeruser FROM transaction tr INNER JOIN user c ON c.id = tr.userid" +
            " WHERE c.id =?1 ", nativeQuery = true)
    DAOTransaction [] getTransactionsByUserId(int agentId); //this is the one needed to check

    @Query(value = "SELECT * FROM transaction WHERE utrnumber =?1 AND status <> 'Rejected' "
            , nativeQuery = true)
    DAOTransaction   isUtrNumberValid(String utrnumber);

/*    @Query(value = "SELECT tr.trid, tr.amount, tr.trdatetime, tr.trstatus, tr.trtype, tr.userid FROM transaction AS tr ")
    String [] getTransactionsByUserAgentId(int agentId);*/
}
