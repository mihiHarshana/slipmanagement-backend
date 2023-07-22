package com.mhsoft.model;


import javax.persistence.*;

@Entity
@Table(name = "bankdetails")
public class DAOBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int userid;

    public String getBranchname() {
        return branchName;
    }
    public void setBranchname(String branchname) {
        this.branchName = branchname;
    }
    @Column (name="bankname")
    private String bankName;
    @Column (name="bankcode")
    private String bankCode;
    @Column (name = "bankaccno")
    private String accountNo;
    @Column (name = "bankinst")
    private String instructions;
    @Column (name="branchname")
    private String branchName;
    @Column
    private boolean latest;
    @Column
    private boolean defaultacc;

    @Column (name="validto")
    private Long validTo;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getValidTo() {
        return validTo;
    }

    public void setValidTo(Long validto) {
        this.validTo = validto;
    }

    public boolean isDefaultacc() {
        return defaultacc;
    }

    public void setDefaultacc(boolean defaultacc) {
        this.defaultacc = defaultacc;
    }

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankname) {
        this.bankName = bankname;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankcode) {
        this.bankCode = bankcode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String bankaccno) {
        this.accountNo = bankaccno;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String bankinst) {
        this.instructions = bankinst;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }
}
