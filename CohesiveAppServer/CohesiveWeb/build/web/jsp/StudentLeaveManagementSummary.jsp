<%-- 
   Document   : StudentLeaveManagementSummary
   Created on : Jul 30, 2019, 7:27:37 PM
   Author     : IBD Technologies
   --%>
    <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Css Library Starts----->
            <link rel="stylesheet" href="/css/library/bootstrap.min.css">
            <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
            <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
            <!-- Css Library Ends----->
            <!-- Js Library Starts----->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/angular-sanitize.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>
            <!-- Js Library Ends--->
            <!-- Css Framework Library Starts--->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!-- Css Framework Library Ends--->
            <!-- Js Framework Library Starts--->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/SummaryOperation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <script src="/js/Utils/date_picker.js"></script>
            <script src="/js/Utils/SummaryBridge.js"></script>
            <!--Js Framework Library Ends--->
            <script src="/js/StudentLeaveManagementsummary.js"></script>
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
                        <h6 align="center">Student Leave Management Summary</h6>
                        <div id="operationsection" class="subScreenOperationSection" ng-view>
                        </div>
                    </div>
                    <div id="searchHeader">
                    </div>
                </header>
                <div id="subscreenContent" class="subscreenContent">
                    <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
                    <div id="mastersection" ng-show="!searchShow&&mastershow">
                          <!--<ul class="nav nav-tabs mb-4" id="subTab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#studentTab" role="tab" aria-selected="true">Student</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#leaveTab" role="tab" aria-selected="false">Leave</a>
                                    </li>
                        </ul>-->
                       <!--<div class="tab-content" id="subTab">-->
                        <!--<div class="tab-pane fade show active" id="studentTab" role="tabpanel">-->
                                <!--<div class="form-group row">
                                    <label for="studentName" class="col-3 col-form-label">Student Name</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input id="studentName" type="text" ng-readonly="studentNamereadOnly" ng-model="studentName" class="form-control">
                                            <div class="input-group-append">
                                                <button type="button" class="btn btn-primary" ng-disabled="studentNamereadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="studId" class="col-3 col-form-label">ID</label>
                                    <div class=" input-group-append col-9">
                                        <input id="staffId" type="text" ng-readonly="studentIDreadOnly" ng-model="studentID" class="form-control">
                                    </div>
                                </div>-->
                                <!--<div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>
                                </div>-->
                                <div class="form-group row">
                                    <label for="studentName" class="col-3 col-form-label">Name</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input id="studentName" type="text" ng-readonly="studentNamereadOnly" ng-model="studentName" class="form-control">
                                            <div class="input-group-append">
                                                <button type="button" class="btn btn-primary" ng-disabled="studentNamereadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                             <c:forEach items="${cookie}" var="currentCookie">  
                          <c:if test="${currentCookie.key=='userType'}">
                <c:if test="${currentCookie.value.value!='P'}">   
                                
                                
                                
                                <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>
                                </div>
                                
                                
                                <div class ="form-group row">
                        <label for="fromDate" class="col-3 col-form-label">Creation From Date</label>
                        <div class="col-9">
                           <div class="input-group">
                              <input   ng-readonly="fromReadOnly" id="fromDate" ng-model="from" class="form-control">
                              <div class="input-group-append">
                                 <button type="button" ng-disabled="fromReadOnly" id="fromDateShow" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                              </div>
                           </div>
                        </div>
                    </div>
             
                        <div class ="form-group row">
                        <label for="toDate" class="col-3 col-form-label">Creation To Date</label>
                        <div class="col-9">
                           <div class="input-group">
                              <input   ng-readonly="toReadOnly" id="toDate" ng-model="to" class="form-control">
                              <div class="input-group-append">
                                 <button type="button" ng-disabled="toReadOnly" id="toDateShow" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                              </div
                           </div>
                        </div>
                    </div>
            
                 </div>
                                
                    </c:if>
                
                
                 </c:if> 


    </c:forEach>    
                    
                                
                                <div class="form-group row">
                                    <label for="leaveStatus" class="col-3 col-form-label">Status</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <select class="custom-select" id="leaveStatus" ng-disabled="leaveStatusReadOnly" ng-model="leaveStatus">
                                                <option ng-repeat="x in statusMaster" value="{{x.value}}">{{x.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            <!--</div>-->

                            <!--<div class="tab-pane fade  " id="leaveTab" role="tabpanel">-->
                                <!--<div class="form-group row">
                                    <label for="leaveFrom" class="col-3 col-form-label">From</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="leaveFrom" ng-readonly="fromReadOnly" ng-model="from">
                                            <div class="input-group-append">
                                                <button type="button" id="leaveFromShow" ng-disabled="fromReadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="leaveTo" class="col-3 col-form-label">To</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="leaveTo" ng-readonly="toReadOnly" ng-model="to">
                                            <div class="input-group-append">
                                                <button type="button" id="leaveToShow" ng-disabled="toReadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->
                            <!--</div>-->
                        <!--</div>-->
                    </div>
                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <div class="text-center contentDetailTab">Leave Management</div>
                        <nav class="navbar navbar-light bg-light contentDetailTabNav">
                            <ul class="pagination pagination-sm   mb-0">
                                <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('StudLeaveManagementSummary')}}</span></a></li>
                                <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                <li class="page-item">
                                    <a class="page-link page_style svwRecCount">
                                        <span class="svwRecCount">{{fnMvwGetTotalPage('StudLeaveManagementSummary')}}</span></a>
                                </li>
                            </ul>
                            <div>
                            </div>
                            <ul class="pagination pagination-sm justify-content-end mb-0">
                                <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('StudLeaveManagementSummary',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('StudLeaveManagementSummary',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                <li class="page-item">
                                    <a id="addButton" ng-click="fnMvwAddRow('StudLeaveManagementSummary',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                </li>
                                <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('StudLeaveManagementSummary',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                            </ul>
                        </nav>
                        <div class="table-responsive" id="StudLeaveManagementSummary">
                            <table class="table table-striped table-sm table-bordered ">
                                <thead align="center">
                                    <tr>
                                        <th scope="col"></th>
                                        <th scope="col">Leave Type</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">From (DD/MM/YYYY)</th>
                                        <th scope="col">To (DD/MM/YYYY)</th>
                                        <!--<th scope="col">Leave Type</th>-->
                                    </tr>
                                </thead>
                                <tbody align="center">
                                    <tr ng-repeat="X in StudentLeaveManagementSummaryShowObject">
                                        <td>
                                            <input type="checkbox" ng-model="X.checkBox">
                                        </td>
                                        <td>{{X.type}}
                                        </td>
                                        <td>{{X.leaveStatus}}
                                        </td>
                                        <td>{{X.from}}
                                        </td>
                                        <td>{{X.to}}
                                        </td>
                                        
                                    </tr>
                            </table>
                        </div>
                    </div>
                    <div id="searchBody" ng-controller="StudentNamesearch">
                    </div>
                </div>
                <footer class="subscreenFooter" ng-show="!searchShow">
                    <nav class="nav nav-pills nav-justified">
                        <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Filter</a>
                        <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Summary</a>
                    </nav>
                </footer>
                <div id="snackbar"></div>
                <div id="spinner"></div>
        </body>

        </html>