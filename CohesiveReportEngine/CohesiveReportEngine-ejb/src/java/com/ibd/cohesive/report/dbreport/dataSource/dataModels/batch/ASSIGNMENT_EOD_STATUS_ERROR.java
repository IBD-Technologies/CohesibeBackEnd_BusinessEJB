/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch;

/**
 *
 * @author DELL
 */
public class ASSIGNMENT_EOD_STATUS_ERROR {
    String INSTITUTE_ID;
    String ASSIGNMENT_ID;
    String BUSINESS_DATE;
    String ERROR;

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

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
    
    
}
