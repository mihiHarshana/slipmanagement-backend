package com.mhsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bankdetails")
public class DAOBank {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column
        private int userid;
        @Column
        private String bankname;
        @Column
        private String bankcode;
        @Column
        private String bankaccno;
        @Column
        private String bankinst;
        @Column
        private boolean latest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankaccno() {
        return bankaccno;
    }

    public void setBankaccno(String bankaccno) {
        this.bankaccno = bankaccno;
    }

    public String getBankinst() {
        return bankinst;
    }

    public void setBankinst(String bankinst) {
        this.bankinst = bankinst;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }
}
