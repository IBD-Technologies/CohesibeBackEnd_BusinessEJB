/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.student;

import com.ibd.businessViews.IStudentDataSetBusiness;
import com.ibd.cohesive.report.businessreport.dataSources.dataModels.student.StudentOtherActivityDetail;
import com.ibd.cohesive.report.dbreport.dataSources.student.StudentDataSource;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class StudentOtherActivityDetail_DataSource extends StudentDataSource<StudentOtherActivityDetail>{
    String fileName;
    String instanceID;
    
    @Override
      public List<StudentOtherActivityDetail> fetch()
	{
	
            try
            {
                //parseParameters();
                fileName = this.getStudentId();
                instanceID =this.getLoginInstitute();
//                
//                IStudentDataSetBusiness studentDataSetBusiness= new ReportDependencyInjection().getStudentDataSetBusiness();
//                return studentDataSetBusiness.getStudentOtherActivityDetail_DataSet(fileName, instanceID);
        
                
                ArrayList<StudentOtherActivityDetail> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IStudentDataSetBusiness batchDataSet=preProcessor.getStudentDataSetBusiness();
                   
                  String result= batchDataSet.getStudentOtherActivityDetail_DataSet(fileName, instanceID);
                  StudentOtherActivityDetail obj=new StudentOtherActivityDetail();
                  resultset= obj.convertStringToArrayList(result);
                  
                 
 
                   return resultset;
                }else{
                    return null;
                }
        
            
            
       } catch(NamingException ex){
           ex.printStackTrace();
           return null;
//             throw new EJBException();
       } catch(Exception ex){
           ex.printStackTrace();
           return null;
              
    }
        }  
}
