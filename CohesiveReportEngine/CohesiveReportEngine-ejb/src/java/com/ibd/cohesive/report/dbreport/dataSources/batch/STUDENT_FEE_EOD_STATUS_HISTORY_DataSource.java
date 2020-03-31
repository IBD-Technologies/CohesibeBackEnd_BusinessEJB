/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.report.dbreport.dataSource.dataModels.batch.STUDENT_FEE_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class STUDENT_FEE_EOD_STATUS_HISTORY_DataSource extends BatchDataSource<STUDENT_FEE_EOD_STATUS_HISTORY>{

    String businessDate;
    String instituteID;
    
    @Override
      public List<STUDENT_FEE_EOD_STATUS_HISTORY> fetch()
	{
	
            try
            {

                 businessDate =this.getBusinessDate();
                instituteID=this.getLoginInstitute();
                
//                IBatchDataset batchDataSet=new ReportDependencyInjection().getBatchDataset();
//                return batchDataSet.getNOTIFICATION_EOD_STATUS_DataSet(businessDate);  
        
                List<STUDENT_FEE_EOD_STATUS_HISTORY> resultset=null;
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IBatchDataset batchDataSet=preProcessor.getBatchDataset();
                   
                  String result= batchDataSet.getSTUDENT_FEE_EOD_STATUS_HISTORY_DataSet(businessDate,instituteID);
                  STUDENT_FEE_EOD_STATUS_HISTORY obj=new STUDENT_FEE_EOD_STATUS_HISTORY();
                  resultset= obj.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
            
            
       } catch(NamingException ex){
           System.out.println(ex);
           return null;
       } catch(Exception ex){
           System.out.println(ex);
           return null;
              
    }
        }
}
