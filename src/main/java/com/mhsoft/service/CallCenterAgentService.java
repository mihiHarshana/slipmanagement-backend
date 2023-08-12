package com.mhsoft.service;

import com.mhsoft.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallCenterAgentService {

    @Autowired
    TransactionRepo transactionRepo;

    public String [] getAllTransactionDetails () {

       return  transactionRepo.getAllTransactions();

    }
}
