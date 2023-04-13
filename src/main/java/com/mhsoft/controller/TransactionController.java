package com.mhsoft.controller;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  @Autowired TransactionRepo transRepo;

    @RequestMapping(value = "/api/withdrawal", method = RequestMethod.POST)
    public String setWithdrawal(@RequestBody  DAOTransaction daoTrans) {
        DAOTransaction temp = transRepo.save(daoTrans);
        Utils utils = new Utils();
        if (temp == null) {
            return utils.JsonMessage("Cannot save withdrawal Try again", HttpStatus.NOT_ACCEPTABLE);
        }
        return utils.JsonMessage("Withdrawal Successful", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/api/deposit", method = RequestMethod.POST)
    public String setDeposit(DAOTransaction daoTrans) {
        DAOTransaction temp =  transRepo.save(daoTrans);
        Utils utils = new Utils();
        if (temp == null) {
            return utils.JsonMessage("Cannot save deposit Try again", HttpStatus.NOT_ACCEPTABLE);
        }
        return utils.JsonMessage("Deposit Successful", HttpStatus.ACCEPTED);
    }
}
