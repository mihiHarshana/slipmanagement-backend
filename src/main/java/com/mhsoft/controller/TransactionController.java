package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.DAOUser;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.service.TransactionService;
import com.mhsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

  @Autowired TransactionRepo transRepo;
  @Autowired JwtTokenUtil jwtTokenUtil;
  @Autowired UserRepo userRepo;
  @Autowired TransactionService trService;

    private static String UPLOADED_FOLDER = "E:\\projects\\fileuploads\\";

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
        System.out.println("==== Time Stamp " + LocalDateTime.now().toEpochSecond(ZoneOffset.MIN));
        tempTr.setTrdatetime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        tempTr.setTramount(daoTrans.getTramount());
        tempTr.setTrstatus(daoTrans.getTrstatus());
        tempTr.setTrtype(daoTrans.getTrtype());
        tempTr.setUserid(daoTrans.getUserid());
    //    tempTr.setAgentremarks(daoTrans.getAgentremarks());
   //     tempTr.setCustomerremarks(daoTrans.getCustomerremarks());
      //  tempTr.setCcagentremarks(daoTrans.getCcagentremarks());
    //    tempTr.setUtrnumber(daoTrans.getUtrnumber());
     //   tempTr.setFilename(daoTrans.getFilename());
    //    tempTr.setTrdisputeamount(daoTrans.getTrdisputeamount());
        return tempTr;

    }

    @RequestMapping (value = "api/new-deposit", method = RequestMethod.POST)
    public String setNewDeposit (DAOTransaction daoTransaction, @RequestParam("file") MultipartFile file ,
                                 @RequestHeader String Authorization) {

        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

       boolean isTrNumberValid = trService.isUtrNumberValid(daoTransaction.getUtrnumber());
       if (isTrNumberValid) {
           return Utils.getInstance().JsonMessage("UTR Number duplicated.", HttpStatus.NOT_ACCEPTABLE);
       }

        if (file.isEmpty()) {
            return Utils.getInstance().JsonMessage("Please select a file to upload", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path temp_path = Paths.get(UPLOADED_FOLDER + LocalDate.now() + "\\" + USER_ID + "\\");
            Files.createDirectories(temp_path);
            Path path = Paths.get(temp_path + "\\" + file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println("File uploaded sucessfully.");

            if (daoTransaction != null) {
                DAOTransaction temp = transRepo.save(saveTrData(daoTransaction));
                if (temp == null) {
                    return Utils.getInstance().JsonMessage("Cannot save deposit Try again", HttpStatus.NOT_ACCEPTABLE);
                }
                return Utils.getInstance().JsonMessage("Deposit Successful", HttpStatus.ACCEPTED);
            }
            return Utils.getInstance().JsonMessage("No data available for Deposit", HttpStatus.NOT_ACCEPTABLE);

        } catch (IOException e) {
            return Utils.getInstance().JsonMessage("Somethign went wrong, please try again..! ", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
