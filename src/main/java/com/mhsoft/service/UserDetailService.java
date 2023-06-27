package com.mhsoft.service;

import com.mhsoft.model.DAOTransaction;
import com.mhsoft.model.DAOUser;
import com.mhsoft.repo.TransactionRepo;
import com.mhsoft.repo.UserDetailsRepo;
import com.mhsoft.repo.UserRepo;
import org.apache.el.parser.JJTELParserState;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {

        @Autowired
        private UserDetailsRepo userDetailsRepo;

    @Autowired
    private UserRepo userRepo;

        public String [] getAgentDetailsByUserId(int userid) {
            String UserDetailsRepo  = userDetailsRepo.getBankDetialsOfAgentByuserId(userid);
            if (UserDetailsRepo == null) {
                return null;
            }
            return UserDetailsRepo.split(",");
        }

        public DAOUser getUserDetailsByUserId(int userId) {
            return userRepo.getUserDetailsByUserId(userId);
        }

}
