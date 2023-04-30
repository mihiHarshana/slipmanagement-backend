package com.mhsoft.controller;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

  @Autowired TransactionRepo transRepo;

    @RequestMapping(value = "/api/withdraw", method = RequestMethod.POST)
    public String setWithdrawal(@RequestBody  DAOTransaction daoTrans) {
        if (daoTrans != null ) {
            DAOTransaction temp = transRepo.save(saveTrData(daoTrans));
            if (temp == null) {
                return Utils.getInstance().JsonMessage("Cannot save withdrawal Try again", HttpStatus.NOT_ACCEPTABLE);
            }
            return Utils.getInstance().JsonMessage("Withdrawal Successful", HttpStatus.ACCEPTED);
        }
        return Utils.getInstance().JsonMessage("No data available for withdrawal", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/api/deposit", method = RequestMethod.POST)
    public String setDeposit(@RequestBody DAOTransaction daoTrans) {
        if (daoTrans != null ) {
            DAOTransaction temp = transRepo.save(saveTrData(daoTrans));
            if (temp == null) {
                return Utils.getInstance().JsonMessage("Cannot save deposit Try again", HttpStatus.NOT_ACCEPTABLE);
            }
            return Utils.getInstance().JsonMessage("Deposit Successful", HttpStatus.ACCEPTED);
        }
        return Utils.getInstance().JsonMessage("No data available for Deposit", HttpStatus.NOT_ACCEPTABLE);
    }

    private DAOTransaction saveTrData(DAOTransaction daoTrans) {
        DAOTransaction tempTr= new DAOTransaction();
        tempTr.setTrdatetime(LocalDateTime.now());
        tempTr.setTramount(daoTrans.getTramount());
        tempTr.setTrstatus(daoTrans.getTrstatus());
        tempTr.setTrtype(daoTrans.getTrtype());
        tempTr.setUserid(daoTrans.getUserid());
        return tempTr;

    }
}
