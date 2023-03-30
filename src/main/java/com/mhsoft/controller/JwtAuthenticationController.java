package com.mhsoft.controller;

import java.util.Objects;

import javax.print.attribute.standard.DateTimeAtCompleted;

import com.mhsoft.utils.Utils;
import com.mysql.cj.util.Util;
import org.apache.tomcat.jni.Time;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.context.support.UiApplicationContextUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.model.JwtRequest;
import com.mhsoft.model.JwtResponse;
import com.mhsoft.model.UserDTO;
import com.mhsoft.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	/*
	 * @RequestMapping(value = "/authenticate", method = RequestMethod.POST) public
	 * ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest
	 * authenticationRequest) throws Exception {
	 * 
	 * authenticate(authenticationRequest.getUsername(),
	 * authenticationRequest.getPassword());
	 * 
	 * final UserDetails userDetails =
	 * userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	 * 
	 * final String token = jwtTokenUtil.generateToken(userDetails);
	 * 
	 * return ResponseEntity.ok(new JwtResponse(token)); }
	 */
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

			final String token = jwtTokenUtil.generateToken(userDetails);


			JSONObject jo = new JSONObject();
			jo.put("token", token);
			jo.put("mobile", userDetails.getUsername());
			jo.put("logintime", System.currentTimeMillis());

			return jo.toString();
		} catch (Exception e){
			Utils utils = new Utils();
			return  utils.JsonMessage("Invalid Credentails", HttpStatus.ACCEPTED);
		}

	}

	/*
	 * @RequestMapping(value = "/register", method = RequestMethod.POST) public
	 * ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
	 * return ResponseEntity.ok(userDetailsService.save(user)); }
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveUser(@RequestBody UserDTO user) {
		String jo = userDetailsService.save(user);

		return jo;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}