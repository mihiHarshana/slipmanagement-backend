package com.mhsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.DAOUser;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.service.TransactionService;
import com.mhsoft.service.UserDetailService;
import com.mhsoft.utils.CustomerDetails;
import com.mhsoft.utils.CustomerStatus;
import com.mhsoft.utils.Utils;
import com.mhsoft.utils.Withdrawal;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

  @Autowired TransactionRepo transRepo;
  @Autowired JwtTokenUtil jwtTokenUtil;
  @Autowired UserRepo userRepo;
  @Autowired TransactionService trService;
  @Autowired UserDetailService userDetailService;

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
        tempTr.setAmount(daoTrans.getAmount());
        tempTr.setStatus(daoTrans.getStatus());
        tempTr.setTrtype(daoTrans.getTrtype());
        tempTr.setUserid(daoTrans.getUserid());
        tempTr.setCurrency(daoTrans.getCurrency());

        tempTr.setAgentremarks(daoTrans.getAgentremarks());
        tempTr.setCustomerremarks(daoTrans.getCustomerremarks());
        tempTr.setCcagentremarks(daoTrans.getCcagentremarks());
        tempTr.setUtrnumber(daoTrans.getUtrnumber());
        tempTr.setFilename(daoTrans.getFilename());
        tempTr.setTrdisputeamount(daoTrans.getTrdisputeamount());
        tempTr.setAgentSystem(daoTrans.getAgentSystem());
        tempTr.setPlayerUser(daoTrans.getPlayerUser());
        tempTr.setSlip(daoTrans.getSlip());
        tempTr.setSlipdate(daoTrans.getSlipdate());
        return tempTr;

    }

    @RequestMapping (value = "api/new-deposit", method = RequestMethod.POST)
    public String setNewDeposit (DAOTransaction daoTransaction,
                                 @RequestParam("file") MultipartFile file ,
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
        System.out.println("Slip Date time : " +  slipTime);
        daoTransaction.setUtrnumber(UTRNumber);
        daoTransaction.setSlipdate(slipTime);
        daoTransaction.setCustomerremarks(remarks);
        daoTransaction.setAmount(amount);
        daoTransaction.setUserid(USER_ID);
        daoTransaction.setAgentSystem(agentSystem);
        daoTransaction.setPlayerUser(playerUser);
        daoTransaction.setCurrency(currency);
        daoTransaction.setStatus(Utils.TR_STATUS_SUBMITTED);
        daoTransaction.setTrtype(Utils.TRTRYOEDEPOSIT);

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
            String originalName  = file.getOriginalFilename();
            String filename =originalName.substring(0,file.getOriginalFilename().indexOf(".") );
            String  extention = originalName.substring(file.getOriginalFilename().indexOf("."));
            System.out.println("fter substring + " + filename + " " +  extention);;
            String fileNameToSave = filename+ "_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + extention;

            Path path = Paths.get(temp_path + "\\" + fileNameToSave);
            Files.write(path, bytes);
            System.out.println("File uploaded sucessfully.");

            daoTransaction.setSlip(temp_path + "\\");
            daoTransaction.setFilename(fileNameToSave);

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
                update.setPlayerUser(olddata[i].getPlayerUser());
                update.setAgentSystem(olddata[i].getAgentSystem());
                update.setCurrency(olddata[i].getCurrency());
                update.setAmount(olddata[i].getAmount());
                update.setSlipdate(olddata[i].getSlipdate());
                update.setCustomerremarks(olddata[i].getCustomerremarks());
                update.setUtrnumber(olddata[i].getUtrnumber());
                update.setSlip(olddata[i].getSlip());
                update.setTrdatetime(olddata[i].getTrdatetime());
                update.setUserid(olddata[i].getUserid());
                update.setFilename(olddata[i].getFilename());
                update.setSlip(olddata[i].getSlip());
                update.setTrtype(olddata[i].getTrtype());
                update.setid(trs.getid());
                update.setStatus(trs.getStatus());
                break;
            }
        }
        if (daoTrans != null ) {

/*            update.setTrtype(trs.getTrtype());
            update.setid(trs.getid());
            update.setStatus(trs.getStatus());*/

            DAOTransaction temp = transRepo.save(update);
            if (temp == null) {
                return Utils.getInstance().JsonMessage("Cannot change status  Try again", HttpStatus.NOT_ACCEPTABLE);
            }

            return Utils.getInstance().JsonMessage("Change Status Successful", HttpStatus.ACCEPTED);
        }
        return Utils.getInstance().JsonMessage("No data available for Change status", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping (value = "api/new-withdrawal", method = RequestMethod.POST)
    public String setNewWithdrawal (@RequestBody  String daoWithdrawUserValues,  @RequestHeader String Authorization) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Withdrawal obj = objectMapper.readValue( daoWithdrawUserValues, Withdrawal.class);

        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        DAOTransaction daoWithdraw = new DAOTransaction();
        USER_ID = DAOUser.getUserid();
        daoWithdraw.setUserid(USER_ID);
        daoWithdraw.setPlayerUser(obj.getPlayerUser());
        daoWithdraw.setAgentSystem(obj.getAgentSystem());
        daoWithdraw.setCurrency(obj.getCurrency());
        daoWithdraw.setAmount(obj.getAmount());
        daoWithdraw.setCustomerremarks(obj.getRemarks());
        daoWithdraw.setTrtype(Utils.TRTYPEWIDTHDRAW);
        daoWithdraw.setStatus(Utils.TR_STATUS_SUBMITTED);
        daoWithdraw.setTrdatetime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        transRepo.save(daoWithdraw);
        return Utils.getInstance().JsonMessage("Withdrawal successfull", HttpStatus.ACCEPTED);
    }

    @RequestMapping (value = "api/change-customer-status" , method = RequestMethod.POST)
    public String changeCustomerStatus (@RequestBody  String strCustomerData,  @RequestHeader String Authorization) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerStatus customerData = objectMapper.readValue( strCustomerData, CustomerStatus.class);
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userDetailService.getUserDetailsByUserId(customerData.getId());
        DAOUser daoNewUser = new DAOUser();
        daoNewUser.setUserstatus(customerData.getCustomerStatus());
        daoNewUser.setUserType(DAOUser.getUsertype());
        daoNewUser.setUserId(customerData.getId());
        daoNewUser.setPassword(DAOUser.getPassword());
        daoNewUser.setUserlname(DAOUser.getUserlname());
        daoNewUser.setUserfname(DAOUser.getUserfname());
        daoNewUser.setUsername(DAOUser.getUsername());
        userRepo.save(daoNewUser);

        return Utils.getInstance().JsonMessage("User Status updated Successfully", HttpStatus.ACCEPTED);
    }

    @RequestMapping (value ="/api/view-transaction" , method = RequestMethod.POST)
    public String  viewTransactions(@RequestBody CustomerDetails cusDetails, @RequestHeader String Authorization) {
        System.out.println("This is the id " + cusDetails.getCustomerId());
        DAOTransaction [] daoTrans = trService.getTransactionsByUserId(cusDetails.getCustomerId());
       JSONObject jo = new JSONObject();
  /*       JSONArray trArray = new JSONArray();

        for (int i=0; i<daoTrans.length; i++) {
            JSONObject trans =  new JSONObject();
            trans.put("transactionId", daoTrans[i].getid());
            trans.put("date",daoTrans[i].getTrdatetime());
            trans.put("type", daoTrans[i].getTrtype());
            trans.put("amount", daoTrans[i].getAmount());
            trans.put("status",daoTrans[i].getStatus());
            trans.put("statusList",daoTrans[i].getStatus()); //TODO need to load proper list here
            trans.put("slipDate", daoTrans[i].getSlipdate());
            trans.put("customerRemarks",daoTrans[i].getCustomerremarks());
            trans.put("agentRemarks",daoTrans[i].getAgentremarks());
            trans.put("ccAgentRemarks" ,daoTrans[i].getCcagentremarks());
            trans.put("slipLink",daoTrans[i].getSlip());
            trans.put("slipName", daoTrans[i].getFilename());

            trArray.put(i, trans);
        }
        jo.put("CustomerTransaction", trArray);
        return jo.toString();*/

        DAOTransaction[] userTrDetails = trService.getTransactionsByUserId(cusDetails.getCustomerId());

        // JSONArray tr_can_com = new JSONArray();
        ArrayList<DAOTransaction> arralList_com_can = new ArrayList<>();
        ArrayList<DAOTransaction> arralList_other = new ArrayList<>();
        DAOTransaction [] userTrDetails_com_can; // completed and cancelled
        if (userTrDetails != null) {
            for (int i=0; i<userTrDetails.length; i++) {
                if (userTrDetails[i].getStatus().equals(Utils.TR_STATUS_Cancelled) ||
                        userTrDetails[i].getStatus().equals(Utils.TR_STATUS_Completed ) )  {
                    arralList_com_can.add(userTrDetails[i]);
                } else {
                    arralList_other.add(userTrDetails[i]);
                }

            }
        }

        JSONArray tr_array_com_can = setTrData(arralList_com_can);
        JSONArray tr_array_other = setTrData(arralList_other);

        jo.put("customerTransactionDataOther", tr_array_other ); // this may need to change
        jo.put("customerTransactionDataMajorStatus", tr_array_com_can ); // this may need to change

        return jo.toString();
    }


    private JSONArray setTrData(ArrayList<DAOTransaction> userTrDetails) {
        System.out.println("Printing recieved data ====== " + userTrDetails);
        JSONArray tr_array = new JSONArray();

        for (int i = 0; i < userTrDetails.size(); i++) {
            JSONObject tr_json = new JSONObject();
            System.out.println("Priting trid ----------- " + userTrDetails.get(i).getid());
            tr_json.put(Utils.TR_AMOUNT, userTrDetails.get(i).getAmount());
            tr_json.put(Utils.TR_TYPE, userTrDetails.get(i).getTrtype());
            tr_json.put(Utils.TR_DATE, userTrDetails.get(i).getTrdatetime());
            tr_json.put(Utils.TR_USERID, userTrDetails.get(i).getUserid());
            tr_json.put(Utils.TR_ID, userTrDetails.get(i).getid());
            tr_json.put(Utils.TR_STATUS, userTrDetails.get(i).getStatus());
            tr_json.put(Utils.TR_AMOUNT, userTrDetails.get(i).getAmount());
            tr_json.put(Utils.TR_SLIP, userTrDetails.get(i).getSlip());
            tr_json.put(Utils.TR_SLIP_NAME, userTrDetails.get(i).getFilename());
            tr_json.put(Utils.TR_SLIP_DATe, userTrDetails.get(i).getSlipdate());
            tr_json.put(Utils.TR_CUS_REMARKS, userTrDetails.get(i).getCustomerremarks());
            tr_json.put(Utils.TR_CCAGENT_REMARKS, userTrDetails.get(i).getCcagentremarks());
            tr_json.put(Utils.TR_AGENT_REMARKS, userTrDetails.get(i).getAgentremarks());
            tr_json.put(Utils.TR_STATUS_LIST, Utils.getInstance().getTransStatus(userTrDetails.get(i).getStatus(),
                    userTrDetails.get(i).getTrtype()));
            tr_array.put(i, tr_json);
        }

        for (int i =0; i <=tr_array.length(); i++) {
            System.out.println("Priting after wards " + tr_array);
        }
        return tr_array;
    }
}
