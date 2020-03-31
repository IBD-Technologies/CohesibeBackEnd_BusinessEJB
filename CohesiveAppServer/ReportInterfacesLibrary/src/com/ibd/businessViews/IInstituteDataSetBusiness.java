/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;

/**
 *
 * @author DELL
 */
public interface IInstituteDataSetBusiness {
    public String MarkComparison_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException;
    public String CLASS_MARK_REPORT_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException;
}
