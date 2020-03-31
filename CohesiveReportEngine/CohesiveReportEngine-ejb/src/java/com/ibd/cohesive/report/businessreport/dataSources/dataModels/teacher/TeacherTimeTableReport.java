/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class TeacherTimeTableReport {
    String DAY;
    String PERIOD_NO;
    String SUBJECT_NAME;
    String STANDARD;
    String SECTION;
    String startTime;
    String endTime;

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

    public String getSUBJECT_NAME() {
        return SUBJECT_NAME;
    }

    public void setSUBJECT_NAME(String SUBJECT_NAME) {
        this.SUBJECT_NAME = SUBJECT_NAME;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    
    public ArrayList<TeacherTimeTableReport>convertStringToArrayList(String result){
        
          ArrayList<TeacherTimeTableReport>TeacherTimeTableReportList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TeacherTimeTableReport appStatus=new TeacherTimeTableReport();
              
              appStatus.setDAY(record.split("~")[0]);
              appStatus.setEndTime(record.split("~")[1]);
              appStatus.setPERIOD_NO(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSUBJECT_NAME(record.split("~")[5]);
              appStatus.setStartTime(record.split("~")[6]);
              
           TeacherTimeTableReportList.add(appStatus);
          }
          
        return TeacherTimeTableReportList;
           
      
}
    
    
    
    
    
    
    
}
