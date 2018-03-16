<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="display_technician" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;ตารางงานกลุ่มช่าง
					</h4>
				</div>
				<div class="modal-body"
					style="background: rgba(232, 235, 240, 0.48);">
					<div class="row">
						<div class="col-md-4">
							<span>เลือกวันที่สำหรับแสดงตารางงานกลุ่มช่าง</span>
						</div>
						<div class="col-md-3">
						<div class="input-group">
							<input type="text" class="form-control"
								id="dateTableOrderTechnicain" placeholder="วัน-เดือน-ปี">
							<div class="input-group-addon"><i class="fa fa-calendar-o"></i></div>
						</div>
					</div>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
						<div class="table-responsive">
							 <table class="table table-bordered mb-0 table-hover" id="tbDisplayTechnicianGroup">
							 	<thead class="thead-bg">
									<tr>
										<th width="100px;"><center>เลือก</center></th>
										<th><center>กลุ่มช่าง</center></th>
										<th><center>หัวหน้าช่าง</center></th>
										<th width="200px;"><center>ภาระงาน ( จำนวนงาน )</center></th>
									</tr>
								</thead>
								<tbody id="tbodyNewItem">
									<tr class="trNewItem">
									</tr>
								</tbody>
							 </table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" id="ok"
						onclick="appendToBlockChild()" >ตกลง</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
			</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	
<script type="text/javascript">	
function assignTaskBefore(){
	$('#display_technician').modal("show");
	loadTechnicainGroupByDate();
}
function loadTechnicainGroupByDate(){
	var dateWorkLoad = $('#dateTableOrderTechnicain').val();
	//$('.preloader').modal('show');
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/assignworksheet/loadTechnicianGroupByDateWorksheet",
		dataType : 'json',
		async: false,
		data : JSON.stringify(dateWorkLoad),
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){ 
				var rows = "";
				for(var i=0;i<data["result"].length;i++){
					var checked = "";
					if(i==0){
					checked = "checked";
					}
					
					var startRow = "<tr>";
					var endRow = "</tr>";
					var columnRadio = "<td><center>" +
					"<label class='custom-control custom-radio'>" + 
					"<input name='radioTechnicainGroupId' type='radio'"+
					" value='"+ data["result"][i]["technicianGroupBean"]["id"] +"'" +
					"class='custom-control-input' "+checked+">" +
					"<span class='custom-control-indicator'></span>" +
					"<span class='custom-control-description'>&nbsp;</span>" +
					"</label></center></td>";
					
					var columnTechGroup = "<td><center>"+ data["result"][i]["technicianGroupBean"]["technicianGroupName"] +"</center></td>";
					
					var columnPersonnelLead = "<td><center>"+ data["result"][i]["technicianGroupBean"]["personnel"].firstName +" " + data["result"][i]["technicianGroupBean"]["personnel"].lastName + "</center></td>";
					
					var columnSizeWorkSheet = "<td><center>"+ data["result"][i]["worksheetSize"] +"</center></td>";
					
					rows = rows + startRow+columnRadio+columnTechGroup+columnPersonnelLead+columnSizeWorkSheet+endRow;
				}
				
				//var emptyData = "";
				if(rows.length > 0){
					$('#tbDisplayTechnicianGroup > tbody').html(rows);
				}else{
					$('#tbDisplayTechnicianGroup > tbody').empty();
				}
				
				setTimeout(
						function() 
							  {
							$('.preloader').modal('hide');
							$('#display_technician').css('overflow-y', 'auto');
							  }, 200);
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

function appendToBlockChild(){
	var radioTechnicianGroup = $("input:radio[name ='radioTechnicainGroupId']:checked").val();
	if(typeof radioTechnicianGroup != 'undefined'){
		$('#display_technician').modal('hide');
		assignTask(radioTechnicianGroup,"test");
	}
}

$( document ).ready(function() {
	//set date picker
	$("#dateTableOrderTechnicain").datepicker({
		autoclose: true,
		format: 'dd-mm-yyyy',
		todayHighlight:'TRUE'
	});	
	var tomorrow = new Date();
	var currentDate = new Date();
	currentDate.setDate(tomorrow.getDate()+1);
 	$("#dateTableOrderTechnicain").datepicker("setDate", currentDate);
 	
 	$("#dateTableOrderTechnicain").change(function() {
 		loadTechnicainGroupByDate();
 	});
});
</script>

