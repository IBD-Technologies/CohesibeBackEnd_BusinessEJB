/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.summary.classmarksummary;

/**
 *
 * @author DELL
 */
public class ClassMarkFilter {
    String authStatus;
//    String recordStatus;
    String exam;
    String standard;
    String section;
    String subjectID;
    

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

//    public String getRecordStatus() {
//        return recordStatus;
//    }
//
//    public void setRecordStatus(String recordStatus) {
//        this.recordStatus = recordStatus;
//    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
    
    
    
}
