<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div id="job3" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-12">
			<label for="exampleInputEmail1"><b>เหตุผลการขอแจ้งจูนสัญญาณโทรทัศน์</b></label><br>
			<label class="custom-control custom-radio"> <input
				id="c_tune_radio1" name="c_junType" type="radio" checked="checked"
				value="1" class="custom-control-input"> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;&nbsp;&nbsp;โทรทัศน์ใหม่</span>
			</label><br><label class="custom-control custom-radio"> <input
				id="c_tune_radio2" name="c_junType" type="radio" value="2"
				class="custom-control-input"> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;&nbsp;&nbsp;ช่องสัญญาณเคลื่อน หรือ ช่องสัญญาณหาย</span>
			</label><br><label class="custom-control custom-radio"> <input
				id="c_tune_radio3" name="c_junType" type="radio" value="3"
				class="custom-control-input"> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;&nbsp;&nbsp;เพิ่มช่องสัญญาณ</span>
			</label>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<b>หมายเหตุ / ข้อมูลเพิ่มเติม</b>
			<textarea class="form-control" id="c_tune_remark" rows="3"></textarea>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
function saveTune(){
	$('.preloader').modal('show');
	var tuneWorksheetBean = {};
	
	tuneWorksheetBean.remark = $('#c_tune_remark').val();
	var radioTuneType = $('input[name = "c_junType"]:checked').val();
	tuneWorksheetBean.tuneType = radioTuneType;
	
	tuneWorksheetBean.availableDateTime = $('#daterange1').val();
	
	//service application
	ServiceApplicationBean = {};
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	tuneWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	//send save worksheet tune
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/saveTune",
		dataType : 'json',
		async : false,
		data : JSON.stringify(tuneWorksheetBean),
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				window.location.reload();
			}else{
				
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

function loadTune(){
	
}
</script>