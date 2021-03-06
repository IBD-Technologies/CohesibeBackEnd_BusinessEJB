/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.institute;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_NOTIFICATION_MASTER;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ibdtech
 */
public class IVW_NOTIFICATION_MASTER_DATASET {
    
    public ArrayList<IVW_NOTIFICATION_MASTER> getTableObject(String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside IVW_NOTIFICATION_MASTER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        Map<String,DBRecord>l_contentTypeMap=null;
        try
        {
        l_contentTypeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE", "IVW_NOTIFICATION_MASTER", session, dbSession);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
        
         dbg("end of IVW_NOTIFICATION_MASTER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_contentTypeMap,session) ;
        
        
        
        
        
       
    
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
        
    }
    
    
    
    
    private ArrayList<IVW_NOTIFICATION_MASTER> convertDBtoReportObject(Map<String,DBRecord>p_contentTypeMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<IVW_NOTIFICATION_MASTER>dataset=new ArrayList();
        try{
            
            
            dbg("inside IVW_NOTIFICATION_MASTER_DATASET convertDBtoReportObject",session);
            
            if(p_contentTypeMap!=null&&!p_contentTypeMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_contentTypeMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_contentTypeRecords=recordIterator.next();
                    IVW_NOTIFICATION_MASTER contentType=new IVW_NOTIFICATION_MASTER();
                 
                    contentType.setINSTITUTE_ID(l_contentTypeRecords.getRecord().get(0).trim());
                    contentType.setNOTIFICATION_ID(l_contentTypeRecords.getRecord().get(1).trim());
                    contentType.setNOTIFICATION_TYPE(l_contentTypeRecords.getRecord().get(2).trim());
                    contentType.setNOTIFICATION_FREQUENCY(l_contentTypeRecords.getRecord().get(3).trim());
                    contentType.setDATE(l_contentTypeRecords.getRecord().get(4).trim());
                    contentType.setDAY(l_contentTypeRecords.getRecord().get(5).trim());
                    contentType.setMONTH(l_contentTypeRecords.getRecord().get(6).trim());
                    contentType.setMESSAGE(l_contentTypeRecords.getRecord().get(7).trim());
                    contentType.setMEDIA_COMMUNICATION(l_contentTypeRecords.getRecord().get(8).trim());
                    contentType.setASSIGNEE(l_contentTypeRecords.getRecord().get(9).trim());
                    contentType.setMAKER_ID(l_contentTypeRecords.getRecord().get(10).trim());
                    contentType.setCHECKER_ID(l_contentTypeRecords.getRecord().get(11).trim());
                    contentType.setMAKER_DATE_STAMP(l_contentTypeRecords.getRecord().get(12).trim());
                    contentType.setCHECKER_DATE_STAMP(l_contentTypeRecords.getRecord().get(13).trim());
                    contentType.setRECORD_STATUS(l_contentTypeRecords.getRecord().get(14).trim());
                    contentType.setAUTH_STATUS(l_contentTypeRecords.getRecord().get(15).trim());
                    contentType.setVERSION_NUMBER(l_contentTypeRecords.getRecord().get(16).trim());
                    contentType.setMAKER_REMARKS(l_contentTypeRecords.getRecord().get(17).trim());
                    contentType.setCHECKER_REMARKS(l_contentTypeRecords.getRecord().get(18).trim());
                    contentType.setINSTANT(l_contentTypeRecords.getRecord().get(19).trim());
                    contentType.setEMAIL(l_contentTypeRecords.getRecord().get(20).trim());
                    contentType.setMOBILE_NO(l_contentTypeRecords.getRecord().get(21).trim());
                    
                    
                    
                    

                    dataset.add(contentType);
                    
                }
            
            }
            
            
                else
            {
                IVW_NOTIFICATION_MASTER service=new IVW_NOTIFICATION_MASTER();
                 
                    service.setINSTITUTE_ID(" ");
                    service.setNOTIFICATION_ID(" ");
                    service.setNOTIFICATION_TYPE(" ");
                    service.setNOTIFICATION_FREQUENCY(" ");
                    service.setDATE(" ");
                    service.setDAY(" ");
                    service.setMONTH(" ");
                    service.setMESSAGE(" ");
                    service.setMEDIA_COMMUNICATION(" ");
                    service.setASSIGNEE(" ");
                    service.setMAKER_ID(" ");
                    service.setCHECKER_ID(" ");
                    service.setMAKER_DATE_STAMP(" ");
                    service.setCHECKER_DATE_STAMP(" ");
                    service.setRECORD_STATUS(" ");
                    service.setAUTH_STATUS(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setMAKER_REMARKS(" ");
                    service.setCHECKER_REMARKS(" ");
                    service.setINSTANT(" ");
                    service.setEMAIL(" ");
                    service.setMOBILE_NO(" ");
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of IVW_NOTIFICATION_MASTER_DATASET convertDBtoReportObject",session);
            
        }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        return dataset;
        
    }
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
  
    
    
}
