package com.mhsoft.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	private boolean status; 
	private String type;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String password, boolean status, String type) {
		this.setUsername(username);
		this.setPassword(password);
		this.setStatus(status);
		this.setType(type);
		
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean getStatus () {
		return status;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public void setStatus (Boolean status ) {
		this.status = status;
	}
}