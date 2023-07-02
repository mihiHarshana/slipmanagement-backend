package com.mhsoft.service;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.repo.CallCenterAgentRepo;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CallCenterAgentService {

    @Autowired
    TransactionRepo transactionRepo;

    public String [] getAllTransactionDetails () {

       return  transactionRepo.getAllTransactions();

    }
}
