<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="addTechnicianGroup" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><i class="ti-plus"></i> เพิ่มกลุ่มช่างเทคนิค</h4>
			</div>
			<form id="formAddTechniciangroup">
				<div class="modal-body">
				<div class="row">
					<div class="form-group">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>ชื่อกลุ่มช่างเทคนิค<span class="text-red"> *</span></b></label> <input
							type="text" class="form-control" id="addTechnicianGroupName" name="addTechnicianGroupName"
							aria-describedby="" value="">
							<div class="messages"></div>
					</div>
					</div>
				</div>
				<div class="row mt05">
					<div class="form-group">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>หัวหน้าทีม<span class="text-red"> *</span></b></label> 
						<select id="addPersonnelNotMember" name="categoryID" class="form-control">
							
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
	function AddTechnicianGroup(){
		var technicianGroupBean = generateTechnicianGroup();
		//send save
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/techniciangroup/save",
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
	
	function generateTechnicianGroup(){
		var technicianGroupBean = {};
		technicianGroupBean.technicianGroupName = $('#addTechnicianGroupName').val();
		var PersonnelBean = {};
		PersonnelBean.id = $('#addPersonnelNotMember').val();
		technicianGroupBean.personnel = PersonnelBean;
		return technicianGroupBean;
	}
	function openDialogAddTechnicianGroup(){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/techniciangroup/openAddTechnicianGroup",
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					 $('#addPersonnelNotMember').find('option').remove();
					 $('#addPersonnelNotMember').append($("<option selected='selected' disabled='disabled'></option>").attr("value","").text(textChooseDropdown));
					 for(var i=0;i<data["result"].length; i++){
						 var textNameDisplay = data["result"][i].firstName + " " + data["result"][i].lastName;
						$('#addPersonnelNotMember').append($("<option></option>").attr("value",data["result"][i].id).text(textNameDisplay));
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
		
		$('#addTechnicianGroup').modal('show');
	}
</script><script>
	var constraints = {
		addTechnicianGroupName : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		categoryID : {
		presence : {
			message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
		}
	}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#formAddTechniciangroup");
	form.addEventListener("submit", function(ev) {
		ev.preventDefault();
		handleFormSubmit(form);
	});

	function handleFormSubmit(form, input) {
		// validate the form aainst the constraints
		var errors = validate(form, constraints);
		// then we update the form to reflect the results
		showErrors(form, errors || {});
		if (!errors) {
			AddTechnicianGroup();
		}
	}

	// Hook up the inputs to validate on the fly
	var inputs = document.querySelectorAll("input, textarea, select");
	for (var i = 0; i < inputs.length; ++i) {
		inputs.item(i).addEventListener("change", function(ev) {
			var errors = validate(form, constraints) || {};
			showErrorsForInput(this, errors[this.name])
		});
	}
</script>