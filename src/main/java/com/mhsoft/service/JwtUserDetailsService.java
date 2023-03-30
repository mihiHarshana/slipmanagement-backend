package com.mhsoft.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mhsoft.model.UserDTO;
import com.mhsoft.utils.Utils;

@Service
public class JwtUserDetailsService implements UserDetailsService {



	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new org.springframework.security.core.userdetails.User("test1", "test1",
				new ArrayList<>());
	}
/*	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}*/

	/*
	 * public JSONObject checkUserAvailable(String username) { DAOUser user =
	 * userDao.findByUsername(username); if (user != null) {
	 * 
	 * JSONObject jo = new JSONObject(); jo.put("message", "User not available.");
	 * jo.put("stauts", "OK");
	 * 
	 * return jo; }
	 * 
	 * return new JSONObject();
	 * 
	 * }
	 */

	public String save(UserDTO user) {

/*		DAOUser tempUser = userDao.findByUsername(user.getUsername());
		if (tempUser == null ) {
			DAOUser newUser = new DAOUser();
			newUser.setUsername(user.getUsername());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setUserstatus(user.getUserStatus());
			newUser.setUserType(user.getUsertype());
			userDao.save(newUser);
			Utils util = new Utils();
			return util.JsonMessage("User registration successfull", HttpStatus.ACCEPTED).toString();
		} 
		Utils util = new Utils();
		return   util.JsonMessage("User Already Exists", HttpStatus.ACCEPTED).toString();*/
		
		return "Test1";
	}
}