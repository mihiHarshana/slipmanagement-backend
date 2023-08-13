package com.mhsoft.model;

import javax.persistence.Column;

public class UserDTO {
	private String username;
	private String password;
	@Column(name="usertype")
	private String userType;
	@Column (name="userstatus")
	private String userStatus;
	private  int id;

	@Column(name = "userfname")
	private String firstname;
	@Column(name = "userlname")
	private String lastname;

	private Long registerdate;


	private Long logindatetime;
	private Long approveddatetime;
	private Long suspendeddatetime;

	public Long getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Long registerdate) {
		this.registerdate = registerdate;
	}

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
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public String  getUserStatus() {
		return userStatus;
	}
	public void setId(int userid) {
		this.id = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(Long logindatetime) {
		this.logindatetime = logindatetime;
	}

	public Long getApproveddatetime() {
		return approveddatetime;
	}

	public void setApproveddatetime(Long approveddatetime) {
		this.approveddatetime = approveddatetime;
	}

	public Long getSuspendeddatetime() {
		return suspendeddatetime;
	}

	public void setSuspendeddatetime(Long suspendeddatetime) {
		this.suspendeddatetime = suspendeddatetime;
	}
}