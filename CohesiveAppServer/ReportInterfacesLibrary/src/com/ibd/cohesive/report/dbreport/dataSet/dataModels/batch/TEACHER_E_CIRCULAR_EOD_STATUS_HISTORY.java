/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY {
    String INSTITUTE_ID;
    String E_CIRCULAR_ID;
    String TEACHER_ID;
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

    public String getE_CIRCULAR_ID() {
        return E_CIRCULAR_ID;
    }

    public void setE_CIRCULAR_ID(String E_CIRCULAR_ID) {
        this.E_CIRCULAR_ID = E_CIRCULAR_ID;
    }

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
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
    
    public ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>convertStringToArrayList(String result){
        
          ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY> TEACHER_E_CIRCULAR_EOD_STATUS_HISTORYList=new ArrayList();
          
         
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY appStatus=new TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY();
              
              appStatus.setE_CIRCULAR_ID(record.split("~")[0]);
              appStatus.setBUSINESS_DATE(record.split("~")[1]);
              appStatus.setEND_TIME(record.split("~")[2]);
              appStatus.setERROR(record.split("~")[3]);
              appStatus.setINSTITUTE_ID(record.split("~")[4]);
              appStatus.setSEQUENCE_NO(record.split("~")[5]);
              appStatus.setSTART_TIME(record.split("~")[6]);
              appStatus.setSTATUS(record.split("~")[7]);
              appStatus.setTEACHER_ID(record.split("~")[8]);
              
              
              
              TEACHER_E_CIRCULAR_EOD_STATUS_HISTORYList.add(appStatus);
          }
          
          
          return TEACHER_E_CIRCULAR_EOD_STATUS_HISTORYList;
      
      }  
}
