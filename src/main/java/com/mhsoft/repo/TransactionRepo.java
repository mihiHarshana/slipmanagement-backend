package com.mhsoft.repo;

import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepo extends JpaRepository<DAOTransaction, Integer> {
    @Query(value = "SELECT * from transaction WHERE userid = ?1 " , nativeQuery = true)
    DAOTransaction[] getBankDetailsByUserId(int userId);


    @Query(value= "INSERT INTO `transaction` (`tramount`, `trdatetime`, `trstatus`, `trtype`, `userid`)" +
            " VALUES (, '22:22:57', 'SUBMITTED', 'DEPOSIT', '1')" , nativeQuery = true)
    String setDepositTransaction(DAOTransaction trans);


}