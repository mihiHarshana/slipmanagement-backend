package com.mhsoft.utils;

public class Withdrawal {

    String agentSystem;
    double amount;
    String  currency;

    public String getAgentSystem() {
        return agentSystem;
    }

    public void setAgentSystem(String agentSystem) {
        this.agentSystem = agentSystem;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPlayerUser() {
        return playerUser;
    }

    public void setPlayerUser(String playerUser) {
        this.playerUser = playerUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    String playerUser;
    String remarks;
}
