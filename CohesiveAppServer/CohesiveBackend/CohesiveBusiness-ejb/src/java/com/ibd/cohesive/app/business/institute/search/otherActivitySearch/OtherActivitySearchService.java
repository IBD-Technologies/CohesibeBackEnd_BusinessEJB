/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.search.otherActivitySearch;

import com.ibd.businessViews.IOtherActivitySearchService;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.app.business.util.BusinessEJB;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.exception.ExceptionHandler;
import com.ibd.cohesive.app.business.util.message.request.Parsing;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.app.business.util.message.request.RequestBody;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(IOtherActivitySearchService.class)
@Stateless
public class OtherActivitySearchService implements IOtherActivitySearchService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Request request;
    ExistingAudit exAudit;
    
    public OtherActivitySearchService(){
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
       try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       BSValidation bsv=inject.getBsv(session);
       bs=inject.getBusinessService(session);
       ITransactionControlService tc=inject.getTransactionControlService();
       dbg("inside OtherActivitySearchService--->processing");
       dbg("OtherActivitySearchService--->Processing--->I/P--->requestJson"+requestJson.toString());
       clonedJson=  bs.cloneRequestJsonObject(requestJson);
       dbg("cloned json"+clonedJson.toString());
       request = new Request();
       parser=inject.getParser(session);
       parser.parseRequest(request,clonedJson);
       bs.requestlogging(request,clonedJson, inject,session,dbSession);
       buildBOfromReq(clonedJson); 
       BusinessEJB<IOtherActivitySearchService>otherActivitySearchEJB=new BusinessEJB();
       otherActivitySearchEJB.set(this);
      
       exAudit=bs.getExistingAudit(otherActivitySearchEJB);
       
        if(!(bsv. businessServiceValidation(clonedJson,exAudit,request,errhandler,inject,session,dbSession))){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        }
        if(!businessValidation(errhandler)){
           l_validation_status=false;
           throw new BSValidationException("BSValidationException");
        } 
      
       bs.businessServiceProcssing(request, exAudit, inject,otherActivitySearchEJB);
       

       if(l_session_created_now){
              jsonResponse= bs.buildSuccessResponse(clonedJson, request, inject, session,otherActivitySearchEJB);
              tc.commit(session,dbSession);
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

           request=null;
            bs=inject.getBusinessService(session);
            if(l_session_created_now){
                bs.responselogging(jsonResponse, inject,session,dbSession);
             clonedResponse=bs.cloneResponseJsonObject(jsonResponse); 
            dbg("Response"+jsonResponse.toString());
            BSValidation bsv=inject.getBsv(session);
            //if(!bsv.responseSpecialCharacterValidation(jsonResponse)){
                      clonedResponse=bsv.responseSpecialCharacterValidation(clonedResponse);//Integration changes  
            //BSProcessingException ex=new BSProcessingException("response contains special characters");
              //  dbg(ex);
                
                //session.clearSessionObject();
               //dbSession.clearSessionObject();
               //throw ex;
               
            //}
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
      OtherActivitySearch otherActivitySearch=new OtherActivitySearch();
      RequestBody<OtherActivitySearch> reqBody = new RequestBody<OtherActivitySearch>(); 
           
      try{
      dbg("inside otherActivity otherActivitySearch buildBOfromReq");    
      JsonObject l_body=p_request.getJsonObject("body");
      otherActivitySearch.setSearchFilter(l_body.getString("searchFilter"));

      
        reqBody.set(otherActivitySearch);
        request.setReqBody(reqBody);
        dbg("End of build bo from request");

     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }
   }
     

    

    public void view()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside otherActivity otherActivitySearch--->view");
        IBDProperties i_db_properties=session.getCohesiveproperties();
//        IPDataService pds=inject.getPdataservice();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=request.getReqHeader().getInstituteID();
        RequestBody<OtherActivitySearch> reqBody = request.getReqBody();
        ErrorHandler errorHandler=session.getErrorhandler();
        OtherActivitySearch otherActivitySearch =reqBody.get();
        String searchFilter=otherActivitySearch.getSearchFilter();
        Map<String,DBRecord>otherActivitySearchMap=null;
        
        try{

          otherActivitySearchMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", session, dbSession);
           
        }catch(DBValidationException  ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                errorHandler.removeSessionErrCode("DB_VAL_011");
                errorHandler.removeSessionErrCode("DB_VAL_000");
                errorHandler.log_app_error("BS_VAL_020", searchFilter);
                throw new BSValidationException("BSValidationException");
                
            }else{
                throw ex;
            }
            
        }  
          
        dbg("otherActivitySearchMap size"+otherActivitySearchMap.size());
        buildBOfromDB(otherActivitySearchMap);     
        
          dbg("end of  completed otherActivity otherActivitySearch--->view");                
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    

    
    private void buildBOfromDB(Map<String,DBRecord>otherActivitySearchMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           IMetaDataService mds=inject.getMetadataservice();
           RequestBody<OtherActivitySearch> reqBody = request.getReqBody();
           ErrorHandler errorHandler=session.getErrorhandler();
           OtherActivitySearch otherActivitySearch =reqBody.get();
           String searchFilter=otherActivitySearch.getSearchFilter();
           dbg("searchFilter"+searchFilter);
           
           int recStatusColId=mds.getColumnMetaData("OTHER_ACTIVITY", "IVW_OTHER_ACTIVITY", "RECORD_STATUS", session).getI_ColumnID();
           int authStatusColId=mds.getColumnMetaData("OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", "AUTH_STATUS", session).getI_ColumnID();
           List<DBRecord>authorizedRecords=otherActivitySearchMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId-1).trim().equals("O")).collect(Collectors.toList());   
          
           if(authorizedRecords==null||authorizedRecords.isEmpty()){
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
           List<DBRecord>filteredRecords=  authorizedRecords.stream().filter(rec->rec.getRecord().get(2).trim().contains(searchFilter)||rec.getRecord().get(3).trim().contains(searchFilter)||rec.getRecord().get(4).trim().contains(searchFilter)).collect(Collectors.toList()); 
           dbg("recStatusColId"+recStatusColId);
           dbg("authStatusColId"+authStatusColId);
           dbg("authorizedRecords"+authorizedRecords.size());
           if(filteredRecords!=null)
              dbg("filteredRecords size"+filteredRecords.size());
           else
               dbg("filteredRecords is null");
           
           if(searchFilter.equals("")){
           
               setRecordInOtherActivitySearch(authorizedRecords);
               
           }else if(filteredRecords!=null&&!filteredRecords.isEmpty()){
               
               setRecordInOtherActivitySearch(filteredRecords);
               
           }else{
               
               errorHandler.log_app_error("BS_VAL_020", searchFilter);
               throw new BSValidationException("BSValidationException");
           }
           
         
           
          dbg("end of  buildBOfromDB"); 
           
        }catch(DBValidationException ex){
            throw ex;
        }catch(BSValidationException ex){
            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
 }
    
    private void setRecordInOtherActivitySearch(List<DBRecord>otherActivitySearchList)throws BSProcessingException{
        
        try{
           dbg("inside setRecordInOtherActivitySearch"); 
           RequestBody<OtherActivitySearch> reqBody = request.getReqBody();
           OtherActivitySearch otherActivitySearch =reqBody.get();
           int maxResultSize=Integer.parseInt(session.getCohesiveproperties().getProperty("MAX_SEARCH_RESULTS"));
            int resultSize=0;
               if(otherActivitySearchList.size()<=maxResultSize){
                   
                   resultSize=otherActivitySearchList.size();
               }else{
                   resultSize=maxResultSize;
               }
               
               otherActivitySearch.result=new OtherActivitySearchResult[resultSize];
               
               for(int i=0;i<resultSize;i++){
                   
                   String otherActivityID=otherActivitySearchList.get(i).getRecord().get(2).trim();
                   String otherActivityName=otherActivitySearchList.get(i).getRecord().get(3).trim();
                   String otherActivityType=otherActivitySearchList.get(i).getRecord().get(4).trim();
                   dbg("setRecordInOtherActivitySearch-->otherActivityID"+otherActivityID);
                   dbg("setRecordInOtherActivitySearch-->otherActivityType"+otherActivityType);
                   dbg("setRecordInOtherActivitySearch-->otherActivityName"+otherActivityName);
                   otherActivitySearch.result[i]=new OtherActivitySearchResult();
                   otherActivitySearch.result[i].setOtherActivityID(otherActivityID);
                   otherActivitySearch.result[i].setOtherActivityType(otherActivityType);
                   otherActivitySearch.result[i].setOtherActivityName(otherActivityName);
               }
            
            
        
        dbg("end of setRecordInOtherActivitySearch");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    public JsonObject buildJsonResFromBO()throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside otherActivity otherActivitySearch buildJsonResFromBO");    
        RequestBody<OtherActivitySearch> reqBody = request.getReqBody();
        OtherActivitySearch otherActivitySearch =reqBody.get();
        JsonArrayBuilder resultArray=Json.createArrayBuilder();
             
        for(int i=0;i<otherActivitySearch.result.length;i++){
            
            resultArray.add(Json.createObjectBuilder().add("activityID", otherActivitySearch.result[i].getOtherActivityID())
                                                      .add("activityName", otherActivitySearch.result[i].getOtherActivityName())
                                                      .add("activityType", otherActivitySearch.result[i].getOtherActivityType()));
        }

             body=Json.createObjectBuilder()  .add("searchFilter", otherActivitySearch.getSearchFilter())
                                              .add("searchResults", resultArray)
                                              .build();
               
           
                                            
              dbg(body.toString());  
           dbg("end of otherActivity otherActivitySearch buildJsonResFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
   
      private boolean businessValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException{
       boolean status=true;
              
       try{
       dbg("inside otherActivity otherActivitySearch--->businessValidation");    
       if(!filterMandatoryValidation(errhandler)){
           status=false;
       }
       
       
       dbg("end of otherActivity otherActivitySearch--->businessValidation"); 
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
    private boolean filterMandatoryValidation(ErrorHandler errhandler)throws BSProcessingException,BSValidationException{
      boolean status=true;
        try{
        dbg("inside otherActivity otherActivitySearch master mandatory validation");
        RequestBody<OtherActivitySearch> reqBody = request.getReqBody();
        OtherActivitySearch otherActivitySearch =reqBody.get();
         
         
        
         
          
         
          
          
        dbg("end of otherActivity otherActivitySearch master mandatory validation");
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status;
        
    }
    
   
 public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
     return null;
 }   
 public  void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
 }
 public void create()throws BSProcessingException,DBValidationException,DBProcessingException{
 }
 public void authUpdate()throws DBValidationException,DBProcessingException,BSProcessingException{
 }
 public void fullUpdate()throws BSProcessingException,DBValidationException,DBProcessingException{
 }
 public void delete()throws DBValidationException,DBProcessingException,BSProcessingException{
 }   
 
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }  
}
