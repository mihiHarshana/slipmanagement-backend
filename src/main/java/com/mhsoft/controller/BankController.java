package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.dao.BankDao;
import com.mhsoft.model.DAOBank;
import com.mhsoft.model.DAOUser;
import com.mhsoft.model.UserDTO;
import com.mhsoft.service.BankService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BankController {


    @Autowired
    BankDao bankDao;
    @Autowired
    BankService bankService;


    @RequestMapping(value = "/api/bankdetails" ,method = RequestMethod.POST )
    public String getBankDetailsByUserID(@RequestBody Integer userid) {
        //int int_userId = Integer.parseInt(userid);
        String  bankDetailsOfUser = bankService.getBankDetailsByIUserId(userid);
     //   String bankDetailOfAgent =


        return bankDetailsOfUser;
    }

/*
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }*/
}
