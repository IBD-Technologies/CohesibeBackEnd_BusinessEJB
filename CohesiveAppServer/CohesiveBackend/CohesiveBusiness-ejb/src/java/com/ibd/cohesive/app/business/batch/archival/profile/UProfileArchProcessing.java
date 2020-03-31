/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.profile;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class UProfileArchProcessing implements IUProfileArchProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public UProfileArchProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
  public void processing (String userID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       
       try{
             session.createSessionObject();
             dbSession.createDBsession(session);
             l_session_created_now=session.isI_session_created_now();   
             IDBReadBufferService readBuffer=inject.getDBReadBufferService();
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             BusinessService bs=inject.getBusinessService(session);
             batchUtil=inject.getBatchUtil(session);
             IMetaDataService mds=inject.getMetadataservice();
             ITransactionControlService tc=inject.getTransactionControlService();
             dbg("user Profile arch processing");
             String currentDate=bs.getCurrentDate();
             int tableId=mds.getTableMetaData("USER", "UVW_USER_PROFILE", session).getI_Tableid();
             Map<String,DBRecord> userProfileMap=null;
             boolean tableExistence=true;
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={userID,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update=new HashMap();
                    column_to_Update.put("4", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "USER_PROFILE_ARCH_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
             
             
             
             int checkerDateColId=mds.getColumnMetaData("USER", "UVW_USER_PROFILE", "CHECKER_DATE_STAMP", session).getI_ColumnID();
             dbg("checkerDateColId"+checkerDateColId);       
             
             int recStatColId=mds.getColumnMetaData("USER", "UVW_USER_PROFILE", "RECORD_STATUS", session).getI_ColumnID();
             dbg("recStatColId"+recStatColId);
             
             try{
                 
                 
               userProfileMap=  readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER", "UVW_USER_PROFILE", session, dbSession, "Yes");
                 
                 
             }catch(DBValidationException ex){
                 
                 if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                     
                     tableExistence=false;
                 }else{
                     throw ex;
                 }
             }
             dbg("tableExistence"+tableExistence); 
             
             if(tableExistence){
                 
                Iterator<String>keyIterator= userProfileMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    ArrayList<String>value=userProfileMap.get(key).getRecord();
                    String checkerDateStamp=value.get(checkerDateColId-1).trim();
                    String recStatus=value.get(recStatColId-1).trim();
                    dbg("key"+key);
                    dbg("checkerDateStamp"+checkerDateStamp);
                    dbg("recStatus"+recStatus);
                    
                    if(recStatus.equals("D")){
                    
                    if(bs.compareCheckerDateApp(checkerDateStamp,"Profile",session,dbSession,inject)){
                        
                        String[] l_pkey=key.split("~");
                        
                        dbg("before delete record call for key"+key);
                        
                        dbts.deleteRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_USER_PROFILE", l_pkey, session);
                        dbg("after delete record call for key"+key);
                     
                        String[] recordValues=getRecordValues(value);
                        dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_User_"+currentDate,"USER",tableId, recordValues);
                        
                    }
                    }
                }
                 
             }
             
             tc.commit(session, dbSession);
             dbg("end of user wise processing");
        batchUtil.userProfileArchProcessingSuccessHandler(businessDate, userID, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.userProfileArchProcessingErrorHandler(businessDate, userID, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.userProfileArchProcessingErrorHandler(businessDate, userID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.userProfileArchProcessingErrorHandler(businessDate, userID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.userProfileArchProcessingErrorHandler(businessDate, userID, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
      
  }
  
  private String[] getRecordValues(ArrayList<String> recordList)throws BSProcessingException{
      
      try{
          dbg("inside getRecordValues");
          
          
         String[] recordValues={recordList.get(0),recordList.get(1),recordList.get(2),recordList.get(3),recordList.get(4),recordList.get(5),recordList.get(6),recordList.get(7),recordList.get(8),recordList.get(9),recordList.get(10),recordList.get(11),recordList.get(12),recordList.get(13)};
          
          
          
          dbg("end of getRecordValues");
          return recordValues;
      }catch(Exception ex){
           dbg(ex);
        throw new BSProcessingException(ex.toString());   
      }
      
  }
  
 
  
   public void processing(String userID,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(userID,businessDate);
       
      }catch(DBValidationException ex){
          throw ex;
      }catch(BSValidationException ex){
          throw ex;     
      }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }finally {
           this.session=tempSession;
            
        } 
   }
   
   @Asynchronous
   public Future<String> parallelProcessing(String userID,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(userID,businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
  
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
