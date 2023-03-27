package com.mhsoft.service;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mhsoft.dao.UserDao;
import com.mhsoft.model.DAOUser;
import com.mhsoft.model.UserDTO;

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

		DAOUser tempUser = userDao.findByUsername(user.getUsername());
		if (tempUser == null) {
			DAOUser newUser = new DAOUser();
			newUser.setUsername(user.getUsername());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setUserstatus(user.getUserStatus());
			newUser.setUserType(user.getUsertype());
			userDao.save(newUser);
			JSONObject jo = new JSONObject();
			jo.put("message", "User registration successfull");
			jo.put("Status", "OK");
			return jo.toString();
		} 
		JSONObject jo = new JSONObject();
		jo.put("message", "User registed already exists.");
		jo.put("Status", "OK");
		return jo.toString();

	}
}