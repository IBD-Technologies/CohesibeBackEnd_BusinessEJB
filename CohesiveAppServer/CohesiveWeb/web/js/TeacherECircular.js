/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.circularDescription = "";
	$scope.contentPath = "";
	$scope.subjectID = "";
	$scope.circularID = "";
	$scope.ECircularMaster = [{
		ECircularId: "",
		ECircularDescription: ""
	}];
	$scope.circularType = "";
	$scope.circularDate = "";
	$scope.message = "";
	$scope.classes = Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.StatusMaster = Institute.StatusMaster;
	$scope.Status = Institute.StatusMaster;
	$scope.ECircularType = Institute.ECircularTypeMaster;
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.circularIDreadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
	$scope.circularTypereadOnly = true;
	$scope.circularDatereadOnly = true;
        $scope.signStatusreadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.completeDatereadOnly = true;
	$scope.messagereadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.statusReadonly = true;
        $scope.urlreadOnly = true;
	$scope.contentPathreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.tableOperationShow = true;
	$scope.subReadonly = true;
	$scope.operation = '';
        //Generic Field Ends
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
	$scope.fnCircularSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Circular';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	

});
//--------------------------------------------------------------------------------------------------------------

//-------Default Load record Starts -------------------------------------
$(document).ready(function () {
	  MenuName = "TeacherECircular";
          window.parent.nokotser=$("#nokotser").val();
          window.parent.Entity="Teacher";
     
	  fnDatePickersetDefault('circularDate',fndatePickercircularDateEventHandler);
//	  fnDatePickersetDefault('circularCompleteDate',fndatePickercircularCompleteDateEventHandler);
          fnsetDateScope();
		   selectBoxes= ['subject','circularType','Status'];
          fnGetSelectBoxdata(selectBoxes);
	 
          
          
//         $("#StudECircularFile").change(function(){ 
//         $("#StudECircularUpld").submit();
//        
//}); 
//  $("#StudECircularUrl").change(function(){ 
//        $("#StudECircularUrlUpld").submit();
//        
//});    

$( "#InstituteECircularURL" ).keydown(function() {
  $("#Institute").attr("src",$("#InstituteECircularURL").val());
});
         
});
// Default Load Record Ends


function fnPostImageUpload(id,fileName,UploadID)
{
    
       $(id).attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope(); 
       if (id =='#studECircular')
         $scope.contentPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       else if(id =='#video') 
         $scope.url="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       $scope.$apply();
   
}

function fnTeacherECircularpostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.SubjectMaster.length>0)
      {    
      // $scope.Types = Institute.NotificationMaster;
	$scope.subjects = Institute.SubjectMaster;
        $scope.signature=Institute.ParticipateMaster;
	window.parent.fn_hide_parentspinner();
	  if (window.parent.TeacherECircularkey.circularID !=null && window.parent.TeacherECircularkey.circularID !='')
	{
            
             if (window.parent.TeacherECircularkey.teacherID !=null && window.parent.TeacherECircularkey.teacherID !='')
	{
		var circularID=window.parent.TeacherECircularkey.circularID;
		var teacherID=window.parent.TeacherECircularkey.teacherID;
		 window.parent.TeacherECircularkey.circularID =null;
                 window.parent.TeacherECircularkey.teacherID =null;
		
		fnshowSubScreen(circularID,teacherID);
		
            }     
                
	}
    /*var emptyTeacherECircular = {
		teacherName: "",
		teacherID: "",
		subjectID: "",
		subjectName: "Select option",
		circularID: "",
		circularDescription: "",
		circularType: "",
		circularDate: "",
		completedDate: "",
		message: "",
		parentComment: "",
		contentPath: "",
                url:""
	};
         //Screen Specific DataModel Starts
	var dataModel = emptyTeacherECircular;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null && $scope.teacherID!=""){
	dataModel.teacherID = $scope.teacherID;
	var response = fncallBackend('TeacherECircular', 'View', dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
}*/
$scope.$apply();
}
}
function fnshowSubScreen(circularID,teacherID)
{
        subScreen = true;
	var emptyTeacherECircular = {
		teacherName: "",
		teacherID: "",
		subjectID: "",
		subjectName: "Select option",
		circularID: "",
		circularDescription: "",
		circularType: "",
		circularDate: "",
		completedDate: "",
		message: "",
		parentComment: "",
		contentPath: "",
                url:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacherECircular;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.circularID=circularID;
        $scope.teacherID=teacherID;
        
        if($scope.circularID!=null && $scope.circularID!="")
        {
        if($scope.teacherID!=null && $scope.teacherID!="")
        {    
            
            
	dataModel.circularID = $scope.circularID;
        dataModel.teacherID = $scope.teacherID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherECircular', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	
    }
}
return true;
}
// Cohesive Screen Specific Query Starts
function fnTeacherECircularQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.subjectID = "";
	$scope.subjectName = "Select option";
	$scope.circularID = "";
	$scope.circularType = "";
	$scope.circularDescription = "";
	$scope.circularDate = "";
	$scope.completedDate = "";
	$scope.contentPath = "";
	$scope.message = "";
	$scope.parentComment = "";
        $("#Institute").attr("src","");
//           $("#studECircular").attr("src","");
//         $("#TeacherECircularFile").val(""); 
//            $("#video").attr("src","");
//         $("#StudECircularUrl").val(""); 
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.subjectNamereadOnly = true;
	$scope.circularIDreadOnly = false;
	$scope.circularTypereadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.circularDatereadOnly = true;
        $scope.signStatusreadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.contentPathreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = false;
        $scope.urlreadOnly = false;
	$scope.contentPathreadOnly = false;
	$scope.messagereadOnly = true;
	$scope.parentCommentreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit ={};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
    //Generic Field Ends
	return true;
}
// Cohesive Screen Specific Query Ends

// Cohesive Screen Specific View Starts
function fnTeacherECircularView() {
	var emptyTeacherECircular = {
		teacherName: "",
		teacherID: "",
		circularID: "",
		circularDescription: "",
		circularType: "",
		circularDate: "",
		message: "",
		contentPath: "",
                url:""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyTeacherECircular;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
        dataModel.teacherName = $scope.teacherName;
        if($scope.circularID!=null)
	dataModel.circularID = $scope.circularID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherECircular', 'View', dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
        
        
}
// Cohesive Screen Specific View Ends
//Screen Specific Mandatory Validation Starts
function fnTeacherECircularMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//			if ($scope.teacherID == '' || $scope.teacherID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
//				return false;
//			}
			/*if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}*/
                        if ($scope.teacherID == '' || $scope.teacherID == null) {
                         
                         if ($scope.teacherName == '' || $scope.teacherName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID or Teacher Name']);
			return false;
                         }
                         
                     }
                        if ($scope.circularID == '' || $scope.circularID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['ECircular ID']);
				return false;
			}
			/*if ($scope.circularDate == '' || $scope.circularDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['ECircular Due Date']);
				return false;
			}*/

			break;

		case 'Save':
                       if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}
                        if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			if ($scope.circularID == '' || $scope.circularID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['ECircular ID']);
				return false;
			}
                        if ($scope.circularType == '' || $scope.circularType == null || $scope.circularType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['ECircular Type in General Tab']);
				return false;
			}
                        
			if ($scope.circularDescription == '' || $scope.circularDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['ECircular Description']);
				return false;
			}
			if ($scope.subjectName == '' || $scope.subjectName == null || $scope.subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Name in General Tab']);
				return false;
			}
			if ($scope.circularDate == '' || $scope.circularDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['ECircular Due Date']);
				return false;
			}
			if ($scope.message == '' || $scope.message == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Comments in General Tab']);
				return false;
			}

			break;


	}
	return true;
}

function fnTeacherECircularDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}

function fnTeacherECircularDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultECircularId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultECircularId($scope))
				return false;

			break;
	}
	return true;
}
function fnDefaultECircularId($scope) {
	var availabilty = false;
	return true;
}

// Screen Specific Mandatory Validation Ends
// Cohesive Create Framework Starts 
function fnTeacherECircularCreate() { 
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.subjectID = "";
	$scope.subjectName = "Select option";
	$scope.circularID = "";
	$scope.circularType = "";
	$scope.circularDate = "";
	$scope.completedDate = "";
	$scope.message = "";
	$scope.parentComment = "";
        $scope.url = "";
        $scope.contentPath = "";
	$scope.circularDescription = "";
          $("#studECircular").attr("src","");
         $("#TeacherECircularFile").val(""); 
            $("#video").attr("src","");
         $("#StudECircularUrl").val(""); 
	$scope.teacherIDreadOnly = false;
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.subjectIDreadOnly = false;
	$scope.circularTypereadOnly = false;
	$scope.circularIDreadOnly = false;
	$scope.circularDatereadOnly = false;
        $scope.signStatusreadOnly = false;
        $( "#circularDate" ).datepicker( "option", "disabled", false );
	$scope.messagereadOnly = false;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = false;
	$scope.statusReadonly = false;
	$scope.circularDescriptionreadOnly = false;
        $scope.urlreadOnly = false;
	$scope.contentPathreadOnly = false;
        //Screen Specific Scope Starts
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.subjects = Institute.SubjectMaster;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
        //Generic Field Ends
	return true;
}
// Cohesive Create Framework Ends
// Cohesive Edit Framework Starts
function fnTeacherECircularEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
         //Generic field Ends
	//Screen Specific Scope Starts
	$scope.teacherIDreadOnly = true;
         $scope.teacherNameSearchreadOnly = true;
	$scope.teacherNamereadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.subjectIDreadOnly = false;
	$scope.circularTypereadOnly = false;
	$scope.circularIDreadOnly = false;
	$scope.circularDescriptionreadOnly = false;
	$scope.circularDatereadOnly = false;
        $scope.signStatusreadOnly = false;
        $( "#circularDate" ).datepicker( "option", "disabled", false );
	$scope.messagereadOnly = false;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = false;
	$scope.statusReadonly = false;
        $scope.urlreadOnly = false;
	$scope.contentPathreadOnly = false;
    //Screen Specific Scope Ends	
	return true;
}
// Cohesive Edit Framework Ends
// Cohesive Delete Framework Starts
function fnTeacherECircularDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	// Generic Field Ends
    // screen Specific Scope Starts
	$scope.teacherIDreadOnly = true;
	$scope.teacherNamereadOnly = true;
         $scope.teacherNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.circularTypereadOnly = true;
	$scope.circularIDreadOnly = true;
	$scope.circularDatereadOnly = true;
        $scope.signStatusreadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.messagereadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.circularDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentPathreadOnly = true;
	 // screen Specific Scope Ends	
	return true;
}
// Cohesive Delete Framework Ends
// Cohesive Authorization Framework Starts
function fnTeacherECircularAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
    //Generic Field Ends
	// Screen Specific Scope Starts
	$scope.teacherIDreadOnly = true;
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.circularTypereadOnly = true;
	$scope.circularIDreadOnly = true;
	$scope.circularDatereadOnly = true;
        $scope.signStatusreadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.messagereadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.circularDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentPathreadOnly = true;
	// Screen Specific Scope Ends
	
	return true;
}

// Cohesive Authorization Framework Ends


function fnTeacherECircularEnroll() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
        enroll=true;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
    //Generic Field Ends
	// Screen Specific Scope Starts
	$scope.teacherIDreadOnly = true;
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.circularTypereadOnly = true;
	$scope.circularIDreadOnly = true;
	$scope.circularDatereadOnly = true;
        $scope.signStatusreadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.messagereadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.circularDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentPathreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}





// Cohesive Reject Framework Starts
function fnTeacherECircularReject() {

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
	$scope.teacherIDreadOnly = true;
	$scope.teacherNamereadOnly = true;
         $scope.teacherNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.circularTypereadOnly = true;
	$scope.circularIDreadOnly = true;
	$scope.circularDatereadOnly = true;
        $scope.signStatusreadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.messagereadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.circularDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentPathreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
// Cohesive Reject Framework Ends
// Cohesive Back Framework Starts
function fnTeacherECircularBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specfic Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.teacherName = "";
		$scope.teacherID = "";
		$scope.subjectID = "";
		$scope.subjectName = "Select option";
		$scope.circularID = "";
		$scope.circularDescription = "";
		$scope.circularType = "";
		$scope.circularDate ="";
		$scope.completedDate ="";
		$scope.message ="";
		$scope.parentComment ="";
                $scope.contentPath ="";
                $scope.url ="";
                $("#Institute").attr("src","");
//                $("#studECircular").attr("src","");
//                $("#TeacherECircularFile").val(""); 
//                $("#video").attr("src","");
//                $("#StudECircularUrl").val(""); 
	}
	        //Screen Specific Scope Starts
		$scope.teacherIDreadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
		$scope.teacherNamereadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.subjectIDreadOnly = true;
		$scope.circularTypereadOnly = true;
		$scope.circularIDreadOnly = true;
		$scope.circularDatereadOnly = true;
                $scope.signStatusreadOnly = true;
                $( "#circularDate" ).datepicker( "option", "disabled", true );
		$scope.messagereadOnly = true;
		$scope.parentCommentreadOnly = true;
		$scope.completedDatereadOnly = true;
		$scope.statusReadonly = true;
                $scope.urlreadOnly = true;
	        $scope.contentPathreadOnly = true;
		$scope.circularDescriptionreadOnly = true;
		// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	// Generic Field Ends
}
// Cohesive Back Framework Ends
// Cohesive Save Framework Starts
function fnTeacherECircularSave() {
	var emptyTeacherECircular = {
		teacherName: "",
		teacherID: "",
		subjectID: "",
		subjectName: "Select option",
		circularDescription:"",
		circularID: "",
		circularType: "",
		circularDate: "",
		completedDate: "",
		message: "",
		status: "",
		parentComment: "",
                contentPath:"",
                url:""
	};
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific DataModel Starts
	var dataModel = emptyTeacherECircular;
        if($scope.teacherName!=null)
	dataModel.teacherName = $scope.teacherName;
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
        if($scope.subjectID!=null)
	dataModel.subjectID = $scope.subjectID;
        if($scope.subjectName!=null)
	dataModel.subjectName = $scope.subjectName;
        if($scope.circularID!=null)
	dataModel.circularID = $scope.circularID;
        if($scope.circularDescription!=null)
	dataModel.circularDescription = $scope.circularDescription;
        if($scope.circularType!=null)
	dataModel.circularType = $scope.circularType;
        if($scope.circularDate!=null)
	dataModel.circularDate = $scope.circularDate;
        if($scope.message!=null)
	dataModel.message = $scope.message;
        if($scope.completedDate!=null)
	dataModel.completedDate = $scope.completedDate;
        if($scope.parentComment!=null)
	dataModel.parentComment = $scope.parentComment;
        if($scope.contentPath!=null)
	dataModel.contentPath = $scope.contentPath;
       if($scope.url!=null)
	dataModel.url = $scope.url;
	//Screen Specific DataModel Ends
	var response = fncallBackend('TeacherECircular', parentOperation, dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
// Cohesive Save Framework Starts
function fndatePickercircularDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.circularDate = $.datepicker.formatDate('dd-mm-yy', $("#circularDate").datepicker("getDate"));
		$scope.$apply();
}
function fndatePickercircularCompleteDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.completedDate = $.datepicker.formatDate('dd-mm-yy', $("#circularCompleteDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.circularDate = $.datepicker.formatDate('dd-mm-yy', $("#circularDate").datepicker("getDate"));
//	$scope.completedDate = $.datepicker.formatDate('dd-mm-yy', $("#circularCompleteDate").datepicker("getDate"));
		$scope.$apply();
}	

function fnTeacherECircularpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

    if(enroll){
        
     parentOperation="Auth";
    
    }

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.teacherIDreadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
		$scope.teacherNamereadOnly = true;
		$scope.circularTypereadOnly = true;
		$scope.circularIDreadOnly = true;
		$scope.circularDescriptionreadOnly = true;
		$scope.circularDatereadOnly = true;
                $scope.signStatusreadOnly = true;
                $( "#circularDate" ).datepicker( "option", "disabled", true );
		$scope.messagereadOnly = true;
		$scope.parentCommentreadOnly = true;
		$scope.completedDatereadOnly = true;
		$scope.statusReadonly = true;
                $scope.urlreadOnly = true;
	        $scope.contentPathreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.circularID = "";
		$scope.circularDescription ="";
		$scope.circularType ="";
                $scope.teacherID ="";
                $scope.subjectName ="";
                $scope.teacherName ="";
                $scope.circularDate ="";
                $scope.completedDate ="";
                $scope.message ="";
                $scope.parentComment ="";
                $scope.subjectID ="";
                 $scope.contentPath ="";
                  $scope.url ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.circularID = response.body.circularID;
		$scope.circularDescription = response.body.circularDescription;
		$scope.circularType = response.body.circularType;
		$scope.teacherID = response.body.teacherID;
		$scope.subjectName = response.body.subjectName;
		$scope.teacherName = response.body.teacherName;
		$scope.circularDate = response.body.circularDate;
		$scope.completedDate = response.body.completedDate;
		$scope.message = response.body.message;
		$scope.parentComment = response.body.parentComment;
		$scope.subjectID = response.body.subjectID;
                $scope.contentPath = response.body.contentPath;
                $scope.signStatus = response.body.signStatus;
                    $scope.url = response.body.url;
                    
                    $scope.audit=response.audit;
		//Screen Specific Response Scope Ends
                if($scope.contentPath!=null&&$scope.contentPath!=''){
                
                 fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.contentPath);
                
                
                }
 
        }
         if (subScreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subScreen=false;
      }
//         if($scope.contentPath!=null)
//         $("#studECircular").attr("src",$scope.contentPath);    
//         if($scope.url!=null)
//         $("#video").attr("src",$scope.url);    
//         if($scope.url!=null)
//         $("#Institute").attr("src",$scope.url); 
      
      
      
      
		return true;

}

}