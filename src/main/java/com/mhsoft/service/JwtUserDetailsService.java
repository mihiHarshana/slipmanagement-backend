package com.mhsoft.service;

import com.mhsoft.dao.UserDao;
import com.mhsoft.model.DAOUser;
import com.mhsoft.model.UserDTO;
import com.mhsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public String save(UserDTO user) {

        DAOUser tempUser = userDao.findByUsername(user.getUsername());
        if (tempUser == null) {
            DAOUser newUser = new DAOUser();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setUserstatus(user.getUserStatus());
            newUser.setUserType(user.getUserType());
            newUser.setUserfname(user.getFirstname());
            newUser.setUserlname(user.getLastname());
            userDao.save(newUser);
            return Utils.getInstance().JsonMessage("User registration successfull", HttpStatus.ACCEPTED).toString();
        }
        return Utils.getInstance().JsonMessage("User Already Exists", HttpStatus.ACCEPTED).toString();
    }
    public String update(UserDTO user) {
        DAOUser tempUser = userDao.findByUsername(user.getUsername());
        DAOUser newUser = new DAOUser();
        newUser.setUserId(tempUser.getUserid());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setUserstatus(user.getUserStatus());
        newUser.setUserType(user.getUserType());
        newUser.setUserfname(user.getFirstname());
        newUser.setUserlname(user.getLastname());
        userDao.save(newUser);
        return Utils.getInstance().JsonMessage("User details updated successfull", HttpStatus.ACCEPTED).toString();
    }
}