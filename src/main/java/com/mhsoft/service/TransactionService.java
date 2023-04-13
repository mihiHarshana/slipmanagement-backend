package com.mhsoft.service;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
@Autowired
    private TransactionRepo transactionRepo;

    public DAOTransaction [] getTransactionsByUserId(int userid) {
        DAOTransaction [] transaction = transactionRepo.getBankDetailsByUserId(userid);
        if (transaction == null) {
            return null;
        }
        return transaction;
    }
}
