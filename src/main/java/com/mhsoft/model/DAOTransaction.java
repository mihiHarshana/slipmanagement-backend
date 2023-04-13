package com.mhsoft.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "transaction")
public class DAOTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trid;

    private Time trdatetime;
    private String trtype;
    private double tramount;
    private String trstatus;
    private int userid;

    public int getTrid() {
        return trid;
    }

    public void setTrid(int trid) {
        this.trid = trid;
    }

    public Time getTrdatetime() {
        return trdatetime;
    }

    public void setTrdatetime(Time trdatetime) {
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

    public String getTrstatus() {
        return trstatus;
    }

    public void setTrstatus(String trstatus) {
        this.trstatus = trstatus;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}


