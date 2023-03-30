package com.mhsoft.service;


import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.dto.UserDTO;
import com.mhsoft.entity.User;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private Utils utils;

    enum Types {
        ADMIN,
        AGENT,
        CUSTOMERCAREAGENT,
        CUSTOMER
    }

    enum Status {
        PENDING,
        APPROVED,
        SUSPENDED,
        UNSUSPENDED
    }

    public String saveUser(UserDTO userDTO) {
        User user = userRepo.getUserByUserName(userDTO.getUsername());
        Utils utils = new Utils();
        if (user == null) {
            UserDTO newUser = new UserDTO();
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword((bcryptEncoder.encode(userDTO.getPassword())));
            newUser.setUsertype(userDTO.getUsertype());
            newUser.setUserstatus("PENDING"); // When registering user is in pending approval
            userRepo.save(modelMapper.map(newUser, User.class));
            return utils.JsonMessage("User registration sucessfull", HttpStatus.ACCEPTED);
        } else {
            return utils.JsonMessage("User already available", HttpStatus.ACCEPTED);
        }
    }

    public String updateUser(UserDTO userDTO) {
        User userupdate = userRepo.getUserByUserName(userDTO.getUsername());
        Utils utils = new Utils();
        if (userupdate != null) {
            userupdate.setUsername(userDTO.getUsername());
            userupdate.setPassword((bcryptEncoder.encode(userDTO.getPassword())));
            userupdate.setUsertype(userDTO.getUsertype());
            userupdate.setUserstatus(userDTO.getUserstatus());
            userRepo.save(modelMapper.map(userupdate, User.class));
            return utils.JsonMessage("User Update successfull", HttpStatus.ACCEPTED);
        } else {
            return utils.JsonMessage("User not available", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public User getUser(User userDTO) {
        User user =  userRepo.getUserByUserName(userDTO.getUsername());
        return user;
    }

    public String loginUser(User user){

        User tempuser = userRepo.getUserByUserName(user.getUsername());
        Utils utils = new Utils();
        if (tempuser == null) {
           return utils.JsonMessage("Invalid Credentials", HttpStatus.ACCEPTED);

        } else if (bcryptEncoder.matches(user.getPassword(), user.getPassword())) {
            if (tempuser.getUserstatus().equals(Status.APPROVED.toString())) {
                final String token = jwtTokenUtil.generateToken(user.getUsername());
                JSONObject jo = new JSONObject();
                jo.put("mobile", tempuser.getUsername());
                jo.put("type", tempuser.getUsertype());
                jo.put("login" , System.currentTimeMillis());
                jo.put("token",token);
                return jo.toString();
            } else if (tempuser.getUserstatus().equals(Status.PENDING.toString())) {
               return  utils.JsonMessage("User pending approval", HttpStatus.ACCEPTED);
            } else if (tempuser.getUserstatus().equals(Status.SUSPENDED.toString())) {
                return utils.JsonMessage("User is suspended", HttpStatus.ACCEPTED);
            } else {
               return utils.JsonMessage("This should not execute", HttpStatus.ACCEPTED);
            }
        }
        else {
            return utils.JsonMessage("Invalid Credentials", HttpStatus.ACCEPTED);
        }
    }

}
