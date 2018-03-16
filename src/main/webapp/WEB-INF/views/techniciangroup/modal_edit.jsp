<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="editTechnicianGroup" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขข้อมูลกลุ่มช่างเทคนิค</h4>
			</div>
			<form id="formUpdateTechniciangroup">
			<div class="modal-body">
				<div class="row">
					<div class="form-group">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>ชื่อกลุ่มช่างเทคนิค<span class="text-red"> *</span></b></label> 
						<input type="hidden" id = "editTechnicianGroupIdHidden" value="" />
						<input type="text" class="form-control" id="editTechnicianGroupName" name="editTechnicianGroupName"
							aria-describedby="" value="">
						<div class="messages"></div>
					</div>
					</div>
				</div>
				<div class="row mt05">
					<div class="form-group">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>หัวหน้าทีม<span class="text-red"> *</span></b></label> <select
							id="editPersonnelNotMember" name="categoryID" class="form-control">							
						</select>
						<div class="messages"></div>
					</div>
					</div>
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

<script type="text/javascript" src="resources/assets/plugins/jquery/jquery-1.12.3.min.js"></script>
<script type="text/javascript">
	function UpdateTechnicianGroup(){
		var technicianGroupBean = generateEditTechnicianGroup();
		
		//send update
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/techniciangroup/update",
			dataType : 'json',
			async: false,
			data : JSON.stringify(technicianGroupBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					//generatePersonnelSearchSession();
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
	
	function generateEditTechnicianGroup(){
		var technicianGroupBean = {};
		technicianGroupBean.id = $('#editTechnicianGroupIdHidden').val();
		technicianGroupBean.technicianGroupName = $('#editTechnicianGroupName').val();
		var PersonnelBean = {};
		PersonnelBean.id = $('#editPersonnelNotMember').val();
		technicianGroupBean.personnel = PersonnelBean;
		return technicianGroupBean;
	}
	
	function openDialogEditTechnicianGroup(id){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/techniciangroup/openEditTechnicianGroup/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					 $('#editPersonnelNotMember').find('option').remove();
					 $('#editPersonnelNotMember').append($("<option></option>").attr("value","").text(textChooseDropdown));
					 for(var i=0;i<data["result"].length; i++){
						 var textNameDisplay = data["result"][i].firstName + " " + data["result"][i].lastName;
						 var isCheckMember = false;
						 if(data["resultStore01"] != null){
							 if(data["resultStore01"]["personnel"].id == data["result"][i].id){
								 isCheckMember = true;
							 }
						 }
						 if(isCheckMember){
							 $('#editPersonnelNotMember').append($("<option selected='selected'></option>").attr("value",data["result"][i].id).text(textNameDisplay));
						 }else{
							 $('#editPersonnelNotMember').append($("<option></option>").attr("value",data["result"][i].id).text(textNameDisplay));
						 }
						
					}
					 
					 if(data["resultStore01"] != null){
						 $('#editTechnicianGroupName').val(data["resultStore01"].technicianGroupName);
						 $('#editTechnicianGroupIdHidden').val(data["resultStore01"].id);
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
		
		$('#editTechnicianGroup').modal('show');
		
		$(".error").remove();					
		var constraintsEdit = {
			"editTechnicianGroupName" : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			"categoryID" : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			}
		};

		// Hook up the form so we can prevent it from being posted
		var formEdit = document.querySelector("form#formUpdateTechniciangroup");
		formEdit.addEventListener("submit", function(ev) {
			
			ev.preventDefault();
			var errors = validate(formEdit, constraintsEdit);
			// then we update the form to reflect the results
			showErrors(formEdit, errors || {});
			if (!errors) {
				UpdateTechnicianGroup();
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
</script>