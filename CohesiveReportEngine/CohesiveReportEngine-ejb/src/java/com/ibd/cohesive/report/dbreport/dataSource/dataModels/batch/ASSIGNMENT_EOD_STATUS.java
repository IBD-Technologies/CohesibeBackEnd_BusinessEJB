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
public class ASSIGNMENT_EOD_STATUS {
    String INSTITUTE_ID;
    String ASSIGNMENT_ID;
    String BUSINESS_DATE;
    String STATUS;
    String ERROR;
    String NO_OF_SUCCESS;
    String NO_FAILURES;
    String GROUP_ID;
    String START_TIME;
    String END_TIME;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getASSIGNMENT_ID() {
        return ASSIGNMENT_ID;
    }

    public void setASSIGNMENT_ID(String ASSIGNMENT_ID) {
        this.ASSIGNMENT_ID = ASSIGNMENT_ID;
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

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
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
    public ArrayList<ASSIGNMENT_EOD_STATUS>convertStringToArrayList(String result){
        
          ArrayList<ASSIGNMENT_EOD_STATUS> ASSIGNMENT_EOD_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              ASSIGNMENT_EOD_STATUS appStatus=new ASSIGNMENT_EOD_STATUS();
              
              appStatus.setASSIGNMENT_ID(record.split("~")[0]);
              appStatus.setBUSINESS_DATE(record.split("~")[1]);
              appStatus.setEND_TIME(record.split("~")[2]);
              appStatus.setERROR(record.split("~")[3]);
              appStatus.setGROUP_ID(record.split("~")[4]);
              appStatus.setINSTITUTE_ID(record.split("~")[5]);
              appStatus.setNO_FAILURES(record.split("~")[6]);
              appStatus.setNO_OF_SUCCESS(record.split("~")[7]);
              appStatus.setSTART_TIME(record.split("~")[8]);
              appStatus.setSTATUS(record.split("~")[9]);
              
              
              ASSIGNMENT_EOD_STATUSList.add(appStatus);
          }
          
          
          return ASSIGNMENT_EOD_STATUSList;
      }
    
    
}
