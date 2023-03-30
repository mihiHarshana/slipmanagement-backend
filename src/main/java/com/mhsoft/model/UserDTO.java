package com.mhsoft.model;

public class UserDTO {
	private String username;
	private String password;
	private String usertype;
	private String userstatus;

	private  int id;

	public String getUsername() {
		return username;
	}

	public int getId() {return  id;}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	
	public String  getUserStatus() {
		return userstatus;
	}
	public void setId(int userid) {
		this.id = userid;
	}
}