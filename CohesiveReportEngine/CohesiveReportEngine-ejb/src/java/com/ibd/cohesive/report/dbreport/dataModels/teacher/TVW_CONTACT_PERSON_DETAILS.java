/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.teacher;

import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherMarksSummary;
import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class TVW_CONTACT_PERSON_DETAILS {
    String TEACHER_ID;
    String CP_ID;
    String CP_NAME;
    String CP_RELATIONSHIP;
    String CP_OCCUPATION;
    String CP_MAILID;
    String CP_CONTACTNO;
    String IMAGE_PATH;
    String VERSION_NUMBER;    

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getCP_ID() {
        return CP_ID;
    }

    public void setCP_ID(String CP_ID) {
        this.CP_ID = CP_ID;
    }

    public String getCP_NAME() {
        return CP_NAME;
    }

    public void setCP_NAME(String CP_NAME) {
        this.CP_NAME = CP_NAME;
    }

    public String getCP_RELATIONSHIP() {
        return CP_RELATIONSHIP;
    }

    public void setCP_RELATIONSHIP(String CP_RELATIONSHIP) {
        this.CP_RELATIONSHIP = CP_RELATIONSHIP;
    }

    public String getCP_OCCUPATION() {
        return CP_OCCUPATION;
    }

    public void setCP_OCCUPATION(String CP_OCCUPATION) {
        this.CP_OCCUPATION = CP_OCCUPATION;
    }

    public String getCP_MAILID() {
        return CP_MAILID;
    }

    public void setCP_MAILID(String CP_MAILID) {
        this.CP_MAILID = CP_MAILID;
    }

    public String getCP_CONTACTNO() {
        return CP_CONTACTNO;
    }

    public void setCP_CONTACTNO(String CP_CONTACTNO) {
        this.CP_CONTACTNO = CP_CONTACTNO;
    }

    public String getIMAGE_PATH() {
        return IMAGE_PATH;
    }

    public void setIMAGE_PATH(String IMAGE_PATH) {
        this.IMAGE_PATH = IMAGE_PATH;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
    public ArrayList<TVW_CONTACT_PERSON_DETAILS>convertStringToArrayList(String result){
        
          ArrayList<TVW_CONTACT_PERSON_DETAILS> TVW_CONTACT_PERSON_DETAILSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TVW_CONTACT_PERSON_DETAILS appStatus=new TVW_CONTACT_PERSON_DETAILS();
              
              appStatus.setCP_CONTACTNO(record.split("~")[0]);
              appStatus.setCP_ID(record.split("~")[1]);
              appStatus.setCP_MAILID(record.split("~")[2]);
              appStatus.setCP_NAME(record.split("~")[3]);
              appStatus.setCP_OCCUPATION(record.split("~")[4]);
              appStatus.setCP_RELATIONSHIP(record.split("~")[5]);             
              appStatus.setIMAGE_PATH(record.split("~")[6]);
              appStatus.setTEACHER_ID(record.split("~")[7]);
              appStatus.setVERSION_NUMBER(record.split("~")[8]);
              
              
           TVW_CONTACT_PERSON_DETAILSList.add(appStatus);
          }
          
        return TVW_CONTACT_PERSON_DETAILSList;
           
      
}
    
    
    
}
