/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.student;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_CALENDER;
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
 * @author IBD Technologies
 */
public class SVW_STUDENT_CALENDER_DATASET {
//     ArrayList<SVW_STUDENT_CALENDER> dataset;
    
    
    public ArrayList<SVW_STUDENT_CALENDER> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_CALENDER_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        Map<String,DBRecord>l_studentCalenderMap=null;
        try
        {
        
        l_studentCalenderMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_CALENDER", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_CALENDER_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentCalenderMap,session) ;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_CALENDER> convertDBtoReportObject(Map<String,DBRecord>p_attendanceMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_CALENDER>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_CALENDER convertDBtoReportObject",session);
            
            if(p_attendanceMap!=null&&!p_attendanceMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_attendanceMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_attendanceRecords=recordIterator.next();
                    SVW_STUDENT_CALENDER studentCalender=new SVW_STUDENT_CALENDER();
                    studentCalender.setSTUDENT_ID(l_attendanceRecords.getRecord().get(0).trim());
                    studentCalender.setYEAR(l_attendanceRecords.getRecord().get(1).trim());
                    studentCalender.setMONTH(l_attendanceRecords.getRecord().get(2).trim());
                    studentCalender.setEVENTS(l_attendanceRecords.getRecord().get(3).trim());

                    
                    
                    dbg("studentID"+studentCalender.getSTUDENT_ID() ,session);
                    dbg("year"+studentCalender.getYEAR() ,session);
                    dbg("month"+studentCalender.getMONTH(),session);
                    dbg("events"+studentCalender.getEVENTS(),session);

                    
                    
                    
                    dataset.add(studentCalender);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_CALENDER service=new SVW_STUDENT_CALENDER();
                 
                    service.setSTUDENT_ID(" ");
                    service.setYEAR(" ");
                    service.setMONTH(" ");
                    service.setEVENTS(" ");
               
                    
 dataset.add(service);
                
            }
            
            dbg("end of SVW_STUDENT_CALENDER_DATASET convertDBtoReportObject",session);
            
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
