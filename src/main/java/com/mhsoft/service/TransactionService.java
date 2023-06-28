package com.mhsoft.service;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.repo.TransactionRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public JSONArray getTransactionsByUserTransAgentId(int userId) {
        String [] transactions = transactionRepo.getTransactionsByUserTransAgentId(userId);
        JSONArray response = new JSONArray();
        if (transactions == null) {
            return null;
        } else {
            for (int i =0; i< transactions.length; i++) {
                String s[] = transactions[i].split(",");
                JSONObject jo = new JSONObject();
                for (int c=0; c<s.length; c++ ) {
                    jo.put("customerId", s[1] );
                    jo.put("userName",s[2]);
                    jo.put("firstName",s[3]);
                    jo.put("customerStatus",s[4]);
                    jo.put("agentSystem",s[5]);
                    jo.put("playerId",s[6]);

                }
                response.put(i,jo);
            }
        }
        return response;
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
