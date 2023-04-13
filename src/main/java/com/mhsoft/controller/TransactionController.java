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
import java.time.LocalDateTime;

@RestController
public class TransactionController {

  @Autowired TransactionRepo transRepo;

    @RequestMapping(value = "/api/withdraw", method = RequestMethod.POST)
    public String setWithdrawal(@RequestBody  DAOTransaction daoTrans) {
        Utils utils = new Utils();
        if (daoTrans != null ) {
            DAOTransaction temp = transRepo.save(saveTrData(daoTrans));
            if (temp == null) {
                return utils.JsonMessage("Cannot save withdrawal Try again", HttpStatus.NOT_ACCEPTABLE);
            }
            return utils.JsonMessage("Withdrawal Successful", HttpStatus.ACCEPTED);
        }
        return utils.JsonMessage("No data available for withdrawal", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/api/deposit", method = RequestMethod.POST)
    public String setDeposit(@RequestBody DAOTransaction daoTrans) {
        Utils utils = new Utils();
        if (daoTrans != null ) {
            DAOTransaction temp = transRepo.save(saveTrData(daoTrans));
            if (temp == null) {
                return utils.JsonMessage("Cannot save deposit Try again", HttpStatus.NOT_ACCEPTABLE);
            }
            return utils.JsonMessage("Deposit Successful", HttpStatus.ACCEPTED);
        }
        return utils.JsonMessage("No data available for Deposit", HttpStatus.NOT_ACCEPTABLE);
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
