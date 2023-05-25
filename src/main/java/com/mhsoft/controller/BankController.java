package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.service.BankService;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BankController {


    @Autowired
    BankDao bankDao;
    @Autowired
    BankService bankService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;


/*    @RequestMapping(value = "/api/bankdetails", method = RequestMethod.POST)
    public String getBankDetailsByUserID(@RequestBody DAOBank userDetails) {
        //int int_userId = Integer.parseInt(userid);
        DAOBank bankDetailsOfUser = bankService.getBankDetailsByIUserId(userDetails.getUserid());
        //   String bankDetailOfAgent =
        if (bankDetailsOfUser == null) {
            return Utils.getInstance().JsonMessage("Bank details not availale for the user",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        JSONObject jo = new JSONObject();
        jo.put("bankname", bankDetailsOfUser.getBankname());
        jo.put("bankcode", bankDetailsOfUser.getBankcode());
        jo.put("branchname", bankDetailsOfUser.getBranchname());
        jo.put("bandinstructions", bankDetailsOfUser.getBankinst());
        jo.put("bankaccno", bankDetailsOfUser.getBankaccno());
        return jo.toString();
    }*/

/*
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }*/

    @RequestMapping(value = "/api/new-bank-details", method = RequestMethod.POST)
        public String setBankDetailsByUserID(@RequestBody DAOBank bankdetails, @RequestHeader String Authorization) {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);

        bankdetails.setUserid(USER_ID);
        DAOBank bankDao1 = bankDao.save(bankdetails);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(bankDao1 != null) {
          return Utils.getInstance().JsonMessage("Bank details saved successfully", HttpStatus.ACCEPTED);
        } else {
           return  Utils.getInstance().JsonMessage("Error saving bank details.", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
