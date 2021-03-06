/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class SVW_STUDENT_OTHER_ACTIVITY {
String STUDENT_ID;
String ACTIVITY_ID;
String ACTIVITY_NAME;
String ACTIVITY_TYPE;
String LEVEL;
String VENUE;
String DATE;
String RESULT;
String INTREST;
String MAKER_ID;
String CHECKER_ID;
String MAKER_DATE_STAMP;
String CHECKER_DATE_STAMP;
String RECORD_STATUS;
String AUTH_STATUS;
String VERSION_NUMBER;
String MAKER_REMARKS;
String CHECKER_REMARKS;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getACTIVITY_NAME() {
        return ACTIVITY_NAME;
    }

    public void setACTIVITY_NAME(String ACTIVITY_NAME) {
        this.ACTIVITY_NAME = ACTIVITY_NAME;
    }

    public String getACTIVITY_TYPE() {
        return ACTIVITY_TYPE;
    }

    public void setACTIVITY_TYPE(String ACTIVITY_TYPE) {
        this.ACTIVITY_TYPE = ACTIVITY_TYPE;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getVENUE() {
        return VENUE;
    }

    public void setVENUE(String VENUE) {
        this.VENUE = VENUE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getMAKER_ID() {
        return MAKER_ID;
    }

    public void setMAKER_ID(String MAKER_ID) {
        this.MAKER_ID = MAKER_ID;
    }

    public String getCHECKER_ID() {
        return CHECKER_ID;
    }

    public void setCHECKER_ID(String CHECKER_ID) {
        this.CHECKER_ID = CHECKER_ID;
    }

    public String getMAKER_DATE_STAMP() {
        return MAKER_DATE_STAMP;
    }

    public void setMAKER_DATE_STAMP(String MAKER_DATE_STAMP) {
        this.MAKER_DATE_STAMP = MAKER_DATE_STAMP;
    }

    public String getCHECKER_DATE_STAMP() {
        return CHECKER_DATE_STAMP;
    }

    public void setCHECKER_DATE_STAMP(String CHECKER_DATE_STAMP) {
        this.CHECKER_DATE_STAMP = CHECKER_DATE_STAMP;
    }

    public String getRECORD_STATUS() {
        return RECORD_STATUS;
    }

    public void setRECORD_STATUS(String RECORD_STATUS) {
        this.RECORD_STATUS = RECORD_STATUS;
    }

    public String getAUTH_STATUS() {
        return AUTH_STATUS;
    }

    public void setAUTH_STATUS(String AUTH_STATUS) {
        this.AUTH_STATUS = AUTH_STATUS;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getMAKER_REMARKS() {
        return MAKER_REMARKS;
    }

    public void setMAKER_REMARKS(String MAKER_REMARKS) {
        this.MAKER_REMARKS = MAKER_REMARKS;
    }

    public String getCHECKER_REMARKS() {
        return CHECKER_REMARKS;
    }

    public void setCHECKER_REMARKS(String CHECKER_REMARKS) {
        this.CHECKER_REMARKS = CHECKER_REMARKS;
    }

    public String getACTIVITY_ID() {
        return ACTIVITY_ID;
    }

    public void setACTIVITY_ID(String ACTIVITY_ID) {
        this.ACTIVITY_ID = ACTIVITY_ID;
    }

    public String getINTREST() {
        return INTREST;
    }

    public void setINTREST(String INTREST) {
        this.INTREST = INTREST;
    }
    
       public ArrayList<SVW_STUDENT_OTHER_ACTIVITY>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_OTHER_ACTIVITY> SVW_STUDENT_OTHER_ACTIVITYList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_OTHER_ACTIVITY appStatus=new SVW_STUDENT_OTHER_ACTIVITY();
              
              appStatus.setACTIVITY_ID(record.split("~")[0]);
              appStatus.setACTIVITY_NAME(record.split("~")[1]);
              appStatus.setACTIVITY_TYPE(record.split("~")[2]);
              appStatus.setAUTH_STATUS(record.split("~")[3]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[4]);
              appStatus.setCHECKER_ID(record.split("~")[5]);
              appStatus.setCHECKER_REMARKS(record.split("~")[6]);
              appStatus.setDATE(record.split("~")[7]);
              appStatus.setINTREST(record.split("~")[8]);
              appStatus.setLEVEL(record.split("~")[9]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[10]);
              appStatus.setMAKER_ID(record.split("~")[11]);
              appStatus.setMAKER_REMARKS(record.split("~")[12]);
              appStatus.setRECORD_STATUS(record.split("~")[13]);
              appStatus.setRESULT(record.split("~")[14]);
              appStatus.setSTUDENT_ID(record.split("~")[15]);
              appStatus.setVENUE(record.split("~")[16]);
              appStatus.setVERSION_NUMBER(record.split("~")[17]);
             
                          
              
              
              
              
           SVW_STUDENT_OTHER_ACTIVITYList.add(appStatus);
          }
          
        return SVW_STUDENT_OTHER_ACTIVITYList;
           
      
}
    
}
