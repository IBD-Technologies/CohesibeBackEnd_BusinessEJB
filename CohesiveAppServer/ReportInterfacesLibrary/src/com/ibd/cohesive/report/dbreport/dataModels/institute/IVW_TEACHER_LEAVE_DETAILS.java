/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

/**
 *
 * @author ibdtech
 */
public class IVW_TEACHER_LEAVE_DETAILS {
    String TEACHER_ID;
    String FULL_DAY;        
    String NOON;

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getFULL_DAY() {
        return FULL_DAY;
    }

    public void setFULL_DAY(String FULL_DAY) {
        this.FULL_DAY = FULL_DAY;
    }

    public String getNOON() {
        return NOON;
    }

    public void setNOON(String NOON) {
        this.NOON = NOON;
    }
    
    
    
    
}
