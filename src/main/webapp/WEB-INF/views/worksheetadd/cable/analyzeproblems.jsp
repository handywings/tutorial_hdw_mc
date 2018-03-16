<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div id="job11" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-12">
			<label><b>วิเคราะห์ปัญหา</b></label> 
			<select id="menuReports"class="form-control">
			</select>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<b>หมายเหตุ / ข้อมูลเพิ่มเติม</b>
			<textarea class="form-control" id="aprob_tune_remark" rows="3"></textarea>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
function saveAnalyzeProblems(){
	$('.preloader').modal('show');
	var analyzeProblemsWorksheetBean = {};
	
	analyzeProblemsWorksheetBean.menuReportId = $('#menuReports').val();
	analyzeProblemsWorksheetBean.remark = $('#aprob_tune_remark').val();
	
	analyzeProblemsWorksheetBean.availableDateTime = $('#daterange1').val();
	
	//service application
	ServiceApplicationBean = {};
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	analyzeProblemsWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	//send save worksheet tune
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/saveAnalyzeProblems",
		dataType : 'json',
		async : false,
		data : JSON.stringify(analyzeProblemsWorksheetBean),
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				window.location.reload();
			}else{
				console.log("ERROR");
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
	
  }

function loadAnalyzeProblems(){
	$('.preloader').modal('show');
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/loadAnalyzeProblems",
		dataType : 'json',
		async : false,
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				console.log(data);
				var html = '';
				
				$(data["result"]).each(function(i,value) {
					html += '<option value="'+value.id+'">'+value.menuReportName+'</option>';
				});
				
				$('#menuReports').html(html);

				setTimeout(function() {$('.preloader').modal('hide'); }, 200);
				
			}else{
				console.log("ERROR");
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

</script>