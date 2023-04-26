package com.mhsoft.service;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.IDAOTransaction;
import com.mhsoft.repo.TransactionRepo;
import org.hibernate.mapping.Array;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;

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

    public String [] getTransactionsByType(String trType) {
        String []  transactions = transactionRepo.getTransactionsByType(trType);
        if (transactions == null) {
            return null;
        }
        return transactions;
    }

    public String [] getTransactionsByAgentId(int userId) {
        String [] transactions = transactionRepo.getTransactionsByUserAgentId(userId);
        if (transactions == null) {
            return null;
        }
        return transactions;
    }
}
