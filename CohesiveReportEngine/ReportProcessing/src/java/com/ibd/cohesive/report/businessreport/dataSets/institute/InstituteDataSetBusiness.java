/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.MarkComparison;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_REPORT;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(IInstituteDataSetBusiness.class)
@Stateless
public class InstituteDataSetBusiness implements IInstituteDataSetBusiness {
    ReportDependencyInjection inject;
    AppDependencyInjection appInject;
    CohesiveSession session;
    DBSession dbSession;
    
    public InstituteDataSetBusiness(){
        try {
            
            inject=new ReportDependencyInjection("INSTITUTE_BU");
            appInject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    
    
     public String MarkComparison_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside MarkComparison_DataSet");
          
          
          MarkComparison_DataSet classMarkSummary=inject.getMarkComparison();
          
          ArrayList<MarkComparison>MarkComparison_DataSet=  classMarkSummary.getMarkComparisonObject( p_instanceID,standard, session, dbSession, inject,appInject,userID);
         String result=this.convertMarkComparisonListToString(MarkComparison_DataSet, session);
         dbg("end of MarkComparison_DataSet-->result-->"+result);
         return result;          
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    
     public String convertMarkComparisonListToString(ArrayList<MarkComparison>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                MarkComparison appEod=appEodList.get(i);
                
                String record=appEod.getAverageMark()+"~"+
                              appEod.getExam()+"~"+
                              appEod.getLowMark()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSubjectID()+"~"+
                              appEod.getTopMark()+"~"+
                              appEod.getGrade()+"~"+
                              appEod.getGradePercentage()+"~"+
                              appEod.getNo_of_Students()+"~"+
                              appEod.getOrderNo()+"~"+
                              appEod.getSubjectName();
                              
                                                      
                              
                              
                        
                         
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    public String CLASS_MARK_REPORT_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside CLASS_MARK_REPORT_DataSet");
          
          
          CLASS_MARK_REPORT_DATASET classMarkSummary=inject.getClassMark();
          
          ArrayList<CLASS_MARK_REPORT>CLASS_MARK_REPORT_DataSet=  classMarkSummary.getTableObject(p_instanceID, p_instanceID, session, dbSession, inject, appInject, userID);
         String result=this.convertCLASS_MARK_REPORTListToString(CLASS_MARK_REPORT_DataSet, session);
         dbg("end of CLASS_MARK_REPORT_DataSet-->result-->"+result);
         return result;          
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    
     public String convertCLASS_MARK_REPORTListToString(ArrayList<CLASS_MARK_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_MARK_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getAveragreMark()+"~"+
                              appEod.getExam()+"~"+
                              appEod.getLowMark()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSubjectID()+"~"+
                              appEod.getTopMark()+"~"+
                              appEod.getSubjectName();
                              
                                                      
                              
                              
                        
                         
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
