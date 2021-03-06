/* 	
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
    $scope.OperationScopes=OperationScopes;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
    //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.class = "Select option";
	$scope.exam = 'Select option';
	$scope.subjectID = "";
	$scope.subjectName = "Select option";
//	$scope.marks = "";
        $scope.MarkTable=null;
	$scope.subjects = Institute.SubjectMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.SubjectMaster = Institute.SubjectMaster;
	$scope.classes=Institute.ClassMaster;
	$scope.GradeDescription=null;
	$scope.examtypereadOnly = true;
        $scope.classReadonly = true;
	$scope.subjectIDReadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.markReadOnly = true;
        $scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	//Screen Specific Scope Ends
	
	
	
	// Multiple View Starts
	$scope.MarkCurPage = 0;
	$scope.MarkTable = null;
	$scope.MarkShowObject = null;
	// Multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'Mark') {
			if ($scope.MarkTable != null && $scope.MarkTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.MarkCurPage;
				lsvwObject.tableObject = $scope.MarkTable;
				lsvwObject.screenShowObject = $scope.MarkShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.MarkCurPage = lsvwObject.curPage;
				$scope.MarkTable = lsvwObject.tableObject;
				$scope.MarkShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'Mark') {
			if ($scope.MarkTable != null && $scope.MarkTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.MarkCurPage;
				lsvwObject.tableObject = $scope.MarkTable;
				lsvwObject.screenShowObject = $scope.MarkShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.MarkCurPage = lsvwObject.curPage;
				$scope.MarkTable = lsvwObject.tableObject;
				$scope.MarkShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'Mark') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
				    studentID: "",
				    grade: "",
				    mark: "",
				    teacherFeedback: ""
				};
				if ($scope.MarkTable == null)
					$scope.MarkTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.MarkCurPage;
				lsvwObject.tableObject = $scope.MarkTable;
				lsvwObject.screenShowObject = $scope.MarkShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.MarkCurPage = lsvwObject.curPage;
				$scope.MarkTable = lsvwObject.tableObject;
				$scope.MarkShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'Mark') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.MarkCurPage;
				lsvwObject.tableObject = $scope.MarkTable;
				lsvwObject.screenShowObject = $scope.MarkShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.MarkCurPage = lsvwObject.curPage;
				$scope.MarkTable = lsvwObject.tableObject;
				$scope.MarkShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'Mark') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.MarkCurPage;
			lsvwObject.tableObject = $scope.MarkTable;
			lsvwObject.screenShowObject = $scope.MarkShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'Mark') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.MarkCurPage;
			lsvwObject.tableObject = $scope.MarkTable;
			lsvwObject.screenShowObject = $scope.MarkShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'Mark') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.MarkTable);

		}
	};
	
	
	
	
	
	
	
	
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "ClassMark";
        selectBypassCount=0;
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="ClassEntity";
	 selectBoxes= ['class','examType','subject'];
        fnGetSelectBoxdata(selectBoxes);

	
	
});
//Default Load Record Ends
function fnClassMarkpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {
    
    
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0 && Institute.SubjectMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
      $scope.ExamMaster = Institute.ExamMaster;
      $scope.subjects = Institute.SubjectMaster;
      window.parent.fn_hide_parentspinner();  
    
   if ((window.parent.ClassMarkkey.class !=null && window.parent.ClassMarkkey.class !='')&&(window.parent.ClassMarkkey.exam !=null && window.parent.ClassMarkkey.exam !='')&&(window.parent.ClassMarkkey.subjectID !=null && window.parent.ClassMarkkey.subjectID !=''))
	{
		var class1=window.parent.ClassMarkkey.class;
		var exam=window.parent.ClassMarkkey.exam;
                var subjectID=window.parent.ClassMarkkey.subjectID;
		
		 window.parent.ClassMarkkey.class =null;
		 window.parent.ClassMarkkey.exam =null;
                 window.parent.ClassMarkkey.subjectID =null;
		
		fnshowSubScreen(class1,exam,subjectID);
		
	}
        $scope.$apply();
}
}
}

function fnClassMarkDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (($scope.MarkTable ==null)  && parentOperation=='Create')
	{	
var emptyClassMark = {
		class: "Select option",
		exam: 'Select option',
		subjectID: "",
		subjectName: 'Select option',
		marks: [{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		]
	};
    //Screen Specific DataModel Starts
	var dataModel = emptyClassMark;
    if($scope.class!=null)
	dataModel.class = $scope.class;
       dataModel.exam = $scope.exam;
       dataModel.subjectID = $scope.subjectID;
       dataModel.subjectName = $scope.subjectName;
     if ($scope.class == '' || $scope.class== null || $scope.class == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
//			 if ($scope.subjectName == '' || $scope.subjectName == null || $scope.subjectName == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Name']);
//				return false;
//			}
	    var response = fncallBackend('ClassMark', 'Create-Default', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
		$scope.mvwAddDeteleDisable = true; //Multiple View
	   //Multiple View Response Starts 
//		$scope.MarkTable = fnConvertmvw('MarkTable',response.body.marks);
//		$scope.MarkCurPage = 1
//		$scope.MarkShowObject = $scope.fnMvwGetCurPageTable('Mark');
		//Multiple View Response Ends 	
	    // $scope.$apply();
	 
	 }	

return true;
}


function fnshowSubScreen(class1,exam,subjectID)
{
subScreen = true;
var emptyClassMark = {
		class: "Select option",
		exam: 'Select option',
		subjectID: "",
		subjectName: 'Select option',
		marks: [{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		]
	};

    //screen Specific DataModel Starts
	var dataModel = emptyClassMark;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if(class1!==null&&class1!==null){
            
          if(exam!==null&&exam!==null){ 
              
             if(subjectID!==null&&subjectID!==null){ 
              
            
                  dataModel.class =class1;
                  dataModel.exam =exam;
                  dataModel.subjectID =subjectID;

                //Screen Specific DataModel Ends
                  var response = fncallBackend('ClassMark', 'View', dataModel,[{entityName:"class",entityValue:class1}],$scope);
	
          }
    }
        }
        return true;
}
//Cohesive Query Framework Starts
function fnClassMarkQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.subjectID = "";
	$scope.subjectName = 'Select option';
//	$scope.marks = "";
	$scope.MarkTable=null;
	$scope.classReadonly = false;
	$scope.examtypereadOnly = false;
	$scope.subjectIDReadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.markReadOnly = false;
        $scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.mvwAddDeteleDisable = true; //Multiple View
    //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnClassMarkView() {
	var emptyClassMark = {
		class: 'Select option',
		exam: 'Select option',
		subjectID: "",
		subjectName: 'Select option',
		marks: [{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		]
	};

    //screen Specific DataModel Starts
	var dataModel = emptyClassMark;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	  if($scope.class!==null&&$scope.class!==""){
              
              if($scope.subjectID!==null&&$scope.subjectID!==""){
                  
                  
                  if($scope.exam!==null&&$scope.exam!==""){
                  
                    dataModel.class = $scope.class;
                    dataModel.subjectID = $scope.subjectID;
                    dataModel.exam = $scope.exam;
                    //Screen Specific DataModel Ends
                    var response = fncallBackend('ClassMark', 'View', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);

                  }
              }
    
    }
 //	
//	if (response.header.status == 'success') {
//		//Screen Specific Scope Starts
//		$scope.classReadonly = true;
//		$scope.examtypereadOnly = true;
//		$scope.subjectIDReadOnly = true;
//		$scope.subjectNamereadOnly = true;
//		$scope.markReadOnly = true;
//                $scope.gradeReadOnly = true;
//		$scope.teacherFeedbackReadOnly = true;
//		//Screen Specific Scope Ends
//		//Generic Field Starts
//		$scope.MakerRemarksReadonly = true;
//		$scope.CheckerRemarksReadonly = true;
//		$scope.mastershow = true;
//		$scope.detailshow = false;
//		$scope.auditshow = false;
//		$scope.mvwAddDeteleDisable = true; //Multiple View
//		//Generic Field Ends
//		//Screen Specific Response Scope Starts 
//                $scope.class = response.body.class;
//		$scope.subjectID = response.body.subjectID;
//		$scope.subjectName = response.body.subjectName;
//		$scope.exam = response.body.exam;
////		$scope.marks = response.body.marks;
//		//Screen Specific Response Scope Ends
//		// Multiple View Starts
//	    $scope.MarkCurPage = 0;
//	    $scope.MarkTable = null;
//	    $scope.MarkShowObject = null;
//    	// Multiple View Ends
//	    //Multiple View Response Starts 
//		$scope.MarkTable = fnConvertmvw('MarkTable',response.body.marks);
//		$scope.MarkCurPage = 1;
//		$scope.MarkShowObject = $scope.fnMvwGetCurPageTable('Mark');
//		//Multiple View Response Ends 
//		return true;
//	} else {
//		return false;
//	}
	return true;
}
//Cohesive View Framework Ends
//Cohesive Mandatory Validation Starts
function fnClassMarkMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001' ['Exam']);
				return false;
			}
			if ($scope.subjectID == '' || $scope.subjectID == null || $scope.subjectID == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001' ['SubjectName']);
				return false;
			}

			break;

		case 'Save':
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['exam']);
				return false;
			}
			if ($scope.subjectID == '' || $scope.subjectID == null || $scope.subjectID == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['SubjectName']);
				return false;
			}
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnClassMarkDefaultandValidate(operation)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
switch(operation)
   {
     case 'View':   
                            
                      return true;   
			   
  			  break;
	 
	 case 'Save':   
                      return true;   
			   
  			  break;
	 
   
   }		
 return true; 		
}
//Screen Specific Default Validation Ends
//Cohesive Create Framework Starts
function fnClassMarkCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts	
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.subjectID = "";
	$scope.subjectName = 'Select option';
//	$scope.marks = "";
        $scope.MarkTable=null;
	$scope.classReadonly = false;
	$scope.examtypereadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.markReadOnly = false;
        $scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = false;
         $scope.classes=Institute.ClassMaster;
        $scope.subjects = Institute.SubjectMaster;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
       $scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
         $scope.operation = 'Creation';	
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Multiple View Starts
	$scope.MarkCurPage = 0;
	$scope.MarkTable = null;
	$scope.MarkShowObject = null;
	// Multiple View Ends
	//Generic Field Ends
	return true;
}
////Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnClassMarkEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
    //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.markReadOnly = false;
        $scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnClassMarkDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	//Generic Field Ends
    //Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.markReadOnly = true;
        $scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
    //Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnClassMarkAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
    //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
    //Screen Specific Scope Ends	
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnClassMarkReject() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
 	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework starts
function fnClassMarkBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.class= 'Select option';
		$scope.exam= 'Select option';
		$scope.subjectID= "";
		$scope.subjectName= 'Select option';
//		$scope.marks= "";
                $scope.MarkTable=null;
	}
	   $scope.classReadonly = true;
	    $scope.subjectNamereadOnly = true;
	    $scope.examtypereadOnly = true;
		$scope.markReadOnly = true;
                $scope.gradeReadOnly = true;
	    $scope.teacherFeedbackReadOnly = true;
		//Screen Specific Scope Ends
		// Generic Field Starts
                $scope.operation = '';
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		$scope.mastershow = true;
	        $scope.detailshow = false;
                $scope.auditshow = false;
	    //Generic Field Ends
}
//Cohesive BaackFramework Ends
//Cohesive Save Framework Starts
function fnClassMarkSave() {
	var emptyClassMark = {
		class: 'Select option',
		exam: 'Select option',
		subjectID: "",
		subjectName: 'Select option',
		marks: [{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		]
	};

	var dataModel = emptyClassMark;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if($scope.class!=null)
	dataModel.class = $scope.class;
     if($scope.exam!=null)
	dataModel.exam = $scope.exam;
     if($scope.subjectName!=null)
	dataModel.subjectName = $scope.subjectName;
     if($scope.MarkTable!=null)
	dataModel.marks = $scope.MarkTable;
    if($scope.subjectID!=null)
	dataModel.subjectID = $scope.subjectID;
	var response = fncallBackend('ClassMark', parentOperation, dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
	return true;
}
//Cohesive Save Framework Ends

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'MarkTable':
		   
			var MarkTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     MarkTable[index] = new Object();
					 MarkTable[index].idx=index;
					 MarkTable[index].checkBox=false;
					 MarkTable[index].studentID=value.studentID;
					 MarkTable[index].studentName=value.studentName;
					 MarkTable[index].grade=value.grade;
					 MarkTable[index].mark=value.mark;
					 MarkTable[index].teacherFeedback=value.teacherFeedback;
					}
			return MarkTable;
		}
	}




function fnClassMarkpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                 $scope.MakerRemarksReadonly = true;
	         $scope.CheckerRemarksReadonly = true;
		$scope.classReadonly = true;
		
                $scope.gradeReadOnly = true;
		//Screen Specific Scope Ends
		// Specific Screen Scope Ends
		// Generic Field Starts
//		$scope.mastershow = true;
//		$scope.detailshow = false;
//		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
                //
                if(response.header.operation=="Create-Default"){
                 
//	         $scope.attendanceReadOnly = false;
                 $scope.mastershow = false;
	         $scope.detailshow = true;
	         $scope.auditshow = false;
	 	 $scope.markReadOnly = false;
		 $scope.teacherFeedbackReadOnly = false;
                 $scope.examtypereadOnly = false;
	    	 $scope.subjectNamereadOnly = false;
                 
                }else{
                    
//                    $scope.attendanceReadOnly = true;
                    $scope.mastershow = true;
		    $scope.detailshow = false;
		    $scope.auditshow = false;
	            $scope.markReadOnly = true;
		    $scope.teacherFeedbackReadOnly = true;
                    $scope.examtypereadOnly = true;
	    	    $scope.subjectNamereadOnly = true;
                }
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
//                $scope.marks = "";
                $scope.MarkTable=null;
		$scope.class ="";
		$scope.exam ="";
                $scope.subjectID ="";
		$scope.subjectName ="";
        $scope.MarkShowObject=null;
        $scope.audit = {};
		 }
                else
                {
//	        $scope.marks = response.body.marks;
		$scope.class = response.body.class;
		$scope.exam = response.body.exam;
		$scope.subjectID = response.body.subjectID;
//		$scope.marks = response.body.marks;
                $scope.audit=response.audit;
                $scope.GradeDescription=response.body.GradeDescription;
		//Multiple View Response Starts 
                
                if(response.body.marks!==null){
                
		$scope.MarkTable = fnConvertmvw('MarkTable',response.body.marks);
                
               }else{
                   
                   $scope.MarkTable=null;
               }
                
		$scope.MarkCurPage = 1
		$scope.MarkShowObject = $scope.fnMvwGetCurPageTable('Mark');
		//Multiple View Response Ends 
		$scope.mvwAddDeteleDisable = true; //Multiple View
            }
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen = false;
	 }
           
         
         return true;

}

}



