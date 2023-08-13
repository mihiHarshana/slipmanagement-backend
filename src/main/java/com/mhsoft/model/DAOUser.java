package com.mhsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String usertype;
    @Column
    private String userstatus;
    @Column
    private String userfname;
    @Column
    private String userlname;

    @Column
    private Long registerdate;

    @Column
    private Long logindatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserstatus() {
        return userstatus;
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

    @Column
    private Long approveddatetime;

    @Column
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

    public String getUserStatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public void setUserType(String userType) {
        this.usertype = userType;
    }

    public int getUserid() {
        return id;
    }

    public void setUserId(int userId) {
        this.id = userId;
    }

    public String getUserfname() {
        return userfname;
    }

    public String getUserlname() {
        return userlname;
    }

    public void setUserfname(String userfname) {
        this.userfname = userfname;
    }

    public void setUserlname(String userlname) {
        this.userlname = userlname;
    }
}