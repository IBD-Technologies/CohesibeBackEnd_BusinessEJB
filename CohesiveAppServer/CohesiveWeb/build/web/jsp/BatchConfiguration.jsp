<%-- 
    Document   : BatchConfiguration
    Created on : Aug 4, 2019, 12:11:41 PM
    Author     : IBD Technologies
--%>

 <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!--Css Library Starts-->
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
      <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!--Css Library Ends-->
      <!--Js Library Starts-->
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/js/js_library/jquery-ui.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <!--Js Library Ends-->
      <!--Css Framework Starts-->
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
	  <link rel ="stylesheet" href="/css/utils/SelectBox.css">
      <!--Css Framework Ends-->
      <!--Js Framework Starts-->
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/Operation.js"></script>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
      <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
      <!--Js Framework Ends-->	  
      <script type ="text/javascript" src="/js/BatchConfiguration.js"></script>
   </head>
   <body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
         <%
         response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
         response.setHeader("Pragma","no-cache"); //Http 1.0
         response.setHeader("Expires", "-1"); //Proxies
         response.setHeader("X-XSS-Protection","1;mode=block");
         response.setHeader("X-Frame-Options","SAMEORIGIN");
         response.setHeader("Content-Security-Policy","default-src 'self';img-src 'self' data:;script-src  'self';style-src 'unsafe-inline'  'self';base-uri 'none';form-action 'none';frame-ancestors 'self'");
         %>
                <header id="subscreenHeader" class="subscreenHeader mb-3">
                    <div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
                        <h6 align="center">Batch Configuration</h6>
                        <div id="operationsection" class="subScreenOperationSection" ng-view>
                        </div>
                    </div>
                    <div id="searchHeader">
                    </div>
                </header>
                <div id="subscreenContent" class="subscreenContent">
                    <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
         <div id="mastersection" ng-show="!searchShow&&mastershow">
            <div class ="form-group row">
               <label for="batchName" class="col-3 col-form-label">Batch Name</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="batchName" type="text" ng-readonly="batchNameReadOnly" ng-model="batchName" class="form-control">
                     <div class="input-group-append">
                        <button type="button" class="btn btn-primary" ng-disabled="batchNameReadOnly" ng-click="fnBatchNameSearch()"><i class="fas fa-search"></i></button>
                     </div>
                  </div>
               </div>
            </div>
            <div class= "form-group row">
               <label for="studId"  class="col-3 col-form-label">Description</label>
               <div class=" input-group-append col-9">
                  <input id="batchDescription" type="text" ng-readonly ="batchDescriptionreadOnly" ng-model="batchDescription" class="form-control">
               </div>
            </div>
         </div>
         <div id="detailSection" ng-show="!searchShow&&detailshow">
		 
		 
		        <ul class="nav nav-tabs mb-4" id="subTab" role="tablist">
                     <li class="nav-item">
                        <a class="nav-link active"  data-toggle="tab" href="#subGeneral" role="tab"  aria-selected="true">General</a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#subOthers" role="tab"  aria-selected="false">Others</a>
                     </li>
                  </ul>
		 
		   <div class="tab-content" id="subTab">
                     <div class="tab-pane fade show active" id="subGeneral" role="tabpanel" >
            <div class= "form-group row">
                  <label for="batchLayer" class="col-3 col-form-label">Layer</label>
                   <div class="col-9">
                   <div class="input-group">
                   <select class="custom-select" ng-disabled ="layerreadOnly" id="batchLayer" ng-model="layer">
                   <option ng-repeat="x in Batch"  value="{{x.value}}">{{x.name}}</option>
                   </select>
                   </div>
                  </div> 
                  </div>
				  
				   <div class= "form-group row">
                  <label for="ExecutionFrequency" class="col-3 col-form-label">Execution Frequency</label>
                   <div class="col-9">
                   <div class="input-group">
                   <select class="custom-select" ng-disabled ="executionFrequencyreadOnly" id="ExecutionFrequency" ng-model="executionFrequency">
                   <option ng-repeat="x in Frequency"  value="{{x.value}}">{{x.name}}</option>
                   </select>
                   </div>
                  </div> 
                  </div>
				   <div class= "form-group row">
                  <label for="Eod" class="col-3 col-form-label">EOD</label>
				  <div class="col-9">
				  <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" ng-disabled="eodReadOnly"  ng-modal= "eod">
				  <label class="form-check-label" for="inlineRadio1">Yes</label>
				  </div>
				  <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" ng-disabled="eodReadOnly"  ng-modal= "eod" >
				  <label class="form-check-label" for="inlineRadio1">No</label>
                  </div>
				  </div>
				  </div>
				  <div class= "form-group row">
                  <label for="Eod" class="col-3 col-form-label">Intra Day</label>
				  <div class="col-9">
				  <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" ng-disabled="eodReadOnly"  ng-modal= "eod">
				  <label class="form-check-label" for="inlineRadio1">Yes</label>
				  </div>
				  <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" ng-disabled="eodReadOnly"  ng-modal= "eod" >
				  <label class="form-check-label" for="inlineRadio1">No</label>
                  </div>
				  </div>
				  </div>
				  </div>
				  
				  
				  <div class="tab-pane fade" id="subOthers" role="tabpanel" >
				  
				  
                  <div class= "form-group row">
                  <label for="NumOfThreads" class="col-3 col-form-label">Number of threads</label>
                   <div class="col-9">
                   <div class="input-group">
                   <input id="NumOfThreads" type="text" ng-readonly="numOfThreadsReadOnly" ng-model="numberOfThreads" class="form-control">
                   </div>
                  </div> 
                  </div>
				  
				   <div class= "form-group row">
                  <label for="ChildNumOfThreads" class="col-3 col-form-label">Child Number of threads</label>
                   <div class="col-9">
                   <div class="input-group">
                   <input id="ChildNumOfThreads" type="text" ng-readonly="ChildNumOfThreadsReadOnly" ng-model="ChildNumberOfThreads" class="form-control">
                   </div>
                  </div> 
                  </div>  
         </div>
		</div>
		</div>
         <div id="Auditlogsection" ng-show="!searchShow&&auditshow">
            <div class="cohesive_topmargin">
               <div class="Auditlogsection">
                  <ul class="nav nav-tabs" id="auditTab" role="tablist">
                     <li class="nav-item">
                        <a class="nav-link active"  data-toggle="tab" href="#maker" role="tab"  aria-selected="true">Maker</a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#checker" role="tab"  aria-selected="false">Checker</a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#status" role="tab" aria-selected="false">Status</a>
                     </li>
                  </ul>
                  <div class="tab-content" id="auditTab">
                     <div class="tab-pane fade show active" id="maker" role="tabpanel" >
                        <div class="card border-light" >
                           <div class="card-body" >
                              <div class= "form-group row">
                                 <label for="ID" class="col-3 col-form-label">ID</label>
                                 <div class=" input-group-append col-9">
                                    <input id="makerID" class="form-control" ng-model="audit.MakerID" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="date" class="col-3 col-form-label">Date</label>
                                 <div class=" input-group-append col-9">
                                    <input id="makerDtStamp" type="text" class="form-control" ng-model="audit.MakerDtStamp" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="remarks" class="col-3 col-form-label">Remarks</label>
                                 <div class=" input-group-append col-9">
                                    <input id="makerRemarks" type="text" class="form-control" ng-readonly="MakerRemarksReadonly" ng-model="audit.MakerRemarks"> 
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="tab-pane fade" id="checker" role="tabpanel" >
                        <div class="card border-light">
                           <div class="card-body" >
                              <div class= "form-group row">
                                 <label for="ID" class="col-3 col-form-label">ID</label>
                                 <div class=" input-group-append col-9">
                                    <input id="checkerID" class="form-control" ng-model="audit.CheckerID" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="date" class="col-3 col-form-label">Date</label>
                                 <div class=" input-group-append col-9">
                                    <input id="checkerDtStamp" type="text" class="form-control" ng-model="audit.CheckerDtStamp" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="remarks" class="col-3 col-form-label">Remarks</label>
                                 <div class=" input-group-append col-9">
                                    <input id="text" type="text" class="form-control" ng-readonly="CheckerRemarksReadonly" ng-model="audit.CheckerRemarks" >
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="tab-pane fade" id="status" role="tabpanel" >
                        <div class="card border-light">
                           <div class="card-body" >
                              <div class= "form-group row">
                                 <label for="ID" class="col-3 col-form-label">Record</label>
                                 <div class=" input-group-append col-9">
                                    <input id="recordStat" type="text" class="form-control" ng-model="audit.RecordStat" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="date" class="col-3 col-form-label">Authorize</label>
                                 <div class=" input-group-append col-9">
                                    <input id="authStat" type="text" class="form-control" ng-model="audit.AuthStat" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="remarks" class="col-3 col-form-label">Version</label>
                                 <div class=" input-group-append col-9">
                                    <input id="version" type="text" class="form-control" ng-model="audit.Version" readonly>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
          <div id="searchBody" ng-controller="BatchNameSearch">		
         </div>
      </div>
      <footer class="subscreenFooter" ng-show="!searchShow">
         <nav class="nav nav-pills nav-justified">
            <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
            <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Details</a>
            <a id="AuditFooter" class="cohesiveFooter_navitem footer_color" href="#">Audit</a>
         </nav>
      </footer>
      <div id="snackbar"></div>
        <div id="spinner"></div>
   </body>
</html>
