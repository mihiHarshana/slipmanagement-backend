package com.mhsoft.repo;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.IDAOTransaction;
import org.hibernate.mapping.Array;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface TransactionRepo extends JpaRepository<DAOTransaction, Integer> {
    @Query(value = "SELECT * from transaction WHERE userid = ?1 " , nativeQuery = true)
    DAOTransaction[] getBankDetailsByUserId(int userId);


    @Query(value= "INSERT INTO `transaction` (`tramount`, `trdatetime`, `trstatus`, `trtype`, `userid`)" +
            " VALUES (, '22:22:57', 'SUBMITTED', 'DEPOSIT', '1')" , nativeQuery = true)
    String setDepositTransaction(DAOTransaction trans);

    @Query(value = "SELECT tr.userid, tr.trid, tr.tramount, tr.trdatetime, tr.trstatus, tr.trtype, " +
            "user.username FROM transaction AS tr   JOIN user ON tr.userid = user.id where tr.trType = ?1" , nativeQuery = true)
    String [] getTransactionsByType(String trType);


}
