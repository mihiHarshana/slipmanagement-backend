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
        DAOTransaction [] transaction = transactionRepo.getTransactionsByUserId(userid);
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

    public DAOTransaction [] getTransactionsByAgentId(int userId) {
        DAOTransaction [] transactions = transactionRepo.getTransactionsByUserAgentId(userId);
        if (transactions == null) {
            return null;
        }
        return transactions;
    }

    public boolean isUtrNumberValid(String utrNumber) {

        DAOTransaction  transactions = transactionRepo.isUtrNumberValid(utrNumber);
        if (transactions == null) {
            return false;
        } else {
            return true;
        }
    }
}
