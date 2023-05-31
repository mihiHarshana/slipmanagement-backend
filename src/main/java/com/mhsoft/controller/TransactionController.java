package com.mhsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        tempTr.setStatus(daoTrans.getStatus());
        tempTr.setTrtype(daoTrans.getTrtype());
        tempTr.setUserid(daoTrans.getUserid());
        tempTr.setCurrency(daoTrans.getCurrency());

        tempTr.setAgentremarks(daoTrans.getAgentremarks());
        tempTr.setCustomerremarks(daoTrans.getCustomerremarks());
        tempTr.setCcagentremarks(daoTrans.getCcagentremarks());
        tempTr.setUtrnumber(daoTrans.getUtrnumber());
     //   tempTr.setFilename(daoTrans.getFilename());
        tempTr.setTrdisputeamount(daoTrans.getTrdisputeamount());
        tempTr.setAgentsystem(daoTrans.getAgentsystem());
        tempTr.setPlayeruser(daoTrans.getPlayeruser());
        return tempTr;

    }

    @RequestMapping (value = "api/new-deposit", method = RequestMethod.POST)
    public String setNewDeposit (DAOTransaction daoTransaction,
                                 @RequestParam String file ,
                                 @RequestParam String currency,
                                 @RequestParam double amount,
                                 @RequestParam String UTRNumber,
                                 @RequestParam String agentSystem,
                                 @RequestParam String playerUser,
                                 @RequestParam long slipTime,
                                 @RequestParam String remarks,
                                 @RequestHeader String Authorization) {

        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        daoTransaction.setUtrnumber(UTRNumber);
        daoTransaction.setSlipdate(slipTime);
        daoTransaction.setCustomerremarks(remarks);
        daoTransaction.setTramount(amount);
        daoTransaction.setUserid(USER_ID);
        daoTransaction.setAgentsystem(agentSystem);
        daoTransaction.setPlayeruser(playerUser);
        daoTransaction.setCurrency(currency);
        daoTransaction.setStatus(Utils.TR_STATUS_Created);
        daoTransaction.setTrtype(Utils.TRTRYOEDEPOSIT);

       boolean isTrNumberValid = trService.isUtrNumberValid(daoTransaction.getUtrnumber());
       if (isTrNumberValid) {
           return Utils.getInstance().JsonMessage("UTR Number duplicated.", HttpStatus.NOT_ACCEPTABLE);
       }
       // MultipartFile temp_file = (MultipartFile ) file;
        System.out.println( file.getBytes().length);
     //  MultipartFile temp_file = (MultipartFile) file;

/*        if (temp_file.isEmpty()) {
            return Utils.getInstance().JsonMessage("Please select a file to upload", HttpStatus.NOT_ACCEPTABLE);
        }*/
        /*try {
            // Get the file and save it somewhere
            byte[] bytes = temp_file.getBytes();
            Path temp_path = Paths.get(UPLOADED_FOLDER + LocalDate.now() + "\\" + USER_ID + "\\");
            Files.createDirectories(temp_path);
            Path path = Paths.get(temp_path + "\\" + temp_file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println("File uploaded sucessfully.");*/

            if (daoTransaction != null) {
                DAOTransaction temp = transRepo.save(saveTrData(daoTransaction));
                if (temp == null) {
                    return Utils.getInstance().JsonMessage("Cannot save deposit Try again", HttpStatus.NOT_ACCEPTABLE);
                }
                return Utils.getInstance().JsonMessage("Deposit Successful", HttpStatus.ACCEPTED);
            }
            return Utils.getInstance().JsonMessage("No data available for Deposit", HttpStatus.NOT_ACCEPTABLE);

    /*   } catch (IOException e) {
            return Utils.getInstance().JsonMessage("Somethign went wrong, please try again..! ", HttpStatus.NOT_ACCEPTABLE);
        }*/
    }

    @RequestMapping(value = "/api/change-status", method = RequestMethod.POST)
    public String changeTransStatus(@RequestBody String daoTrans, @RequestHeader String Authorization ) throws JsonProcessingException {
        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();


        ObjectMapper objectMapper = new ObjectMapper();
        DAOTransaction trs = objectMapper.readValue(daoTrans, DAOTransaction.class);

        DAOTransaction [] olddata = trService.getTransactionsByUserId(USER_ID);
        DAOTransaction  update = new DAOTransaction();
        for (int i=0; i<olddata.length; i++) {
            if (olddata[i].getid() == trs.getid()) {
                update.setPlayeruser(olddata[i].getPlayeruser());
                update.setAgentsystem(olddata[i].getAgentsystem());
                update.setCurrency(olddata[i].getCurrency());
                update.setTramount(olddata[i].getTramount());
                update.setSlipdate(olddata[i].getSlipdate());
                update.setCustomerremarks(olddata[i].getCustomerremarks());
                update.setUtrnumber(olddata[i].getUtrnumber());
                update.setSlip(olddata[i].getSlip());
                update.setTrdatetime(olddata[i].getTrdatetime());
                break;
            }
        }
        if (daoTrans != null ) {

            update.setTrtype(trs.getTrtype());
            update.setid(trs.getid());
            update.setStatus(trs.getStatus());

            DAOTransaction temp = transRepo.save(update);
            if (temp == null) {
                return Utils.getInstance().JsonMessage("Cannot change status  Try again", HttpStatus.NOT_ACCEPTABLE);
            }

            return Utils.getInstance().JsonMessage("Change Status Successful", HttpStatus.ACCEPTED);
        }
        return Utils.getInstance().JsonMessage("No data available for Change status", HttpStatus.NOT_ACCEPTABLE);
    }
}
