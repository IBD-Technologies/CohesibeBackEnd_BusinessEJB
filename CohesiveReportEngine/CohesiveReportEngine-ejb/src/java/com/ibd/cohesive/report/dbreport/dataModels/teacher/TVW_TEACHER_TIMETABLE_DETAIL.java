/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.teacher;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class TVW_TEACHER_TIMETABLE_DETAIL {
    String TEACHER_ID;
    String DAY;
    String PERIOD_NO;
    String SUBJECT_ID;
    String STANDARD;
    String SECTION;
    String VERSION_NUMBER;
    String DAY_NUMBER;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getPERIOD_NO() {
        return PERIOD_NO;
    }

    public void setPERIOD_NO(String PERIOD_NO) {
        this.PERIOD_NO = PERIOD_NO;
    }

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

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

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getDAY_NUMBER() {
        return DAY_NUMBER;
    }

    public void setDAY_NUMBER(String DAY_NUMBER) {
        this.DAY_NUMBER = DAY_NUMBER;
    }
    
    
    public ArrayList<TVW_TEACHER_TIMETABLE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<TVW_TEACHER_TIMETABLE_DETAIL> TVW_TEACHER_TIMETABLE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TVW_TEACHER_TIMETABLE_DETAIL appStatus=new TVW_TEACHER_TIMETABLE_DETAIL();
              
              appStatus.setDAY(record.split("~")[0]);
              appStatus.setDAY_NUMBER(record.split("~")[1]);
              appStatus.setPERIOD_NO(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSUBJECT_ID(record.split("~")[5]);             
              appStatus.setTEACHER_ID(record.split("~")[6]);
              appStatus.setVERSION_NUMBER(record.split("~")[7]);
              
              
              
           TVW_TEACHER_TIMETABLE_DETAILList.add(appStatus);
          }
          
        return TVW_TEACHER_TIMETABLE_DETAILList;
           
      
}
    
    
    
    
    
    
    
}
