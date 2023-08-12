package com.mhsoft.utils;

public class RegisterUser {
 private int agentcode;
 private  String firstname;
 private  String lastname;
 private  String password;
 private  String userStatus;
 private  String userType;
 private  String username;

 private int id;

   public int getAgentcode() {
      return agentcode;
   }

   public void setAgentcode(int agentcode) {
      this.agentcode = agentcode;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUserStatus() {
      return userStatus;
   }

   public void setUserStatus(String userStatus) {
      this.userStatus = userStatus;
   }

   public String getUserType() {
      return userType;
   }

   public void setUserType(String userType) {
      this.userType = userType;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
