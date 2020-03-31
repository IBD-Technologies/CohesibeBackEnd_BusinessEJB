/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class STUDENT_NOTIFICATION_EOD_STATUS_HISTORY {
    String INSTITUTE_ID;
    String NOTIFICATION_ID;
    String STUDENT_ID;
    String BUSINESS_DATE;
    String STATUS;
    String ERROR;
    String START_TIME;
    String END_TIME;
    String SEQUENCE_NO;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
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

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
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

    public String getSEQUENCE_NO() {
        return SEQUENCE_NO;
    }

    public void setSEQUENCE_NO(String SEQUENCE_NO) {
        this.SEQUENCE_NO = SEQUENCE_NO;
    }
  public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_HISTORY>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_HISTORY> STUDENT_NOTIFICATION_EOD_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_NOTIFICATION_EOD_STATUS_HISTORY appStatus=new STUDENT_NOTIFICATION_EOD_STATUS_HISTORY();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setEND_TIME(record.split("~")[1]);
              appStatus.setERROR(record.split("~")[2]);
              appStatus.setINSTITUTE_ID(record.split("~")[3]);
              appStatus.setNOTIFICATION_ID(record.split("~")[4]);
              appStatus.setSEQUENCE_NO(record.split("~")[5]);
              appStatus.setSTART_TIME(record.split("~")[6]);
              appStatus.setSTATUS(record.split("~")[7]);
              appStatus.setSTUDENT_ID(record.split("~")[8]);
              
              
              STUDENT_NOTIFICATION_EOD_STATUSList.add(appStatus);
          }
          
          
          return STUDENT_NOTIFICATION_EOD_STATUSList;
      
      }   
     
    
}
