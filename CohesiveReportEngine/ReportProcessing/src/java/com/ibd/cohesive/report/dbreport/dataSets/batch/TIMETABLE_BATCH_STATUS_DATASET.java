/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class TIMETABLE_BATCH_STATUS_DATASET {
    public ArrayList<TIMETABLE_BATCH_STATUS> getTableObject(String p_businessDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside TIMETABLE_BATCH_STATUS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        
Map<String,DBRecord>l_tableMap=new HashMap();
        
        l_tableMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_businessDate, "BATCH", "TIMETABLE_BATCH_STATUS", session, dbSession,ismaxVersionRequired);
          
                         
        
         dbg("end of TIMETABLE_BATCH_STATUS_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_tableMap,session) ;

    
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
    
    
    
    
    private ArrayList<TIMETABLE_BATCH_STATUS> convertDBtoReportObject(Map<String,DBRecord>l_tableMap,CohesiveSession session)throws DBProcessingException{
    
       ArrayList<TIMETABLE_BATCH_STATUS> dataset=new ArrayList();
        try{
            
            
            dbg("inside TIMETABLE_BATCH_STATUS_DATASET convertDBtoReportObject",session);
            
           if(l_tableMap!=null&&!l_tableMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=l_tableMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_eodRecords=recordIterator.next();
                    TIMETABLE_BATCH_STATUS eod=new TIMETABLE_BATCH_STATUS();
                    
                    eod.setINSTITUTE_ID(l_eodRecords.getRecord().get(0).trim());
                    eod.setSTANDARD(l_eodRecords.getRecord().get(1).trim());
                    eod.setSECTION(l_eodRecords.getRecord().get(2).trim());
                    eod.setBUSINESS_DATE(l_eodRecords.getRecord().get(3).trim());
                    eod.setSTATUS(l_eodRecords.getRecord().get(4).trim());
                    eod.setNO_OF_SUCCESS(l_eodRecords.getRecord().get(5).trim());
                    eod.setNO_FAILURES(l_eodRecords.getRecord().get(6).trim());
                    eod.setSTART_TIME(l_eodRecords.getRecord().get(7).trim());
                    eod.setEND_TIME(l_eodRecords.getRecord().get(8).trim());
                    
                    
                    

                    dataset.add(eod);
                    
                }
            
            }
            dbg("end of TIMETABLE_BATCH_STATUS_DATASET convertDBtoReportObject",session);
            
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
