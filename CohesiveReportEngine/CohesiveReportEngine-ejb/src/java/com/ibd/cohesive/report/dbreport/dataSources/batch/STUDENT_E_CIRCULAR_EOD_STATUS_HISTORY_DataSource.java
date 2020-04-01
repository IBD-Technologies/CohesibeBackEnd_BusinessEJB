/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSource extends BatchDataSource<STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY> fetch()
	{
	
            try
            {

                businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet(businessDate);  
                  ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet(businessDate, instituteID);
                  STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY obj=new STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY();
                 resultset= obj.convertStringToArrayList(result);
                 
                 
                 return resultset;
                }else{
                    return null;
                }
            
//            return null;
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }     
}
