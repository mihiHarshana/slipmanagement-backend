package com.mhsoft.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @RequestMapping(value = "/api/banktransactions", method = RequestMethod.POST)
    public String getTransactionDetailsByUserId() {




        return "Transaction details";
    }
}
