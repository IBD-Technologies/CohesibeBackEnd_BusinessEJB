/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class BATCH_STATUS_HISTORY {
    String INSTITUTE_ID;
    String BATCH_NAME;
    String BUSINESS_DATE;
    String START_TIME;
    String END_TIME;
    String EOD_STATUS;
    String NO_OF_SUCCESS;
    String NO_FAILURES;
    String ERROR;
    String SEQUENCE_NO;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getBATCH_NAME() {
        return BATCH_NAME;
    }

    public void setBATCH_NAME(String BATCH_NAME) {
        this.BATCH_NAME = BATCH_NAME;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
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

    public String getEOD_STATUS() {
        return EOD_STATUS;
    }

    public void setEOD_STATUS(String EOD_STATUS) {
        this.EOD_STATUS = EOD_STATUS;
    }

    public String getNO_OF_SUCCESS() {
        return NO_OF_SUCCESS;
    }

    public void setNO_OF_SUCCESS(String NO_OF_SUCCESS) {
        this.NO_OF_SUCCESS = NO_OF_SUCCESS;
    }

    public String getNO_FAILURES() {
        return NO_FAILURES;
    }

    public void setNO_FAILURES(String NO_FAILURES) {
        this.NO_FAILURES = NO_FAILURES;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getSEQUENCE_NO() {
        return SEQUENCE_NO;
    }

    public void setSEQUENCE_NO(String SEQUENCE_NO) {
        this.SEQUENCE_NO = SEQUENCE_NO;
    }
    
      
     public ArrayList<BATCH_STATUS_HISTORY>convertStringToArrayList(String result){
        
          ArrayList<BATCH_STATUS_HISTORY> BATCH_STATUS_HISTORYList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              BATCH_STATUS_HISTORY appStatus=new BATCH_STATUS_HISTORY();
              
              appStatus.setBATCH_NAME(record.split("~")[0]);
              appStatus.setBUSINESS_DATE(record.split("~")[1]);
              appStatus.setEND_TIME(record.split("~")[2]);
              appStatus.setEOD_STATUS(record.split("~")[3]);
              appStatus.setERROR(record.split("~")[4]);
              appStatus.setINSTITUTE_ID(record.split("~")[5]);
              appStatus.setNO_FAILURES(record.split("~")[6]);
              appStatus.setNO_OF_SUCCESS(record.split("~")[7]);
              appStatus.setSEQUENCE_NO(record.split("~")[8]);
              appStatus.setSTART_TIME(record.split("~")[9]);
              
              
              BATCH_STATUS_HISTORYList.add(appStatus);
          }
          
          
          return BATCH_STATUS_HISTORYList;
      }
      
    
    
}
