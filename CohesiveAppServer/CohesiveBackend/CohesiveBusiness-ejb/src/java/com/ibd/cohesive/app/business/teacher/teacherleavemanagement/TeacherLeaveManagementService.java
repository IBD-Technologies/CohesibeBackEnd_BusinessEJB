/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teacherleavemanagement;

import com.ibd.businessViews.ITeacherLeaveManagementService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.lock.IBusinessLockService;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
//@Local(ITeacherLeaveManagementService.class)
@Remote(ITeacherLeaveManagementService.class)
@Stateless
public class TeacherLeaveManagementService implements ITeacherLeaveManagementService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public TeacherLeaveManagementService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession= new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
    @Override
    public JsonObject EJBprocessing(JsonObject requestJson)
    {     
        JsonObject response =null;
      try{
          response = processing(requestJson);
      }
      catch(Exception e)
      { 
         dbg(e);
         throw new EJBException(e);
      }  
      return response;
     }
    
    @Override
    public String EJBprocessing(String request)
    {     
        JsonObject responseJson =null;
        JsonObject requestJson=null;
        String response =null;
        
      try{
          requestJson=inject.getJsonUtil().getJsonObject(request);
          responseJson = processing(requestJson);
          response = responseJson.toString();
          
      }
      catch(Exception e)
      { 
         if (session.getDebug()!=null)
          dbg(e);
         throw new EJBException(e);
      }  
      return response;
     }
    
     public JsonObject processing(JsonObject requestJson)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean l_validation_status=true;
       boolean l_session_created_now=false;
       BusinessService  bs;
       Parsing parser;
       ExceptionHandler exHandler;
       JsonObject jsonResponse=null;
       JsonObject clonedResponse=null;
       JsonObject clonedJson=null;
       String l_lockKey=null;
       IBusinessLockService businessLock=null;
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       businessLock=inject.getBusinessLockService();
       dbg("inside TeacherLeaveManagementService--->processing");
       dbg("TeacherLeaveManagementService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
       TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
       String l_teacherID=teacherLeaveManagement.getTeacherID();
       String l_from=teacherLeaveManagement.getFrom();
       String l_to=teacherLeaveManagement.getTo();
       l_lockKey=l_teacherID+"~"+l_from+"~"+l_to;
       if(!businessLock.getBusinessLock(request, l_lockKey, session)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
       
       BusinessEJB<ITeacherLeaveManagementService>teacherLeaveManagementEJB=new BusinessEJB();
       teacherLeaveManagementEJB.set(this);
      
       exAudit=bs.getExistingAudit(teacherLeaveManagementEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,teacherLeaveManagementEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,teacherLeaveManagementEJB);
              tc.commit(session,dbSession);
              dbg("commit is called in teacher leaveManagement");
        }
       }catch(NamingException ex){
            dbg(ex);
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
       }catch(BSValidationException ex){
          if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSValidationException");
          }else{
              throw ex;
          }
       }catch(DBValidationException ex){
           if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"DBValidationException");
          }else{
              throw ex;
          }
       }catch(DBProcessingException ex){
            dbg(ex);
            if(l_session_created_now){
            exHandler = inject.getErrorHandle(session);
            jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"DBProcessingException");
            }else{
              throw ex;
          }
       }catch(BSProcessingException ex){
           dbg(ex);
           if(l_session_created_now){
           exHandler = inject.getErrorHandle(session);
           jsonResponse=exHandler.exceptionHandler(l_session_created_now, session, dbSession, inject, request, clonedJson,"BSProcessingException");
          }else{
              throw ex;
          }
      }
        finally{
           exAudit=null;
            if(l_lockKey!=null){
               businessLock.removeBusinessLock(request, l_lockKey, session);
            }
           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
                dbg("Response"+jsonResponse.toString());
                clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
                BSValidation bsv=inject.getBsv(session);
//                if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
          clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
/*BSProcessingException ex=new BSProcessingException("response contains special characters");
                   dbg(ex);
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
                   throw ex;
                }*/
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
           }
       
        
       return clonedResponse; 
    }
    
    public JsonObject processing(JsonObject requestJson,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
    CohesiveSession tempSession = this.session;
    JsonObject response =null;
    try{
        this.session=session;
        response =processing(requestJson);
     }catch(DBValidationException ex){
                 
        throw ex;
        }catch(BSValidationException ex){
                 
        throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
           this.session=tempSession;
        }
 return response;
    }

    private  void buildBOfromReq(JsonObject p_request)throws BSProcessingException,DBProcessingException{
      TeacherLeaveManagement teacherLeaveManagement=new TeacherLeaveManagement();
      RequestBody<TeacherLeaveManagement> reqBody = new RequestBody<TeacherLeaveManagement>(); 
           
      try{
      dbg("inside teacher leaveManagement buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      String l_operation=request.getReqHeader().getOperation();
      teacherLeaveManagement.setTeacherID(l_body.getString("teacherID"));
      teacherLeaveManagement.setTeacherName(l_body.getString("teacherName"));
      teacherLeaveManagement.setFrom(l_body.getString("from"));
      teacherLeaveManagement.setTo(l_body.getString("to"));

      
      if(!(l_operation.equals("View"))){
          teacherLeaveManagement.setType(l_body.getString("type"));
          teacherLeaveManagement.setReason(l_body.getString("reason"));
          teacherLeaveManagement.setFromNoon(l_body.getString("fromNoon"));
          teacherLeaveManagement.setToNoon(l_body.getString("toNoon"));
      
      }
        reqBody.set(teacherLeaveManagement);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
   

    public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
        
    try{ 
        dbg("inside stident leaveManagement create"); 
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        String l_teacherID=teacherLeaveManagement.getTeacherID();
        String l_from=teacherLeaveManagement.getFrom();
        String l_to=teacherLeaveManagement.getTo();
        String l_type=teacherLeaveManagement.getType();
        String l_reason=teacherLeaveManagement.getReason();
        String l_fromNoon=teacherLeaveManagement.getFromNoon();
        String l_toNoon=teacherLeaveManagement.getToNoon();
        
        dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE",56,l_teacherID,l_from,l_to,l_type,l_reason,l_makerID,l_checkerID,l_makerDateStamp,l_checkerDateStamp,l_recordStatus,l_authStatus,l_versionNumber,l_makerRemarks,l_checkerRemarks,l_fromNoon,l_toNoon);

        
        
        dbg("end of teacher leaveManagement create"); 
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }
    
     

    
    public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
        
     try{ 
        dbg("inside teacher leaveManagement--->auth update"); 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String  l_checkerRemarks=request.getReqAudit().getCheckerRemarks();
        String l_teacherID=teacherLeaveManagement.getTeacherID();
        String l_from=teacherLeaveManagement.getFrom();
        String l_to=teacherLeaveManagement.getTo(); 
        String[] l_primaryKey={l_teacherID,l_from,l_to};
        
         Map<String,String>  l_column_to_update=new HashMap();
         l_column_to_update.put("7", l_checkerID);
         l_column_to_update.put("9", l_checkerDateStamp);
         l_column_to_update.put("11", l_authStatus);
         l_column_to_update.put("14", l_checkerRemarks);
        
                        dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE","TVW_TEACHER_LEAVE_MANAGEMENT", l_primaryKey, l_column_to_update, session);
         dbg("end of teacher leaveManagement--->auth update");          
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }
    public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
        
       Map<String,String> l_column_to_update;
                
       try{ 
        IDBTransactionService dbts=inject.getDBTransactionService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        String l_instituteID=request.getReqHeader().getInstituteID();   
        String l_makerID=request.getReqAudit().getMakerID();
        String l_checkerID=request.getReqAudit().getCheckerID();
        String l_makerDateStamp=request.getReqAudit().getMakerDateStamp();
        String l_checkerDateStamp=request.getReqAudit().getCheckerDateStamp();
        String l_recordStatus=request.getReqAudit().getRecordStatus();
        String l_authStatus=request.getReqAudit().getAuthStatus();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        String l_makerRemarks=request.getReqAudit().getMakerRemarks();
        String l_checkerRemarks=request.getReqAudit().getCheckerRemarks();   
        
        String l_teacherID=teacherLeaveManagement.getTeacherID();
        String l_from=teacherLeaveManagement.getFrom();
        String l_to=teacherLeaveManagement.getTo();
        String l_type=teacherLeaveManagement.getType();
        String l_reason=teacherLeaveManagement.getReason();
        String l_fromNoon=teacherLeaveManagement.getFromNoon();
        String l_toNoon=teacherLeaveManagement.getToNoon();
        
        l_column_to_update= new HashMap();
        l_column_to_update.put("1", l_teacherID);
        l_column_to_update.put("2", l_from);
        l_column_to_update.put("3", l_to);
        l_column_to_update.put("4", l_type);
        l_column_to_update.put("5", l_reason);
        l_column_to_update.put("6", l_makerID);
        l_column_to_update.put("7", l_checkerID);
        l_column_to_update.put("8", l_makerDateStamp);
        l_column_to_update.put("9", l_checkerDateStamp);
        l_column_to_update.put("10", l_recordStatus);
        l_column_to_update.put("11", l_authStatus);
        l_column_to_update.put("12", l_versionNumber);
        l_column_to_update.put("13", l_makerRemarks);
        l_column_to_update.put("14", l_checkerRemarks);
        l_column_to_update.put("15", l_fromNoon);
        l_column_to_update.put("16", l_toNoon);
         
                   String[] l_primaryKey={l_teacherID,l_from,l_to};
                   dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE","TVW_TEACHER_LEAVE_MANAGEMENT", l_primaryKey, l_column_to_update, session);   
                  
        
        
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception".concat(ex.toString()));
        }
    }

    
    public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
        try{
        dbg("inside teacher leaveManagement delete");    
        IDBTransactionService dbts=inject.getDBTransactionService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        String l_teacherID=teacherLeaveManagement.getTeacherID();
        String l_from=teacherLeaveManagement.getFrom();
        String l_to=teacherLeaveManagement.getTo(); 
        String[] l_primaryKey={l_teacherID,l_from,l_to};
        
                        dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE","TVW_TEACHER_LEAVE_MANAGEMENT", l_primaryKey, session);
            dbg("end of teacher leaveManagement delete");
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
             throw new DBProcessingException(ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
     
        }
        
       }

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside teacher leaveManagement--->view");    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        String l_instituteID=request.getReqHeader().getInstituteID();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String l_teacherID=teacherLeaveManagement.getTeacherID();
        String l_from=teacherLeaveManagement.getFrom();
        String l_to=teacherLeaveManagement.getTo(); 
        String[] l_pkey={l_teacherID,l_from,l_to};
        DBRecord leaveManagementRec=null;
        
        try{
        
        
           leaveManagementRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", l_pkey, session, dbSession);
       
         
         }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013", "");
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
                }
         
         
        buildBOfromDB(leaveManagementRec);
        
          dbg("end of  completed teacher leaveManagement--->view");  
        }catch(BSValidationException ex){
            throw ex;  
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
      private void buildBOfromDB(DBRecord p_teacherLeaveManagementRecord)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
           TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
           BusinessService bs=inject.getBusinessService(session);
           String l_teacherID=teacherLeaveManagement.getTeacherID();
           String l_instituteID=request.getReqHeader().getInstituteID();
           ArrayList<String>l_teacherLeaveManagementList= p_teacherLeaveManagementRecord.getRecord();
           
           if(l_teacherLeaveManagementList!=null&&!l_teacherLeaveManagementList.isEmpty()){
               
            String teacherName=bs.getTeacherName(l_teacherID, l_instituteID, session, dbSession, inject);
            teacherLeaveManagement.setTeacherName(teacherName);
            teacherLeaveManagement.setType(l_teacherLeaveManagementList.get(3).trim());
            teacherLeaveManagement.setReason(l_teacherLeaveManagementList.get(4).trim());
            request.getReqAudit().setMakerID(l_teacherLeaveManagementList.get(5).trim());
            request.getReqAudit().setCheckerID(l_teacherLeaveManagementList.get(6).trim());
            request.getReqAudit().setMakerDateStamp(l_teacherLeaveManagementList.get(7).trim());
            request.getReqAudit().setCheckerDateStamp(l_teacherLeaveManagementList.get(8).trim());
            request.getReqAudit().setRecordStatus(l_teacherLeaveManagementList.get(9).trim());
            request.getReqAudit().setAuthStatus(l_teacherLeaveManagementList.get(10).trim());
            request.getReqAudit().setVersionNumber(l_teacherLeaveManagementList.get(11).trim());
            request.getReqAudit().setMakerRemarks(l_teacherLeaveManagementList.get(12).trim());
            request.getReqAudit().setCheckerRemarks(l_teacherLeaveManagementList.get(13).trim());
            teacherLeaveManagement.setFromNoon(l_teacherLeaveManagementList.get(14).trim());
            teacherLeaveManagement.setToNoon(l_teacherLeaveManagementList.get(15).trim());
            }
            
          dbg("end of  buildBOfromDB"); 
        
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside teacher leaveManagement buildJsonResFromBO");    
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
         body=Json.createObjectBuilder().add("teacherID", teacherLeaveManagement.getTeacherID())
                                        .add("teacherName", teacherLeaveManagement.getTeacherName())
                                        .add("from", teacherLeaveManagement.getFrom())
                                        .add("to", teacherLeaveManagement.getTo())
                                        .add("fromNoon", teacherLeaveManagement.getFromNoon())
                                        .add("toNoon", teacherLeaveManagement.getToNoon())
                                        .add("type", teacherLeaveManagement.getType())
                                        .add("reason", teacherLeaveManagement.getReason()).build();
                                            
              dbg(body.toString());  
           dbg("end of teacher leaveManagement buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
    private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside teacher leaveManagement--->businessValidation");    
       String l_operation=request.getReqHeader().getOperation();
       
       if(!masterMandatoryValidation(errhandler)){
           status=false;
       }else{
            if(!masterDataValidation(errhandler)){
             status=false;
            }
       }
       
       if(!(l_operation.equals("View"))){
           
           if(!detailMandatoryValidation(errhandler)) {
               
               status=false;
           } else{
           
           if(!detailDataValidation(errhandler)){
               
               status=false;
           }
            
           
           }
       
       }
       dbg("end of teacher leaveManagement--->businessValidation"); 
       }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
            
       }catch(BSValidationException ex){
           throw ex;

       }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
    return status;
   }
    private boolean masterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside teacher leaveManagement master mandatory validation");
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        
         if(teacherLeaveManagement.getTeacherID()==null||teacherLeaveManagement.getTeacherID().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","teacherID");  
         }
          if(teacherLeaveManagement.getFrom()==null||teacherLeaveManagement.getFrom().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","from Date");  
         }
          if(teacherLeaveManagement.getTo()==null||teacherLeaveManagement.getTo().isEmpty()){
             status=false;  
             errhandler.log_app_error("BS_VAL_002","To Date");  
         }
          
          
        dbg("end of teacher leaveManagement master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
    private boolean masterDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher leaveManagement masterDataValidation");
             RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
             BSValidation bsv=inject.getBsv(session);
             BusinessService bs=inject.getBusinessService(session);
             TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             String l_teacherID=teacherLeaveManagement.getTeacherID();
             String l_from=teacherLeaveManagement.getFrom();
             String l_to=teacherLeaveManagement.getTo();
             String l_operation=request.getReqHeader().getOperation();
             
             if(!bsv.teacherIDValidation(l_teacherID, l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","teacherID");
             }
             if(!bsv.dateFormatValidation(l_from, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","from date");
             }
             if(!bsv.dateFormatValidation(l_to, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","To date");
             }
             if(!bsv.leaveDateValidation(l_from,l_to, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","From and To Dates");
             }
            
             if(l_operation.equals("Create")){
             
                 if(!bsv.pastDateValidation(l_from, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","From Date");
                 }
                 if(!bsv.pastDateValidation(l_to, session, dbSession, inject)){
                     status=false;
                     errhandler.log_app_error("BS_VAL_003","To Date");
                 }
             
                 
                 
             }
             
            
            dbg("end of teacher leaveManagement masterDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    
    private boolean detailMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
        boolean status=true;
        RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
        TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
        
        try{
            
            dbg("inside teacher leaveManagement detailMandatoryValidation");
           
            if(teacherLeaveManagement.getFromNoon()==null||teacherLeaveManagement.getFromNoon().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","fromNoon");  
            }
//            if(teacherLeaveManagement.getToNoon()==null||teacherLeaveManagement.getToNoon().isEmpty()){
//               status=false;  
//               errhandler.log_app_error("BS_VAL_002","toNoon");  
//            } 
            
            if(teacherLeaveManagement.getType()==null||teacherLeaveManagement.getType().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Type");  
            }
            if(teacherLeaveManagement.getReason()==null||teacherLeaveManagement.getReason().isEmpty()){
               status=false;  
               errhandler.log_app_error("BS_VAL_002","Reason");  
            }

            
            
            
           dbg("end of teacher leaveManagement detailMandatoryValidation");        
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    private boolean detailDataValidation(ErrorHandler errhandler)throws BSProcessingException,DBProcessingException,DBValidationException,BSValidationException{
         boolean status=true;
         
         try{
             dbg("inside teacher leaveManagement detailDataValidation");
             RequestBody<TeacherLeaveManagement> reqBody = request.getReqBody();
             TeacherLeaveManagement teacherLeaveManagement =reqBody.get();
             String l_instituteID=request.getReqHeader().getInstituteID();
             BSValidation bsv=inject.getBsv(session);
             String l_leaveType=teacherLeaveManagement.getType();
             String l_fromNoon=teacherLeaveManagement.getFromNoon();
             String l_toNoon=teacherLeaveManagement.getToNoon();
             String fromDate=teacherLeaveManagement.getFrom();
             String toDate=teacherLeaveManagement.getTo();
            
             if(!bsv.leaveTypeValidation(l_leaveType,l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","Leave Type");
             }
             
             if(!bsv.leaveNoonValidation(l_fromNoon,l_instituteID, session, dbSession, inject)){
                 status=false;
                 errhandler.log_app_error("BS_VAL_003","From Noon");
             }
             
//             if(!bsv.leaveNoonValidation(l_toNoon,l_instituteID, session, dbSession, inject)){
//                 status=false;
//                 errhandler.log_app_error("BS_VAL_003","To Noon");
//             }
             
             if(fromDate.equals(toDate)){
                 
                 if(l_toNoon!=null&&!l_toNoon.isEmpty()){
                     
                     
                     if(!l_fromNoon.equals(l_toNoon)){
                         
                         status=false; 
                         errhandler.log_app_error("BS_VAL_074",null);
                         throw new BSValidationException("BSValidationException");                     }
                 }else{
                     
                     teacherLeaveManagement.setToNoon(l_fromNoon);
                 }
                 
             }else{
                 
                if(l_toNoon==null||l_toNoon.isEmpty()){
                    
                    status=false;
                    errhandler.log_app_error("BS_VAL_002","To Noon");
                    throw new BSValidationException("BSValidationException");
                    
                } 
                 
                  
                  
                  
             }
             
             
             
            dbg("end of teacher leaveManagement detailDataValidation");
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        
        return status;
              
    }
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     try{
        dbg("inside TeacherLeaveManagement--->getExistingAudit") ;
        exAudit=new ExistingAudit();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_operation=request.getReqHeader().getOperation();
        String l_service=request.getReqHeader().getService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        String l_versionNumber=request.getReqAudit().getVersionNumber();
        dbg("l_operation"+l_operation);
        dbg("l_service"+l_service);
        dbg("l_instituteID"+l_instituteID);
        if(!(l_operation.equals("Create")||l_operation.equals("View"))){
             
              if(l_operation.equals("AutoAuth")&&l_versionNumber.equals("1")){
                return null;
              }else{  
               dbg("inside TeacherLeaveManagement--->getExistingAudit--->Service--->UserLeaveManagement");  
               RequestBody<TeacherLeaveManagement> teacherLeaveManagementBody = request.getReqBody();
               TeacherLeaveManagement teacherLeaveManagement =teacherLeaveManagementBody.get();
               String l_teacherID=teacherLeaveManagement.getTeacherID();
               String l_from=teacherLeaveManagement.getFrom();
               String l_to=teacherLeaveManagement.getTo();
               String[] l_pkey={l_teacherID,l_from,l_to};
               DBRecord l_teacherLeaveManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", l_pkey, session, dbSession);
               exAudit.setAuthStatus(l_teacherLeaveManagementRecord.getRecord().get(10).trim());
               exAudit.setMakerID(l_teacherLeaveManagementRecord.getRecord().get(5).trim());
               exAudit.setRecordStatus(l_teacherLeaveManagementRecord.getRecord().get(9).trim());
               exAudit.setVersionNumber(Integer.parseInt(l_teacherLeaveManagementRecord.getRecord().get(11).trim()));

 
         dbg("exAuthStatus"+exAudit.getAuthStatus());
         dbg("exMakerID"+exAudit.getMakerID());
         dbg("exRecordStatus"+exAudit.getRecordStatus());
         dbg("exVersionNumber"+exAudit.getVersionNumber());
        
        dbg("end of TeacherLeaveManagement--->getExistingAudit") ;
        }
        }else{
            return null;
        }
        
    }catch(DBValidationException ex){
      throw ex;
     
     }catch(DBProcessingException ex){   
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
     return exAudit;
 }   
    
   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }   
    
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
   
        try{
            
           RequestBody<TeacherLeaveManagement> teacherLeaveManagementBody = request.getReqBody(); 
           TeacherLeaveManagement teacherLeaveManagement =teacherLeaveManagementBody.get();
           String l_teacherID=teacherLeaveManagement.getTeacherID();
           IDBTransactionService dbts=inject.getDBTransactionService();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String l_instituteID=request.getReqHeader().getInstituteID();
           IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            
           Map<String,String>leaveMap=getLeaveDates();
           
           Iterator<String>keyIterator=leaveMap.keySet().iterator();
           
           while(keyIterator.hasNext()){
               
               String  date=keyIterator.next();
               String value=leaveMap.get(date);
               
              String fullDay=null;
                String noon=null;
                
                if(value.equals("FD")){
                    
                    fullDay="Y";
                    noon="";
                }else if(value.equals("F")){
                    
                    fullDay="N";
                    noon="F";
                }else if(value.equals("A")){
                    
                    fullDay="N";
                    noon="A";
                }
                
               char holidayChar= getHolidayCharOfTheDay(date);
                
               boolean isWorkingDay=false;
               
               if(holidayChar=='W'){
                   
                   isWorkingDay=true;
               }else if(holidayChar=='F'&&noon.equals("F")){
                   
                   isWorkingDay=false;
               }else if(holidayChar=='A'&&noon.equals("A")){
                   
                   isWorkingDay=false;
               }else if(holidayChar=='H'){
                   
                   isWorkingDay=false;
               }
               
                if(isWorkingDay){
                
                    
                    boolean recordExistence=false;
                String[] l_primaryKey={l_teacherID};
                    try{
                        
                        
                        
                        readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+date,"INSTITUTE","IVW_TEACHER_LEAVE_DETAILS", l_primaryKey, session, dbSession);
                        
                        recordExistence=true;
                 }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        recordExistence=false;
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
            }
                    
            if(recordExistence){
                
                Map<String,String>l_columnToUpdate=new HashMap();
                l_columnToUpdate.put("2", fullDay);
                l_columnToUpdate.put("3", noon);
                
                dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+date,"INSTITUTE","IVW_TEACHER_LEAVE_DETAILS", l_primaryKey, l_columnToUpdate, session);
                
            }else{
                    
                    
                    dbts.createRecord(session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+date,"INSTITUTE",322,l_teacherID,fullDay,noon);
                
             }
                    
                    
                }
               
           }
           
           
            
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
        
        
}
    
    
    private Map<String,String> getLeaveDates()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        
       try{
            
           RequestBody<TeacherLeaveManagement> teacherLeaveManagementBody = request.getReqBody(); 
           TeacherLeaveManagement teacherLeaveManagement =teacherLeaveManagementBody.get();
           String l_from=teacherLeaveManagement.getFrom();
           String l_to=teacherLeaveManagement.getTo();
           String l_fromNoon=teacherLeaveManagement.getFromNoon();
           String l_toNoon=teacherLeaveManagement.getToNoon();
           BusinessService bs=inject.getBusinessService(session);
           IBDProperties i_db_properties=session.getCohesiveproperties();
           String dateformat=i_db_properties.getProperty("DATE_FORMAT");
           SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
           ArrayList<Date>leaveDates=bs.getLeaveDates(l_from, l_to, session, dbSession, inject);
            
           Map<String,String>leaveMap=new HashMap();
           
           
           for(int i=0;i<leaveDates.size();i++){
               
               Date date=leaveDates.get(i);
               String leavedate=formatter.format(date);
               
               if(leavedate.equals(l_from)){
                    
                    dbg("leavedate  equal to fromDate");
                    
                                        
                     if(l_fromNoon.equals("F")){
                        
                        leaveMap.put(leavedate, "F");
                         
                         
                    
                    }else if(l_fromNoon.equals("A")){


                        leaveMap.put(leavedate, "A");
                        
                        
                    }else{
                        
                        leaveMap.put(leavedate, "FD");
                    }
                    
                }else if(leavedate.equals(l_to)){
                    
                    dbg("leave date equal to fromDate");
                    
                    if(l_toNoon.equals("F")){
                    
                        
                        leaveMap.put(leavedate, "F");
                    
                    }else if(l_toNoon.equals("A")){

                            leaveMap.put(leavedate, "A");
                    }else{
                        
                        leaveMap.put(leavedate, "FD");
                    }
                    
                }else{
                    
                    dbg("attendance date not equal to fromDate");
                    
                    
                    leaveMap.put(leavedate, "FD");
                    
                }
           }  
            
            
            
            return leaveMap;
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } 
        
    }
    
       
    private char getHolidayCharOfTheDay(String date)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
        boolean holidayStatus=false;
      try{  
        
        BusinessService bs=inject.getBusinessService(session);
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_instituteID=request.getReqHeader().getInstituteID();
        ConvertedDate converted=bs.getYearMonthandDay(date);
        String l_year=converted.getYear();
        String l_month=converted.getMonth();
        String l_day=converted.getDay();  
        
        if(Integer.parseInt(l_month)<10){
            
            l_month="0"+l_month;
        }
        
        dbg("inside getHolidayCharOfTheDay-->"+l_month);
        String[] l_pkey={l_instituteID,l_year,l_month};  
          
        ArrayList<String> holidayMaintanenceRecord=pds.readRecordPData( session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_pkey);
           
        String holidayString=holidayMaintanenceRecord.get(3);  
        char[] holidayArr=holidayString.toCharArray();
        char charForTheDay=holidayArr[Integer.parseInt(l_day)-1];
        
              
//                  if(charForTheDay!='W'){
//
//                      holidayStatus=true;
//                  }
//
//              if(noon.equals("F")){
//
//                  if(charForTheDay=='F'){
//
//                      holidayStatus=true;
//                  }
//
//              }else if(noon.equals("A")){
//
//                  if(charForTheDay!='A'){
//
//                      holidayStatus=true;
//                  }
//              }
        



        return charForTheDay;
        
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        } 
    }
    
    
}