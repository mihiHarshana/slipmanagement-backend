package com.mhsoft.model;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class DAOTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Long trdatetime;
    private String trtype;
    private double tramount;
    private String status;
    private int userid;
  //  private double trdisputeamount;
    private String utrnumber;
    private String filename;
    private String customerremarks;
    private String agentremarks;
    private String ccagentremarks;
    private Long slipdate;
    private double trdisputeamount;

    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAgentsystem() {
        return agentsystem;
    }

    public void setAgentsystem(String agentSystem) {
        this.agentsystem = agentSystem;
    }

    public String getPlayeruser() {
        return playeruser;
    }

    public void setPlayeruser(String playerUser) {
        this.playeruser = playerUser;
    }

    private String agentsystem;

    private String playeruser;

   // private String remarks;

    private String slip;

/*    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }*/

    public String getSlip() {
        return slip;
    }

    public void setSlip(String slip) {
        this.slip = slip;
    }

    public String getAgentremarks() {
        return agentremarks;
    }

    public void setAgentremarks(String agentremarks) {
        this.agentremarks = agentremarks;
    }

    public String getCcagentremarks() {
        return ccagentremarks;
    }

    public void setCcagentremarks(String ccagentremarks) {
        this.ccagentremarks = ccagentremarks;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String fileName) {
        this.filename = fileName;
    }

    public double getTrdisputeamount() {
        return trdisputeamount;
    }

    public void setTrdisputeamount(double trDisputeAmount) {
        this.trdisputeamount = trDisputeAmount;
    }

    public String getUtrnumber() {
        return utrnumber;
    }

    public void setUtrnumber(String utrNumber) {
        this.utrnumber = utrNumber;
    }

   public String getCustomerremarks() {
       return customerremarks;
   }

   public void setCustomerremarks(String customerRemarks) {
       this.customerremarks = customerRemarks;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public Long getTrdatetime() {
        return trdatetime;
    }

    public void setTrdatetime(Long trdatetime) {
        this.trdatetime = trdatetime;
    }

    public String getTrtype() {
        return trtype;
    }

    public void setTrtype(String trtype) {
        this.trtype = trtype;
    }

    public double getTramount() {
        return tramount;
    }

    public void setTramount(double tramount) {
        this.tramount = tramount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String trstatus) {
        this.status = trstatus;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Long getSlipdate() {
        return slipdate;
    }

    public void setSlipdate(Long slipdate) {
        this.slipdate = slipdate;
    }
}


