/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.feeNotification;

import com.ibd.cohesive.app.business.batch.fee.IStudentFeeProcessing;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.scheduler.WorkDispatch;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class FeeNotificationProcessing implements IFeeNotificationProcessing{
     AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public FeeNotificationProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
       public void processing (String instituteID,String feeID,String groupID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();   
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        dbg("startTime"+startTime);
        batchUtil=inject.getBatchUtil(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
            
        dbg("inside fee processing");
        

        Map<String,String>column_to_Update=new HashMap();
        column_to_Update.put("9", startTime);
        String[] l_pkeyy={instituteID,feeID,l_businessDate};
        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", l_pkeyy, column_to_Update,session); 
        tc.commit(session, dbSession);
                
              studentIdentification(instituteID,l_businessDate,feeID,groupID) ;   
              
              String[] l_pkey={"FeeManagement"};
              ArrayList<String> batchConfigRecord= pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "BATCH_CONFIG",l_pkey );
              int no_of_threads=Integer.parseInt(batchConfigRecord.get(8).trim());
              
              dbg("no_of_threads"+no_of_threads);
              
            if(no_of_threads==0){

                
                sequentialProcessing(instituteID,feeID,l_businessDate);
            }else{

                parallelProcessing(instituteID,feeID,l_businessDate,no_of_threads);
            }
                    
                    
         batchUtil.feeNotificationProcessingSuccessHandler(instituteID, l_businessDate, feeID, inject, session, dbSession);
        dbg("end of fee processing");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String feeID,String groupID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,feeID,groupID,l_businessDate);
       
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
    
     private void sequentialProcessing(String instituteID,String feeID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null;   
      ITransactionControlService tc=null;
      try{
           dbg("inside feeNotification -->sequential");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           tc=inject.getTransactionControlService();
           IStudentFeeNotificationProcessing studentOtherActProcessing=inject.getStudentFeeNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           batchUtil=inject.getBatchUtil(session);
      
            Map<String,DBRecord>studentEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"FeeNotification"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
//            List<DBRecord>filteredStudentList=studentEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("F")||rec.getRecord().get(4).trim().equals("W")).collect(Collectors.toList());

//            dbg("studentList"+filteredStudentList.size());
            
             List<DBRecord>studentList=new ArrayList();
             Iterator<DBRecord>studentIterator=studentEodMap.values().iterator();
                     
           
//               for(int i=0;i<filteredStudentList.size();i++){
              while(studentIterator.hasNext()){

                   DBRecord feeRecord=studentIterator.next();
                   String l_studentID=feeRecord.getRecord().get(2).trim();
                   String l_status=feeRecord.getRecord().get(4).trim();
                   dbg("l_studentID"+l_studentID);
                   dbg("l_status"+l_status);

                   if(l_status.equals("F")){

                        int feeHistoryMax=batchUtil.getStudentFeeHistoryMaxSequence(l_businessDate, instituteID, feeID, l_studentID, inject, session, dbSession);

                        if(feeHistoryMax<=5){

                            studentList.add(feeRecord);
                        }

                   }
                   else if(l_status.equals("W")){

                       studentList.add(feeRecord);
                   }


               }
            
            
            
                    for(int j=0;j<studentList.size();j++){
                         DBRecord studentFeeRecord=studentList.get(j);
                         String l_studentID=studentList.get(j).getRecord().get(2).trim();
                         String l_status=studentList.get(j).getRecord().get(4).trim();
                         
                         
                         dbg("l_studentID"+l_studentID);
                         dbg("l_status"+l_status);
                         
                         if(l_status.equals("F")){
                             
//                             batchUtil.getStudentFeeHistoryMaxSequence(l_businessDate, instituteID, feeID, l_studentID, inject, session, dbSession);
                             
                             
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("5", "W");
                            String[]l_pkey={instituteID,feeID,l_studentID,l_businessDate};
                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"FeeNotification"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                            batchUtil.moveStudentFeeNotificationToHistory(instituteID, feeID, l_studentID, l_businessDate, studentFeeRecord, session, dbSession, inject);
                         }
                         tc.commit(session, dbSession);
                         
                         dbg("before studentAssProcessing call");
                         studentOtherActProcessing.processing(instituteID,l_studentID,feeID,l_businessDate,session);
                         dbg("after studentAssProcessing call");
                         
                   }
    
                    
                    
    
         batchUtil.feeNotificationProcessingSuccessHandler(instituteID, l_businessDate, feeID, inject, session, dbSession);
        dbg("end of feeNotification -->sequential");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }
     }
    
    private void parallelProcessing(String instituteID,String feeID,String l_businessDate,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      BatchUtil batchUtil=null; 
      ITransactionControlService tc=null;
      try{
           dbg("inside feeNotificationProcessing -->parallel");
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
            IStudentFeeNotificationProcessing studentOtherActProcessing=inject.getStudentFeeNotificationProcessing();
           IDBTransactionService dbts=inject.getDBTransactionService();
           tc=inject.getTransactionControlService();
           batchUtil=inject.getBatchUtil(session);
          Map<String,DBRecord>studentEodMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"FeeNotification"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
//            List<DBRecord>filteredStudentList=studentEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("F")||rec.getRecord().get(4).trim().equals("W")).collect(Collectors.toList());

//            dbg("studentList"+filteredStudentList.size());
            
             List<DBRecord>studentList=new ArrayList();
             Iterator<DBRecord>studentIterator=studentEodMap.values().iterator();
                     
           
//               for(int i=0;i<filteredStudentList.size();i++){
              while(studentIterator.hasNext()){

                   DBRecord feeRecord=studentIterator.next();
                   String l_studentID=feeRecord.getRecord().get(2).trim();
                   String l_status=feeRecord.getRecord().get(4).trim();
                   dbg("l_studentID"+l_studentID);
                   dbg("l_status"+l_status);

                   if(l_status.equals("F")){

                        int feeHistoryMax=batchUtil.getStudentFeeHistoryMaxSequence(l_businessDate, instituteID, feeID, l_studentID, inject, session, dbSession);

                        if(feeHistoryMax<=5){

                            studentList.add(feeRecord);
                        }

                   }
                   else if(l_status.equals("W")){

                       studentList.add(feeRecord);
                   }


               }
           
           
           
           
           
           
           Future<String>[] Result;
           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
           boolean dispatchFailed=false;
           int no_of_execution;
           
           if(studentList.size()%no_of_threads==0){
               
               no_of_execution=studentList.size()/no_of_threads;
               
           }else{
               
               no_of_execution=(studentList.size()/no_of_threads)+1;
           }
           
           dbg("no_of_execution"+no_of_execution);
           int executionCount=0;
           
           while(executionCount<no_of_execution){
           
           int startIndex=executionCount*no_of_threads;
           int endIndex=startIndex+no_of_threads-1;
           dbg("start index"+startIndex);
           dbg("endIndex"+endIndex);
           Result=new  Future[no_of_threads];
           
           //job submission starts
           for(int i=startIndex;i<=endIndex;i++){
               int j=0;
               if(i<studentList.size()){
                if(startIndex==i){
                       j=0;
                   }else{
                       j++;
                   }
                DBRecord studentFeeRecord=studentList.get(i);
                String l_studentID=studentList.get(i).getRecord().get(2).trim();
                String l_status=studentList.get(i).getRecord().get(4).trim();
                dbg("l_studentID"+l_studentID);
                dbg("l_status"+l_status);
                         
                         if(l_status.equals("F")){
                            Map<String,String>column_to_Update=new HashMap();
                            column_to_Update=new HashMap();
                            column_to_Update.put("5", "W");
                            String[]l_pkey={instituteID,feeID,l_studentID,l_businessDate};
                            dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"FeeNotification"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
                            
                            batchUtil.moveStudentFeeNotificationToHistory(instituteID, feeID, l_studentID, l_businessDate, studentFeeRecord, session, dbSession, inject);
                            tc.commit(session, dbSession);
                         }
                         
                
                try{
                    
                  Result[j]= (Future<String>)studentOtherActProcessing.parallelProcessing(instituteID,l_studentID, feeID, l_businessDate);
               
                } catch (EJBException ex) {
                   dispatchFailed=true;
                }
                
                
                if( !dispatchFailed){
                    
                     WorkDispatch dispatch=new WorkDispatch(l_studentID,"WIP");
                     dispatchedJobs.put(j, dispatch);
                }
                
                dbg("dispatchFailed"+dispatchFailed);
             dispatchFailed=false;   
           
           }
           } 
           //job submission ends
           
             //job status monitoring starts   
             
              int threadCount=0;
               boolean comeOutLoop=false;
               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
               if(dispatchedJobs.size()>0){
               
               while(comeOutLoop==false){
                    
                 dbg("while(comeOutLoop==false)");  
                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
                 WorkDispatch dispatch=null;
                   
                 
                 while(keyIterator.hasNext()){
                     
                     Integer key= keyIterator.next();
                    
                     dbg("key"+key);
                     dbg("dispatchedJobs.get(key).getResult()"+dispatchedJobs.get(key).getResult());
                     
                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
                     
                         dbg("Result[key].isDone()");
                     if(Result[key].isDone()){
                         
                            try {
                                  dispatch=  dispatchedJobs.get(key);
                                  String result=Result[key].get();
                                  dispatch.setResult(result);
                                  
                                } catch (ExecutionException ex) {
                                    dbg(ex);
                                  dispatch.setResult("Fail");
                                  
                                  throw ex;
                                  
                                }
                         threadCount++;
                     }
                     }
                 }
                 
                 if(threadCount==dispatchedJobs.size())
                  comeOutLoop=true;
               }
               Result=null;
               dispatchedJobs.clear();
               } 
           executionCount++;
               }
           
          batchUtil.feeNotificationProcessingSuccessHandler(instituteID, l_businessDate, feeID, inject, session, dbSession);
        
          dbg("end of feeNotificationProcessing -->parellel");
        }catch(DBValidationException ex){
//          tc.rollBack(session, dbSession);
          batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
//          tc.rollBack(session, dbSession);
          batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
//           tc.rollBack(session, dbSession);
           batchUtil.feeNotificationProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
        }
         
     }
    
    
    @Asynchronous
   public Future<String> parallelProcessing(String instituteID,String feeID,String groupID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,feeID,groupID,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}

 private void studentIdentification(String instituteID,String l_businessDate,String feeID,String p_groupID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       
       try{
           dbg("inside studentIdentification");
           IBDProperties i_db_properties=session.getCohesiveproperties();
           IDBTransactionService dbts=inject.getDBTransactionService();
           BusinessService bs=inject.getBusinessService(session);
           ITransactionControlService tc=inject.getTransactionControlService();
           ArrayList<String>l_studentOfTheGroup=bs.getStudentsOfTheGroup(instituteID, p_groupID, session, dbSession, inject);
           dbg("l_studentOfTheGroup"+l_studentOfTheGroup.size());        
           
                for(int j=0;j<l_studentOfTheGroup.size();j++){

                     String l_studentID=l_studentOfTheGroup.get(j).trim();
                     dbg("l_studentID"+l_studentID);
                   
                    float balanceAmount=   bs.getBalanceAmount(instituteID, l_studentID, feeID, session, dbSession, inject);
                     
                    if(balanceAmount!=0){
                    
                     
                    try{ 
                     
                         dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"FeeNotification"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID, "BATCH", 336,instituteID,feeID,l_studentID,l_businessDate,"W"," "," "," ");   
                          tc.commit(session, dbSession);
                     }catch(DBValidationException ex){
                        tc.rollBack(session, dbSession);
                       if(!ex.toString().contains("DB_VAL_009")){
                           throw ex;
                       }
                    }
                    
                    
                    
                    }
//                  }
                }
          
           
          dbg("end of studentIdentification");
        }catch(DBValidationException ex){
          throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
       
       
   }
//    public void processing (String instituteID,String feeID,String groupID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       BatchUtil batchUtil=null;
//       boolean l_session_created_now=false;
//       ITransactionControlService tc=null;
//       try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();   
//        tc=inject.getTransactionControlService();
//        BusinessService bs=inject.getBusinessService(session);
//        String startTime=bs.getCurrentDateTime();
//        dbg("startTime"+startTime);
//        batchUtil=inject.getBatchUtil(session);
//        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IDBTransactionService dbts=inject.getDBTransactionService();
//            
//        dbg("inside fee processing");
//        
//        Map<String,String>column_to_Update=new HashMap();
//        column_to_Update.put("9", startTime);
//        String[] l_pkeyy={instituteID,feeID,l_businessDate};
//        dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "FEE_NOTIFICATION_EOD_STATUS", l_pkeyy, column_to_Update,session); 
//        tc.commit(session, dbSession);
//                
//              studentIdentification(instituteID,l_businessDate,feeID,groupID) ;   
//              
//              String[] l_pkey={"FeeManagement"};
//              DBRecord batchConfigRecord= readBuffer.readRecord("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "BATCH_CONFIG",l_pkey ,session, dbSession);
//              int no_of_threads=Integer.parseInt(batchConfigRecord.getRecord().get(8).trim());
//              
//              dbg("no_of_threads"+no_of_threads);
//              
//            if(no_of_threads==0){
//
//                sequentialProcessing(instituteID,feeID,l_businessDate);
//            }else{
//
//                parallelProcessing(instituteID,feeID,l_businessDate,no_of_threads);
//            }
//                    
//                    
//         batchUtil.feeProcessingSuccessHandler(instituteID, l_businessDate, feeID, inject, session, dbSession);
//        dbg("end of fee processing");
//        }catch(DBValidationException ex){
////          tc.rollBack(session, dbSession);
//          batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }catch(DBProcessingException ex){
//          dbg(ex);
////          tc.rollBack(session, dbSession);
//          batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
////           tc.rollBack(session, dbSession);
//           batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }finally{
//               if(l_session_created_now){    
//                  dbSession.clearSessionObject();
//                  session.clearSessionObject();
//               }
//           }
//}
//
//    public void processing(String instituteID,String feeID,String groupID,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      
//       CohesiveSession tempSession = this.session;
//       
//       try{
//           
//           this.session=session;
//           processing(instituteID,feeID,groupID,l_businessDate);
//       
//      }catch(DBValidationException ex){
//          throw ex;
//      }catch(BSValidationException ex){
//          throw ex;     
//      }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(BSProcessingException ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }finally {
//           this.session=tempSession;
//            
//        } 
//   }
//    
//     private void sequentialProcessing(String instituteID,String feeID,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      BatchUtil batchUtil=null;   
//      ITransactionControlService tc=null;
//      try{
//           dbg("inside feeProcessing -->sequential");
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           tc=inject.getTransactionControlService();
//           IStudentFeeProcessing studentOtherActProcessing=inject.getStudentFeeProcessing();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           batchUtil=inject.getBatchUtil(session);
//      
//            Map<String,DBRecord>studentEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
//            List<DBRecord>studentList=studentEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("F")||rec.getRecord().get(4).trim().equals("W")).collect(Collectors.toList());
//            
//            dbg("studentList"+studentList.size());
//            
//                    for(int j=0;j<studentList.size();j++){
//                         DBRecord studentFeeRecord=studentList.get(j);
//                         String l_studentID=studentList.get(j).getRecord().get(2).trim();
//                         String l_status=studentList.get(j).getRecord().get(4).trim();
//                         
//                         dbg("l_studentID"+l_studentID);
//                         dbg("l_status"+l_status);
//                         
//                         if(l_status.equals("F")){
//                            Map<String,String>column_to_Update=new HashMap();
//                            column_to_Update=new HashMap();
//                            column_to_Update.put("5", "W");
//                            String[]l_pkey={instituteID,feeID,l_studentID,l_businessDate};
//                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//                            batchUtil.moveStudentFeeRecordToHistory(instituteID,feeID,l_studentID,l_businessDate,studentFeeRecord, session, dbSession, inject);
//                         }
//                         tc.commit(session, dbSession);
//                         
//                         dbg("before studentAssProcessing call");
//                         studentOtherActProcessing.processing(instituteID,l_studentID,feeID,l_businessDate,session);
//                         dbg("after studentAssProcessing call");
//                         
//                   }
//    
//    
//         batchUtil.feeProcessingSuccessHandler(instituteID, l_businessDate, feeID, inject, session, dbSession);
//        dbg("end of feeProcessing -->sequential");
//        }catch(DBValidationException ex){
////          tc.rollBack(session, dbSession);
//          batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }catch(DBProcessingException ex){
//          dbg(ex);
////          tc.rollBack(session, dbSession);
//          batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
////           tc.rollBack(session, dbSession);
//           batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }
//     }
//    
//    private void parallelProcessing(String instituteID,String feeID,String l_businessDate,int no_of_threads)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//      BatchUtil batchUtil=null; 
//      ITransactionControlService tc=null;
//      try{
//           dbg("inside feeProcessing -->parallel");
//           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//            IStudentFeeProcessing studentOtherActProcessing=inject.getStudentFeeProcessing();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           tc=inject.getTransactionControlService();
//           batchUtil=inject.getBatchUtil(session);
//           Map<String,DBRecord>studentEodMap=readBuffer.readTable("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", session, dbSession);
//           List<DBRecord>studentList=studentEodMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("F")||rec.getRecord().get(4).trim().equals("W")).collect(Collectors.toList());
//           dbg("studentList size"+studentList.size());
//           Future<String>[] Result;
//           Map<Integer,WorkDispatch>dispatchedJobs=new HashMap();
//           boolean dispatchFailed=false;
//           int no_of_execution;
//           
//           if(studentList.size()%no_of_threads==0){
//               
//               no_of_execution=studentList.size()/no_of_threads;
//               
//           }else{
//               
//               no_of_execution=(studentList.size()/no_of_threads)+1;
//           }
//           
//           dbg("no_of_execution"+no_of_execution);
//           int executionCount=0;
//           
//           while(executionCount<no_of_execution){
//           
//           int startIndex=executionCount*no_of_threads;
//           int endIndex=startIndex+no_of_threads-1;
//           dbg("start index"+startIndex);
//           dbg("endIndex"+endIndex);
//           Result=new  Future[no_of_threads];
//           
//           //job submission starts
//           for(int i=startIndex;i<=endIndex;i++){
//               int j=0;
//               if(i<studentList.size()){
//                if(startIndex==i){
//                       j=0;
//                   }else{
//                       j++;
//                   }
//                String l_studentID=studentList.get(i).getRecord().get(2).trim();
//                String l_status=studentList.get(i).getRecord().get(4).trim();
//                dbg("l_studentID"+l_studentID);
//                dbg("l_status"+l_status);
//                         
//                         if(l_status.equals("F")){
//                            Map<String,String>column_to_Update=new HashMap();
//                            column_to_Update=new HashMap();
//                            column_to_Update.put("5", "W");
//                            String[]l_pkey={instituteID,feeID,l_studentID,l_businessDate};
//                            dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_FEE_NOTIFICATION_EOD_STATUS", l_pkey, column_to_Update,session); 
//                            tc.commit(session, dbSession);
//                         }
//                         
//                
//                try{
//                    
//                  Result[j]= (Future<String>)studentOtherActProcessing.parallelProcessing(instituteID,l_studentID, feeID, l_businessDate);
//               
//                } catch (EJBException ex) {
//                   dispatchFailed=true;
//                }
//                
//                
//                if( !dispatchFailed){
//                    
//                     WorkDispatch dispatch=new WorkDispatch(l_studentID,"WIP");
//                     dispatchedJobs.put(j, dispatch);
//                }
//                
//                dbg("dispatchFailed"+dispatchFailed);
//             dispatchFailed=false;   
//           
//           }
//           } 
//           //job submission ends
//           
//             //job status monitoring starts   
//             
//              int threadCount=0;
//               boolean comeOutLoop=false;
//               dbg("dispatchedJobs.size()"+dispatchedJobs.size());
//               if(dispatchedJobs.size()>0){
//               
//               while(comeOutLoop==false){
//                    
//                 dbg("while(comeOutLoop==false)");  
//                 Iterator<Integer> keyIterator=  dispatchedJobs.keySet().iterator();
//                 WorkDispatch dispatch=null;
//                   
//                 
//                 while(keyIterator.hasNext()){
//                     
//                     Integer key= keyIterator.next();
//                    
//                     dbg("key"+key);
//                     dbg("dispatchedJobs.get(key).getResult()"+dispatchedJobs.get(key).getResult());
//                     
//                     if(dispatchedJobs.get(key).getResult().equals("WIP")){
//                     
//                         dbg("Result[key].isDone()");
//                     if(Result[key].isDone()){
//                         
//                            try {
//                                  dispatch=  dispatchedJobs.get(key);
//                                  String result=Result[key].get();
//                                  dispatch.setResult(result);
//                                  
//                                } catch (ExecutionException ex) {
//                                    dbg(ex);
//                                  dispatch.setResult("Fail");
//                                  
//                                  throw ex;
//                                  
//                                }
//                         threadCount++;
//                     }
//                     }
//                 }
//                 
//                 if(threadCount==dispatchedJobs.size())
//                  comeOutLoop=true;
//               }
//               Result=null;
//               dispatchedJobs.clear();
//               } 
//           executionCount++;
//               }
//           
//          batchUtil.feeProcessingSuccessHandler(instituteID, l_businessDate, feeID, inject, session, dbSession);
//        
//          dbg("end of feeProcessing -->parellel");
//        }catch(DBValidationException ex){
////          tc.rollBack(session, dbSession);
//          batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }catch(DBProcessingException ex){
//          dbg(ex);
////          tc.rollBack(session, dbSession);
//          batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }catch(Exception ex){
//           dbg(ex);
////           tc.rollBack(session, dbSession);
//           batchUtil.feeProcessingErrorHandler(instituteID, l_businessDate, feeID, ex, inject, session, dbSession);
//        }
//         
//     }
//    
//    
//    @Asynchronous
//   public Future<String> parallelProcessing(String instituteID,String feeID,String groupID,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//   
//    try{   
//    
//        processing(instituteID,feeID,groupID,l_businessDate);
//        
//              return new AsyncResult<String>("Success");
//
//       
//        }catch(Exception ex){
//           dbg(ex);
//           return new AsyncResult<String>("Fail");
//     }
//    
//}
//
// private void studentIdentification(String instituteID,String l_businessDate,String p_asiignmentID,String p_groupID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
//       
//       try{
//           dbg("inside studentIdentification");
//           IBDProperties i_db_properties=session.getCohesiveproperties();
//           IDBTransactionService dbts=inject.getDBTransactionService();
//           BusinessService bs=inject.getBusinessService(session);
//           ITransactionControlService tc=inject.getTransactionControlService();
//           ArrayList<String>l_studentOfTheGroup=bs.getStudentsOfTheGroup(instituteID, p_groupID, session, dbSession, inject);
//           dbg("l_studentOfTheGroup"+l_studentOfTheGroup.size());        
//           
//           
//                for(int j=0;j<l_studentOfTheGroup.size();j++){
//
//                     String l_studentID=l_studentOfTheGroup.get(j).trim();
//                     dbg("l_studentID"+l_studentID);
//                   
//                    try{ 
//                     
//                     dbts.createRecord(session,"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", 120,instituteID,p_asiignmentID,l_studentID,l_businessDate,"W"," "," "," ");   
//                      tc.commit(session, dbSession);
//                     }catch(DBValidationException ex){
//                   
//                       if(!ex.toString().contains("DB_VAL_009")){
//                           throw ex;
//                       }
//                    }
//                }
//          
//           
//          dbg("end of studentIdentification");
//        }catch(DBValidationException ex){
//          throw ex;
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(Exception ex){
//           dbg(ex);
//           throw new BSProcessingException(ex.toString());
//     }
//       
//       
//   }



 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
