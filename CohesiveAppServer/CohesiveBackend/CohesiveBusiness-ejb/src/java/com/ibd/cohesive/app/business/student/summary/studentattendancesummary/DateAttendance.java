/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.summary.studentattendancesummary;

import com.ibd.cohesive.app.business.student.studentattendanceservice.PeriodAttendance;

/**
 *
 * @author DELL
 */
public class DateAttendance {
    String date;
    public PeriodAttendance period[];
    String makerID;
 String checkerID;
 String makerDateStamp;
 String checkerDateStamp;
 String recordStatus;
 String authStatus;
 String versionNumber;
 String makerRemarks;
 String checkerRemarks;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PeriodAttendance[] getPeriod() {
        return period;
    }

    public void setPeriod(PeriodAttendance[] period) {
        this.period = period;
    }

    public String getMakerID() {
        return makerID;
    }

    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    public String getCheckerID() {
        return checkerID;
    }

    public void setCheckerID(String checkerID) {
        this.checkerID = checkerID;
    }

    public String getMakerDateStamp() {
        return makerDateStamp;
    }

    public void setMakerDateStamp(String makerDateStamp) {
        this.makerDateStamp = makerDateStamp;
    }

    public String getCheckerDateStamp() {
        return checkerDateStamp;
    }

    public void setCheckerDateStamp(String checkerDateStamp) {
        this.checkerDateStamp = checkerDateStamp;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getMakerRemarks() {
        return makerRemarks;
    }

    public void setMakerRemarks(String makerRemarks) {
        this.makerRemarks = makerRemarks;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerRemarks(String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }
    
    
            
}
