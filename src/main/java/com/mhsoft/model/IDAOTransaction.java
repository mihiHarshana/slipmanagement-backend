package com.mhsoft.model;

import java.time.LocalDateTime;



public interface IDAOTransaction {

     int trid=0;

     LocalDateTime trdatetime = null;
     String trtype = null;
     double tramount =0;
     String trstatus = null;
     int userid = 0;

     public int getTrid();

     public void setTrid(int trid);

     public LocalDateTime getTrdatetime();
     public void setTrdatetime(LocalDateTime trdatetime);

     public String getTrtype();

     public void setTrtype(String trtype);

     public double getTramount();

     public void setTramount(double tramount);

     public String getTrstatus();

     public void setTrstatus();

     public int getUserid();

     public void setUserid(int userid);

}


