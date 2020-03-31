/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class CLASS_ATTENDANCE_DETAIL {
    String STANDARD;
    String SECTION;
    String YEAR;
    String MONTH;
//    String DAY;
    String STUDENT_ID;
    String ATTENDANCE;
    String VERSION_NUMBER;

    public String getSTANDARD() {
        return STANDARD;
    }

    public void setSTANDARD(String STANDARD) {
        this.STANDARD = STANDARD;
    }

    public String getSECTION() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION = SECTION;
    }

    
    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getATTENDANCE() {
        return ATTENDANCE;
    }

    public void setATTENDANCE(String ATTENDANCE) {
        this.ATTENDANCE = ATTENDANCE;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

//    public String getDAY() {
//        return DAY;
//    }
//
//    public void setDAY(String DAY) {
//        this.DAY = DAY;
//    }
    
    
    public ArrayList<CLASS_ATTENDANCE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<CLASS_ATTENDANCE_DETAIL> CLASS_ATTENDANCE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_ATTENDANCE_DETAIL appStatus=new CLASS_ATTENDANCE_DETAIL();
              
              appStatus.setATTENDANCE(record.split("~")[0]);
              appStatus.setMONTH(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setSTUDENT_ID(record.split("~")[4]);
              appStatus.setVERSION_NUMBER(record.split("~")[5]);
              appStatus.setYEAR(record.split("~")[6]);
              
              
              
              
              
              
           CLASS_ATTENDANCE_DETAILList.add(appStatus);
          }
          
        return CLASS_ATTENDANCE_DETAILList;
           
      
}
    
    
    
    
}
