package com.mhsoft.service;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.DAOUser;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {
@Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;



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

    public DAOUser [] getUserDetailsByAgentId (int agentId) {
        DAOUser[] daoUser = userRepo.getUserDetailsByAgentId(agentId);
        return daoUser;
       /* JSONArray response = new JSONArray();

        if (daoUser == null) {
            return null;
        } else {
            for (int i =0; i< daoUser.length; i++) {

                JSONObject jo = new JSONObject();

                    jo.put("customerId", daoUser[i].getUserid() );
                    jo.put("userName",daoUser[i].getUsername());
                    jo.put("firstName",daoUser[i].getUserfname());
                    jo.put("customerStatus",daoUser[i].getUserStatus());

                response.put(i,jo);
            }
        }
        return response;*/
    }

    public DAOTransaction getTransactionByTrId(int trId) {
       DAOTransaction daoTransaction = transactionRepo.getTransactionByTrId(trId);
       return daoTransaction;

    }

    public DAOTransaction setTransactionData (DAOTransaction daoTrans) {
        return transactionRepo.save(daoTrans);

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
