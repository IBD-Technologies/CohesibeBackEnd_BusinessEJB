/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.user;

//import com.ibd.cohesive.report.dbreport.dataModels.teacher.UVW_CLASS_ENTITY_ROLEMAPPING;
import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class UVW_CLASS_ENTITY_ROLEMAPPING {
    String USER_ID;
    String ROLE_ID;
    String STANDARD;
    String SECTION;
    String VERSION_NUMBER;
    String INSTITUTE_ID;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }
    
    

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
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
    
    public ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING>convertStringToArrayList(String result){
        
          ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING> UVW_CLASS_ENTITY_ROLEMAPPINGList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_CLASS_ENTITY_ROLEMAPPING appStatus=new UVW_CLASS_ENTITY_ROLEMAPPING();
              
              appStatus.setINSTITUTE_ID(record.split("~")[0]);
              appStatus.setROLE_ID(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setUSER_ID(record.split("~")[4]);
              appStatus.setVERSION_NUMBER(record.split("~")[5]);             
                     
              
              
              
              
           UVW_CLASS_ENTITY_ROLEMAPPINGList.add(appStatus);
          }
          
        return UVW_CLASS_ENTITY_ROLEMAPPINGList;
           
      
}
    
    
    

}
