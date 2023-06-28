package com.mhsoft.model;

import javax.persistence.*;

@Entity
@Table(name="agentcode")
public class DAOAgentCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "agentid")
    private int agentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentid) {
        this.agentId = agentid;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    @Column (name="agentcode")
    private String agentCode;

    @Column
    private boolean latest;

}
