/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class STUDENT_EVENT_NOTIFICATION_EOD_STATUS {
    
    String INSTITUTE_ID;
    String STUDENT_ID;
    String BUSINESS_DATE;
    String STATUS;
    String START_TIME;
    String END_TIME;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }
    
    
    
      public ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS> STUDENT_EVENT_NOTIFICATION_EOD_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_EVENT_NOTIFICATION_EOD_STATUS appStatus=new STUDENT_EVENT_NOTIFICATION_EOD_STATUS();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setEND_TIME(record.split("~")[1]);
              appStatus.setINSTITUTE_ID(record.split("~")[2]);
              appStatus.setSTART_TIME(record.split("~")[3]);
              appStatus.setSTATUS(record.split("~")[4]);
              appStatus.setSTUDENT_ID(record.split("~")[5]);
                    
       
              STUDENT_EVENT_NOTIFICATION_EOD_STATUSList.add(appStatus);
          }
          
          
          return STUDENT_EVENT_NOTIFICATION_EOD_STATUSList;
      }
    
}
