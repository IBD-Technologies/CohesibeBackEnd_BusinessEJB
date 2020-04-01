/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.NotificationDetailBusiness;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author ibdtech
 */
public class NotificationDetailBusinessDataSet {
    public ArrayList<NotificationDetailBusiness> getNotificationDetailBusiness(String standard,String section,String p_studentID,String l_instituteID,String notificationType,String fromDate,String toDate,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<NotificationDetailBusiness>   dataset=new ArrayList();

        
        try{
        
        dbg("inside NotificationDetailBusiness_DataSet",session);     
        dbg("standard"+standard,session); 
        dbg("section"+section,session); 
        dbg("p_studentID"+p_studentID,session); 
        dbg("l_instituteID"+l_instituteID,session); 
        dbg("notificationType"+notificationType,session);
        dbg("fromDate"+fromDate,session);
        dbg("toDate"+toDate,session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);
        Map<String,DBRecord>instituteNotificationMap=null;
        IPDataService pds=inject.getPdataservice();
        int k=1;
//        BusinessService bs=appInject.getBusinessService(session);
        
        try{
        
        instituteNotificationMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", session,dbSession);
   
        }catch(DBValidationException ex){
                                dbg("exception in view operation"+ex,session);
                                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
        //                            session.getErrorhandler().log_app_error("BS_VAL_013", l_notificationID);
        //                            throw new BSValidationException("BSValidationException");

                                }else{

                                    throw ex;
                                }
                            }
        
        if(instituteNotificationMap!=null){
        
            dbg("instituteNotificationMap not null",session);
            
                   List<DBRecord>filterRecords= this.getFilteredNotificationRecords(notificationType, standard, section, p_studentID, l_instituteID, instituteNotificationMap, session, appInject, dbSession, fromDate, toDate);
            

                    for(int i=0;i<filterRecords.size();i++){

                        dbg("listForGroup iteration ",session);
                        ArrayList<String>notificationList=filterRecords.get(i).getRecord();
                        String notificationID=notificationList.get(1).trim();
                        String notificationDate=notificationList.get(19).trim();
                        Map<String,DBRecord>notificationStatusMap=null;
                        
                         try{
                        
                        notificationStatusMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification"+i_db_properties.getProperty("FOLDER_DELIMITER")+notificationID, "BATCH", "STUDENT_NOTIFICATION_EOD_STATUS", session, dbSession);
                        
                        
                        }catch(DBValidationException ex){
                                dbg("exception in view operation"+ex,session);
                                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
        //                            session.getErrorhandler().log_app_error("BS_VAL_013", l_notificationID);
        //                            throw new BSValidationException("BSValidationException");

                                }else{

                                    throw ex;
                                }
                            }
                         
                         
                         if(notificationStatusMap!=null){
                             
                             List<DBRecord>filteredStatusRecords=this.getFilteredNotificationStatusRecords(standard, section, p_studentID, l_instituteID, notificationStatusMap, session, appInject, dbSession);
                            Iterator<DBRecord>valueIterator= filteredStatusRecords.iterator();
                             
                             while(valueIterator.hasNext()){
                                 
                                 DBRecord notificationStatusRec=valueIterator.next();
                                 
                                 String l_notificationID=notificationStatusRec.getRecord().get(1).trim();
                                 String l_studentID=notificationStatusRec.getRecord().get(2).trim();
                                 String l_notificationDate=notificationStatusRec.getRecord().get(3).trim();
                                 String l_status=notificationStatusRec.getRecord().get(4).trim();
                                 String[] studentPkey = {l_studentID};
                                 ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                                 String l_standard = l_studentList.get(2).trim();
                                 String l_section = l_studentList.get(3).trim();
                                 String[] l_pkey={notificationID};
                                 DBRecord notificationRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Notification","INSTITUTE","IVW_NOTIFICATION_MASTER", l_pkey, session,dbSession);
                                 String l_notificationType= notificationRecord.getRecord().get(2).trim();
                                 String l_message= notificationRecord.getRecord().get(7).trim();
                                 
                                 NotificationDetailBusiness notificationDetail=new NotificationDetailBusiness();
                                 
                                 notificationDetail.setDate(l_notificationDate);
                                 notificationDetail.setNotificationType(l_notificationType);
                                 notificationDetail.setSection(l_section);
                                 notificationDetail.setStandard(l_standard);
                                 notificationDetail.setStatus(l_status);
                                 notificationDetail.setStudentID(l_studentID);
                                 notificationDetail.setStudentName(bs.getStudentName(l_studentID, l_instituteID, session, dbSession, appInject));
                                 notificationDetail.setMessage(l_message);
                                 notificationDetail.setSerialNumber(Integer.toString(k));
                                 dataset.add(notificationDetail);
                                 k++;
                             }
                             
                         }
                         


            }
        
        }
        
        if(dataset.isEmpty()){
            
            NotificationDetailBusiness notificationDetail=new NotificationDetailBusiness();
                                 
             notificationDetail.setDate(" ");
             notificationDetail.setNotificationType(" ");
             notificationDetail.setSection(" ");
             notificationDetail.setStandard(" ");
             notificationDetail.setStatus(" ");
             notificationDetail.setStudentID(" ");
             notificationDetail.setStudentName(" ");
             notificationDetail.setMessage(" ");
             notificationDetail.setSerialNumber(" ");
             dataset.add(notificationDetail);
        }
        
        
        
        dbg("end of NotificationDetailBusiness_DataSet",session); 
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
       return dataset;
    }
    
    
    
    private List<DBRecord>getFilteredNotificationRecords(String notificationType,String standard,String section,String studentID,String instituteID,Map<String,DBRecord>instituteNotificationMap,CohesiveSession session,AppDependencyInjection appInject,DBSession dbSession,String p_fromDate,String p_toDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
        
        try{
            dbg("inside get filtered notification records",session);
            BusinessService bs=appInject.getBusinessService(session);
            List<DBRecord>notificationRecords=instituteNotificationMap.values().stream().filter(rec->rec.getRecord().get(14).trim().equals("O")&&rec.getRecord().get(15).trim().equals("A")).collect(Collectors.toList());
            String dateFormat=session.getCohesiveproperties().getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            
            
            
            if(notificationType!=null&&!notificationType.isEmpty()){
            
             List<DBRecord>  l_studentList=  notificationRecords.stream().filter(rec->rec.getRecord().get(2).trim().equals(notificationType)).collect(Collectors.toList());
             notificationRecords = new ArrayList<DBRecord>(l_studentList);
             dbg("l_notificationID filter notificationRecords size"+notificationRecords.size(),session);
           }
            
            
            Iterator<DBRecord>notificationRecordIterator=notificationRecords.iterator();
            List<DBRecord> filteredRecords=new ArrayList();
            
            
            while(notificationRecordIterator.hasNext()){
                
                dbg("inside notification record iteraton",session);
                DBRecord notificationRecord=notificationRecordIterator.next();
                String groupID=notificationRecord.getRecord().get(9).trim();
                String l_paymentDate=notificationRecord.getRecord().get(19).trim();
                Date notificationDate=formatter.parse(l_paymentDate);
                Date fromDate=formatter.parse(p_fromDate);
                Date toDate=formatter.parse(p_toDate);
                dbg("notificationDate"+notificationDate,session);
                dbg("fromDate"+fromDate,session);
                dbg("toDate"+toDate,session);
                
                if(notificationDate.compareTo(fromDate)>=0){
                    dbg("from date suceess",session);
                    
                    if(notificationDate.compareTo(toDate)<=0){
                    dbg("to date suceess",session);    
                        
                        if(studentID!=null&&!studentID.isEmpty()){
                    
                    
                        if(bs.checkStudentExistenceInTheGroup(instituteID, studentID, groupID, session, dbSession, appInject)){

                            dbg("student  suceess",session); 
                            filteredRecords.add(notificationRecord);


                        }
                    
                }else  if(standard!=null&&!standard.isEmpty()&&section!=null&&!section.isEmpty()){
                    
                    
                    if(bs.checkClassExistenceInTheGroup(instituteID, standard, section, groupID, session, dbSession, appInject)){

                        dbg("class  suceess",session);
                        filteredRecords.add(notificationRecord);
                        
                        
                    }
                    
                
                }else{
                    dbg("others  suceess",session);
                    filteredRecords.add(notificationRecord);
                }
                        
                        
                     }
                
                
                }
                
                
                
                
                
                
                
                
                
                
                
            }
            
            
            
            
            
            dbg("end of filteredRecords"+filteredRecords.size(),session);
            
            
            return filteredRecords;
            
      }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
      }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    private List<DBRecord> getFilteredNotificationStatusRecords(String standard, String section, String studentID, String instituteID, Map<String, DBRecord> notificationStatusMap, CohesiveSession session, AppDependencyInjection appInject, DBSession dbSession) throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException {

        try {
            dbg("inside getFilteredNotificationStatusRecords", session);
            BusinessService bs = appInject.getBusinessService(session);
            List<DBRecord> notificationStatusRecords = notificationStatusMap.values().stream().collect(Collectors.toList());
            IPDataService pds = appInject.getPdataservice();
            IBDProperties i_db_properties = session.getCohesiveproperties();
            String dateFormat = i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            Iterator<DBRecord> notificationRecordIterator = notificationStatusRecords.iterator();
            List<DBRecord> filteredRecords = new ArrayList();
            dbg("studentID" + studentID, session);
            dbg("standard" + standard, session);
            dbg("section" + section, session);

            while (notificationRecordIterator.hasNext()) {

                DBRecord notificationRecord = notificationRecordIterator.next();
                String l_studentID = notificationRecord.getRecord().get(2).trim();
                dbg("l_studentID" + l_studentID, session);

                if (studentID != null && !studentID.isEmpty()) {

                    if (l_studentID.equals(studentID)) {

                        filteredRecords.add(notificationRecord);

                    }

                } else if (standard != null && !standard.isEmpty() && section != null && !section.isEmpty()) {

                    String[] studentPkey = {l_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                    String l_standard = l_studentList.get(2).trim();
                    String l_section = l_studentList.get(3).trim();

                    if (l_standard.equals(standard) && l_section.equals(section)) {

                        filteredRecords.add(notificationRecord);

                    }

                } else {

                    filteredRecords.add(notificationRecord);
                }

            }

            

            dbg("end of getFilteredNotificationStatusRecords", session);
            return filteredRecords;

        } catch (DBProcessingException ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (DBValidationException ex) {
            dbg(ex, session);
            throw ex;
        } catch (Exception ex) {
            dbg(ex, session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }

    }
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
