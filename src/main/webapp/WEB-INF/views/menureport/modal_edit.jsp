<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

	<div class="modal fade" id="editMenuReport" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขรูปแบบใบงาน</h4>
				</div>
				<form id="formUpdateMenuReport">
				<div class="modal-body">
					<div class="form-group">
					<label for="exampleInputEmail1"><b>ชื่อรูปแบบใบงาน<span class="text-red"> *</span></b></label>
					<input type="text" class="form-control" id="edit_menuReportName" aria-describedby="emailHelp" name="edit_menuReportName">
					<input type="hidden" id="menuReportId" value="" />
					<div class="messages"></div>
					</div>
					<div class="form-group">
					<label for="exampleInputEmail1"><b>รหัสรูปแบบใบงาน</b></label>
					<input type="text" class="form-control" id="edit_menuReportCode" aria-describedby="emailHelp" name="edit_menuReportCode">
					<div class="messages"></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">บันทึก</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
				</form>
			</div>
		</div>
	</div>

<script type="text/javascript">

	function editUnit(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/menureport/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#menuReportId').val(data["result"]["id"]);
					$('#edit_menuReportName').val(data["result"]["menuReportName"]);
					$('#edit_menuReportCode').val(data["result"]["menuReportCode"]);
					$('#editMenuReport').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
						"edit_menuReportName" : {
							presence : {
								message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
							}
						}
					};

					// Hook up the form so we can prevent it from being posted
					var formEdit = document.querySelector("form#formUpdateMenuReport");
					formEdit.addEventListener("submit", function(ev) {
						
						ev.preventDefault();
						var errors = validate(formEdit, constraintsEdit);
						// then we update the form to reflect the results
						showErrors(formEdit, errors || {});
						if (!errors) {
							updateMenuReport();
						}
					});


					// Hook up the inputs to validate on the fly
					var inputsEdit = document.querySelectorAll("input, textarea, select");
					for (var i = 0; i < inputsEdit.length; ++i) {
						inputsEdit.item(i).addEventListener("change", function(ev) {
							var errors = validate(formEdit, constraintsEdit) || {};
							showErrorsForInput(this, errors[this.name])
						});
					}
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
	
	//update
	function updateMenuReport(){
		//generate bean
		var menuReportBean = generateEditJson();
		//validate basic
// 		console.log(unitBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/menureport/update",
			dataType : 'json',
			data : JSON.stringify(menuReportBean),
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
	
	function generateEditJson(){
		var menuReportBean = {};

		menuReportBean.id = $('#menuReportId').val();
		menuReportBean.menuReportName = $('#edit_menuReportName').val();
		menuReportBean.menuReportCode = $('#edit_menuReportCode').val();
		
		return menuReportBean;
	}



</script>

